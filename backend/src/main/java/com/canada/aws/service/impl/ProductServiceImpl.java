package com.canada.aws.service.impl;

import com.canada.aws.dto.ProductDto;
import com.canada.aws.model.Brand;
import com.canada.aws.model.Category;
import com.canada.aws.model.Product;
import com.canada.aws.model.ProductImage;
import com.canada.aws.repo.BrandRepository;
import com.canada.aws.repo.CategoryRepository;
import com.canada.aws.repo.ProductRepository;
import com.canada.aws.service.ProductService;
import com.canada.aws.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

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

        saveImages(productDto.getMainImage(), productDto.getExtraImages(), savedProduct);

        return savedProduct;
    }

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

}
