import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductSearchDto } from '../../model/ProductSearchDto';
import { Brand } from '../../model/Brand';
import { BrandCheckBox } from '../../model/BrandCheckBox';
import { SellerCheckbox } from '../../model/SellerCheckbox';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  categoryId: number;
  selectedKeyword: string;
  extractedKeyword: string;
  products: ProductSearchDto[];
  brands: BrandCheckBox[] = [];
  sellers: SellerCheckbox[] = [];
  priceCriteria = [];
  featureCriteria = [];
  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
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
    this.categoryId = this.activatedRoute.snapshot.queryParams["categoryId"];
    this.selectedKeyword = this.activatedRoute.snapshot.queryParams["keyword"];
    this.productService.getProductsByCategoryIdKeyword(this.categoryId, this.selectedKeyword, 0,10).subscribe(products => {
      this.products = products;
      this.products.forEach(p => {
        if (this.brands.filter(br => br.name === p.brand.name).length === 0){
          this.brands.push(p.brand);
        }
        if(this.sellers.filter(sl => sl.name === p.seller.name).length === 0){
          this.sellers.push(p.seller);
        }


      });
    })
  }

  filterByBrand(): void{
    console.log(this.brands.filter(b => b.isChecked==true));
  }

  filterByPrice(i:number): void{
    console.log(this.priceCriteria[i].lessThan);
  }

  filterBySeller(): void{
    console.log(this.sellers.filter(s => s.isChecked==true));
  }
}
