package com.canada.aws.service.impl;

import com.canada.aws.dto.*;
import com.canada.aws.model.*;
import com.canada.aws.repo.*;
import com.canada.aws.service.ProductService;
import com.canada.aws.utils.ConvertDataUtils;
import com.canada.aws.utils.FileUploadUtils;
import com.canada.aws.utils.MapperUtils;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UserEntityRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    ProductServiceHelper productServiceHelper;


    public Product createAProduct(ProductDto productDto) throws IOException {
        String alias = productDto.getName()
                .toLowerCase(Locale.ROOT)
                .trim()
                .replace(",","_")
                .replace("&","_")
                .replace(" ","_");

        Brand brand = brandRepository.findById(productDto.getBrand_id()).get();

        Category category = categoryRepository.findById(productDto.getCategory_id()).get();


        Product savedProduct = Product.builder()
                .brand(brand)
                .category(category)
                .name(productDto.getName())
                .alias(alias)
                .fullDescription(productDto.getFullDescription())
                .price(productDto.getPrice())
                .build();

        if(productDto.getDiscountPrice() > 0){
            savedProduct.setDiscountStart(productDto.getDiscountStart());
            savedProduct.setDiscountEnd(productDto.getDiscountEnd());
        }

        savedProduct = productRepository.save(savedProduct);

        productServiceHelper.saveImages(productDto.getMainImage(), productDto.getExtraImages(), savedProduct);

        return savedProduct;
    }

    @Override
    public List<Product> getAllProductsByKeyword(String keyword){
        Set<Product>productSet = new HashSet<>();
        List<Category>categories = categoryRepository.findCategoriesContainsName(keyword.toLowerCase());
        categories.forEach(c ->{
            List<Product>productsOfCategory = productRepository.findAllByCategoryId(c.getId());
            if(!productsOfCategory.isEmpty()){
                productSet.addAll(productsOfCategory);
            }
        });

        List<Brand>brands = brandRepository.findBrandsContainsName(keyword);
        brands.forEach(b ->{
            List<Product>productsOfBrand = productRepository.findAllByBrandId(b.getId());
            if(!productsOfBrand.isEmpty()){
                productSet.addAll(productsOfBrand);
            }
        });

        List<Product>productsContainingKeyword = productRepository.findAllByProductName(keyword);
        if(!productsContainingKeyword.isEmpty()){
            productSet.addAll(productsContainingKeyword);
        }


        return productSet.stream().collect(Collectors.toList());
    }


    @Override
    public List<Product> getAllProductsByCategoryIdAndKeyword(
            Integer categoryId,
            String keyword
    ) {
        List<Category>subCategories = categoryRepository.findSubCategoriesFromARoot(categoryId.toString());
        List<Integer>categoryIds = subCategories.stream().map(Category::getId).collect(Collectors.toList());
        categoryIds.add(categoryId);
        List<Product> productList = productRepository.findAllByCategoryIdsKeyword(categoryIds,keyword);
        return productList;

    }

    @Override
    public List<Product> filterByBrands(List<Product> products, List<Integer>brandIds) {
        List<Product>productList = new ArrayList<>();
        for(Integer i: brandIds){
            List<Product> pFilter = products.stream().filter(p->p.getBrand().getId().equals(i)).collect(Collectors.toList());
            productList.addAll(pFilter);
        }
        return productList;
    }

    @Override
    public List<Product> filterByPrice(List<Product> products, Integer priceStart, Integer priceEnd) {
        return products.stream().filter(p -> {
            if(p.getDiscountPrice()!=null) return p.getDiscountPrice()>=priceStart && p.getDiscountPrice() <priceEnd;

            return p.getPrice() >= priceStart && p.getPrice() < priceEnd;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Product> sortProducts(List<Product> products, String sortType) {
        switch(sortType){
            case "asc":
                Collections.sort(products, (p1, p2) -> {
                    if(p1.getDiscountPrice()!=null && p2.getDiscountPrice()!=null){
                        return p1.getDiscountPrice().compareTo(p2.getDiscountPrice());
                    }
                    else if(p1.getDiscountPrice()!=null && p2.getDiscountPrice()==null){
                        return p1.getDiscountPrice().compareTo(p2.getPrice());
                    }
                    else{
                        return p1.getPrice().compareTo(p2.getPrice());
                    }
                });
                break;
            case "desc":
                Collections.sort(products, (p1, p2) -> {
                    if(p1.getDiscountPrice()!=null && p2.getDiscountPrice()!=null){
                        return p2.getDiscountPrice().compareTo(p1.getDiscountPrice());
                    }
                    else if(p1.getDiscountPrice()!=null && p2.getDiscountPrice()==null){
                        return p2.getDiscountPrice().compareTo(p1.getPrice());
                    }
                    else{
                        return p2.getPrice().compareTo(p1.getPrice());
                    }
                });
                break;
            default:
                Collections.sort(products, (p1, p2) -> p1.getCreatedTime().compareTo(p2.getCreatedTime()));
                break;
        }
        return products;
    }

    @Override
    public ProductSearchResultDto getSearchResult(List<Product> productsByCategoryKeyword, List<Product> productByFilter, Integer pageIdx, Integer perPage, String keyword) {
        // no filter, only category & keyword

            if(productByFilter==null){
                productByFilter = productsByCategoryKeyword;
            }
            Set<Brand>brands = productsByCategoryKeyword.stream().map(Product::getBrand).collect(Collectors.toSet());
            List<BrandDto>brandDtos = MapperUtils.mapperList(Arrays.asList(brands.toArray()), BrandDto.class);


            Set<UserEntity>sellers = productsByCategoryKeyword.stream().map(Product::getSeller).collect(Collectors.toSet());
            List<SellerDto>sellerDtos = MapperUtils.mapperList(Arrays.asList(sellers.toArray()), SellerDto.class);

            List<ProductSearchDto> productDtos = MapperUtils.mapperList(productByFilter, ProductSearchDto.class);
            int start = pageIdx * perPage;
            int end = (start+perPage<productByFilter.size())?perPage*(pageIdx+1):productByFilter.size();

            try{
            ProductSearchResultDto result = ProductSearchResultDto.builder()
                    .totalResults(productByFilter.size())
                    .products(productDtos.subList(start, end))
                    .sellers(sellerDtos)
                    .brands(brandDtos)
                    .keyword(keyword)
                    .build();

            return result;
        }
        catch (Exception ex){
            ProductSearchResultDto result = ProductSearchResultDto.builder()
                    .totalResults(0)
                    .products(null)
                    .sellers(sellerDtos)
                    .brands(brandDtos)
                    .keyword(keyword)
                    .build();
            return result;
        }

    }

    @Override
    public ProductDetailsDto getProductByProductId(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            List<String> images = new ArrayList<>(List.of(product.get().getMainImage()));
            images.addAll(product.get().getExtraImages().stream().map(ProductImage::getName).collect(Collectors.toList()));

           ProductDetailsDto productDto = MapperUtils.mapperObject(product.get(), ProductDetailsDto.class);


           List<Category>categories = categoryService.getTreeCategoriesByCategoryId(product.get().getCategory().getId());

           if(categories==null) {
               categories = new ArrayList<>(List.of(product.get().getCategory()));
           }

           List<CategoryDto>categoryDtos = MapperUtils.mapperList(categories, CategoryDto.class);

           productDto.setTreeCategories(categoryDtos);
           productDto.setImages(images);

           return productDto;
        }
        return null;
    }

    @Override
    public ProductsBrowsingResultDto getBrowsingHistoryProducts(String userId, Integer categoryId) {
        Optional<UserEntity>user = userRepository.findById(userId);

        List<String> browsingProductImages = new ArrayList<>();
        List<ProductSearchDto>inspiredsDto = new ArrayList<>();

        if(user.isPresent() && user.get().getBrowsing_histories().size() > 0){
            List<Integer>product_ids = user.get().getBrowsing_histories().stream().map(BrowsingProductDetailHistory::getProductId).collect(Collectors.toList());

            browsingProductImages = this.productServiceHelper.getBrowsingImagesByProductIds(product_ids);

            List<Product>inspiredProducts = this.productServiceHelper.getInspiredProducts(product_ids);

            inspiredsDto = MapperUtils.mapperList(inspiredProducts, ProductSearchDto.class);

            ProductsBrowsingResultDto result = ProductsBrowsingResultDto.builder()
                    .browsingProductsImages(browsingProductImages)
                    .inspiredProducts(inspiredsDto)
                    .build();

            return result;
        }
        if(user.isEmpty() || browsingProductImages.size()==0 && categoryId!=null){
            List<Product>  inspiredProducts = this.productRepository.findAllByCategoryId(categoryId);
            inspiredsDto = MapperUtils.mapperList(inspiredProducts, ProductSearchDto.class);
            ProductsBrowsingResultDto result = ProductsBrowsingResultDto.builder()
                    .browsingProductsImages(browsingProductImages)
                    .inspiredProducts(inspiredsDto)
                    .build();

            return result;
        }
        return null;
    }


}

@Component
class ProductServiceHelper{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ConvertDataUtils utils;

    public void saveImages(MultipartFile mainImage, MultipartFile[] extraImages, Product savedProduct) throws IOException {
        if (!mainImage.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImage.getOriginalFilename());
            String uploadDir = "../product-images/" + savedProduct.getId();

            FileUploadUtils.cleanDirectory(uploadDir);

            FileUploadUtils.saveFile(uploadDir, fileName, mainImage);

            setMainImage(mainImage, savedProduct);

        }

        if(extraImages.length>0){
            String uploadDir = "../product-images/" + savedProduct.getId() + "/extras";
            for(MultipartFile image: extraImages){
                if (image.isEmpty()) continue;
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
                FileUploadUtils.saveFile(uploadDir, fileName, image);
            }
            setExtraImages(extraImages, savedProduct);
        }
    }

    public void setMainImage(MultipartFile mainImage, Product product){
        if(!mainImage.isEmpty()){
            String fileName = StringUtils.cleanPath(mainImage.getOriginalFilename());
            product.setMainImage(fileName);
            this.productRepository.save(product);
        }
    }

    public void setExtraImages(MultipartFile[] extraImages, Product product){
        if(extraImages.length > 0){
            for (MultipartFile extraImg : extraImages) {
                if (!extraImg.isEmpty()) {
                    String fileName = StringUtils.cleanPath(extraImg.getOriginalFilename());

                    ProductImage image = ProductImage.builder()
                            .product(product)
                            .name(fileName)
                            .build();
                    if(product.getExtraImages()==null){
                        product.setExtraImages(new HashSet<>());
                    }
                    product.getExtraImages().add(image);

                    productRepository.save(product);
                }
            }
        }
    }

    public List<String> getBrowsingImagesByProductIds(List<Integer> product_ids){
        Iterable<Product> productIterable = productRepository.findAllById(product_ids);


        List<Product>tempProducts = this.utils.convertIterableToList(productIterable);

        return tempProducts.stream().map(Product::getMainImage).collect(Collectors.toList());
    }

    public List<Product> getInspiredProducts(List<Integer>product_ids){
        Iterable<Product> productIterable = productRepository.findAllById(product_ids);

        List<Product>browsingProducts = this.utils.convertIterableToList(productIterable);

        List<Product>products = productRepository.findAllInspiredProductsByProductList(browsingProducts);

        return products;

    }




}
