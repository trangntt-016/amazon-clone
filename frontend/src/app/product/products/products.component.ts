import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductSearchDto } from '../../model/ProductSearchDto';
import { ProductSearchResultDto } from '../../model/ProductSearchResultDto';
import { BrandCheckBox } from '../../model/BrandCheckBox';
import { ProductHelper } from '../../utils/ProductHelper';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  helper: ProductHelper;
  selectedKeyword: string;
  selectedBrands: number[] = [];
  currentPage: number;
  searchResult: ProductSearchResultDto;
  products: ProductSearchDto[];
  hasNext: boolean;
  hasPrevious: boolean;
  priceCriteria = [];
  featureCriteria = [];
  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.helper = new ProductHelper();

    this.featureCriteria = [
      {
        name:"Price: Low to High",
        alias:"asc"
      },
      {
        name: "Price: High to Low",
        alias:"desc"
      },
      {
        name: "Newest Arrivals",
        alias: "newest"
      }
    ];
    this.priceCriteria = [
      {
        name:"Under $25",
        greaterThan:0,
        lessThan: 25
      },
      {
        name:"$25 to $50",
        greaterThan:25,
        lessThan: 50
      },
      {
        name:"$50 to $100",
        greaterThan:25,
        lessThan: 50
      },
      {
        name:"$100 & Above",
        greaterThan: 100,
        lessThan: 10000
      }
    ];

    this.activatedRoute.queryParams.subscribe(params => {
      let categoryId =+ params['categoryId'];
      if (params['brandId'] != undefined){
        this.selectedBrands = this.helper.removeDuplicateInArray(params['brandId']);
      }
      const keyword = params["keyword"];

      this.currentPage = parseInt (params["pageIdx"]);
      this.selectedKeyword = keyword;
      this.productService.getProductsByCategoryIdKeyword(categoryId, keyword, this.currentPage,40, this.selectedBrands.toString()).subscribe(result => {

        this.searchResult = result;
        this.products = this.searchResult.products;

        if ((this.searchResult.products.length === 40 && this.searchResult.totalResults >= 40 * (this.currentPage + 1))){
          this.hasNext = true;
        }
        else if(this.searchResult.products.length < 40){
          this.hasNext = false;
        }
        if(this.currentPage === 0){
          this.hasPrevious = false;
        }
        else if(this.currentPage > 0){
          this.hasPrevious = true;
        }

        this.searchResult.brands.forEach( b => {
          if(!this.helper.isUnique(this.selectedBrands, b.id)){
            b.isChecked = true;
          }
        })
      });
    });
  }

  filterByBrand(checkedBrand: BrandCheckBox): void{
    const categoryId = this.activatedRoute.snapshot.queryParams["categoryId"];

    //check if user checks true and not adds this brandId to url
    if(checkedBrand.isChecked===true && this.helper.isUnique(this.selectedBrands, checkedBrand.id)){
      this.selectedBrands.push(checkedBrand.id);
      this.router.navigate([`products`], {queryParams:
          {
            categoryId,
            keyword: this.selectedKeyword,
            pageIdx: 0,
            perPage: 40,
            brandId: this.selectedBrands.toString()
          }
      });
    }
    // in case user wants to remove this brandId off the url
    else{
      let idx = this.selectedBrands.indexOf(checkedBrand.id);
      
    }

  }

  filterByPrice(i:number): void{
    console.log(this.priceCriteria[i].lessThan);
  }

  filterBySeller(): void{
    console.log(this.searchResult.sellers.filter(s => s.isChecked==true));
  }

  nextPage(): void{
    const perPage = parseInt(this.activatedRoute.snapshot.queryParams["perPage"]);
    let pageIdx = parseInt(this.activatedRoute.snapshot.queryParams["pageIdx"]);
    let categoryId = this.activatedRoute.snapshot.queryParams["categoryId"];
    if (this.searchResult.products.length < this.searchResult.totalResults){
      pageIdx += 1;
      this.router.navigate([`products`], {queryParams:
          {
            categoryId: categoryId,
            keyword: this.selectedKeyword,
            pageIdx: pageIdx,
            perPage: perPage
          }
      });
    }
  }

  previousPage(): void{
    const perPage = parseInt(this.activatedRoute.snapshot.queryParams["perPage"]);
    let pageIdx = parseInt(this.activatedRoute.snapshot.queryParams["pageIdx"]);
    let categoryId = this.activatedRoute.snapshot.queryParams["categoryId"];
    if (perPage>0){
      pageIdx -= 1;
      this.router.navigate([`products`], {queryParams:
          {
            categoryId: categoryId,
            keyword: this.selectedKeyword,
            pageIdx: pageIdx,
            perPage: perPage
          }
      });
    }
  }
}
