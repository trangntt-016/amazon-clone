import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductSearchDto } from '../../model/ProductSearchDto';
import { ProductSearchResultDto } from '../../model/ProductSearchResultDto';
import { BrandCheckBox } from '../../model/BrandCheckBox';
import { ProductHelper } from '../../utils/ProductHelper';
import * as JsonData from '../../../assets/criteria.json';
import { QueryParams } from '../../model/QueryParams';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  helper: ProductHelper;
  queryParams: QueryParams;
  shortBrands: BrandCheckBox[];
  isViewMore = false;
  searchResult: ProductSearchResultDto;
  products: ProductSearchDto[];
  hasNext: boolean;
  hasPrevious: boolean;
  priceCriteria = JsonData.priceCriteria;
  featureCriteria = JsonData.feature;
  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.helper = new ProductHelper();


    this.activatedRoute.queryParams.subscribe(params => {
      this.queryParams = this.helper.extractQueryParams(params);
      this.queryParams.pageIdx = parseInt(String(this.queryParams.pageIdx));
      localStorage.setItem('categoryId', this.queryParams.categoryId.toString());

      this.productService.getProductsByCategoryIdKeyword(
        this.queryParams.categoryId,
        this.queryParams.keyword,
        this.queryParams.pageIdx,
        this.queryParams.perPage,
        this.queryParams.brandIds,
        this.queryParams.priceStart,
        this.queryParams.priceEnd,
        this.queryParams.sortType
      ).subscribe(result => {

        this.searchResult = result;
        this.products = this.searchResult.products;
        this.shortBrands = this.searchResult.brands.slice(0, 40);
        if ((this.searchResult.products.length === 40 && this.searchResult.totalResults >= 40 * (this.queryParams.pageIdx + 1))){
          this.hasNext = true;
        }
        else if (this.searchResult.products.length < 40){
          this.hasNext = false;
        }
        if (this.queryParams.pageIdx === 0){
          this.hasPrevious = false;
        }
        else if (this.queryParams.pageIdx > 0){
          this.hasPrevious = true;
        }

        if(this.queryParams.brandIds !== null){
          this.searchResult.brands.forEach( b => {
            if (!this.helper.isUnique(this.queryParams.brandIds, b.id)){
              b.isChecked = true;
            }
          });
        }

      });
    });
  }

  filterByBrand(checkedBrand: BrandCheckBox): void{
    // check if user checks true and not adds this brandId to url
    if (checkedBrand.isChecked === true && this.helper.isUnique(this.queryParams.brandIds, checkedBrand.id)){
      if (this.queryParams.brandIds === null) this.queryParams.brandIds = [];
      this.queryParams.brandIds.push(checkedBrand.id);
    }
    // in case user wants to remove this brandId off the url
    else{
      const idx = this.queryParams.brandIds.indexOf(checkedBrand.id);
      this.queryParams.brandIds.splice(idx, 1);
    }
    this.router.navigate([`products`], {queryParams:
        {
          categoryId: this.queryParams.categoryId,
          keyword: this.queryParams.keyword,
          pageIdx: this.queryParams.pageIdx,
          perPage: this.queryParams.perPage,
          brandId: this.queryParams.brandIds.toString(),
          priceStart: this.queryParams.priceStart,
          priceEnd: this.queryParams.priceStart,
          sortType: this.queryParams.sortType
        }
    });

  }

  filterByPrice(criterion: any): void{
    this.router.navigate([`products`], {queryParams:
        {
          categoryId: this.queryParams.categoryId,
          keyword: this.queryParams.keyword,
          pageIdx: this.queryParams.pageIdx,
          perPage: this.queryParams.perPage,
          brandId: this.queryParams.brandIds,
          sortType: this.queryParams.sortType,
          priceStart: criterion.greaterThan,
          priceEnd: criterion.lessThan
        }
    });
  }

  // filterBySeller(): void{
  //   console.log(this.searchResult.sellers.filter(s => s.isChecked == true));
  // }

  sortByFeature(sortType): void{
    this.router.navigate([`products`], {queryParams:
        {
          categoryId: this.queryParams.categoryId,
          keyword: this.queryParams.keyword,
          pageIdx: this.queryParams.pageIdx,
          perPage: this.queryParams.perPage,
          brandId: this.queryParams.brandIds,
          priceStart: this.queryParams.priceStart,
          priceEnd: this.queryParams.priceEnd,
          sortType: sortType.value
        }
    });
  }
  nextPage(): void{
    if (this.searchResult.products.length < this.searchResult.totalResults){
      this.queryParams.pageIdx += 1;
      this.router.navigate([`products`], {queryParams:
          {
            categoryId: this.queryParams.categoryId,
            keyword: this.queryParams.keyword,
            pageIdx: this.queryParams.pageIdx,
            perPage: this.queryParams.perPage,
            brandId: this.queryParams.brandIds,
            priceStart: this.queryParams.priceStart,
            priceEnd: this.queryParams.priceEnd,
            sortType: this.queryParams.sortType
          }
      });
    }
  }

  previousPage(): void{
    if (this.queryParams.perPage > 0){
      this.queryParams.pageIdx -= 1;
      this.router.navigate([`products`], {queryParams:
          {
            categoryId: this.queryParams.categoryId,
            keyword: this.queryParams.keyword,
            pageIdx: this.queryParams.pageIdx,
            perPage: this.queryParams.perPage,
            brandId: this.queryParams.brandIds,
            priceStart: this.queryParams.priceStart,
            priceEnd: this.queryParams.priceEnd,
            sortType: this.queryParams.sortType
          }
      });
    }
  }

  viewMore(): void{
    this.isViewMore = !this.isViewMore;
  }
}
