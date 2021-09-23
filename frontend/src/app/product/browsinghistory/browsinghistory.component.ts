import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { ProductBrowsingHistoryResult } from '../../model/ProductBrowsingHistoryResult';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-browsinghistory',
  templateUrl: './browsinghistory.component.html',
  styleUrls: ['./browsinghistory.component.css']
})
export class BrowsinghistoryComponent implements OnInit {
  browsingProductHistoryResult: ProductBrowsingHistoryResult;
  categoryId: number;

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    let categoryId = this.activatedRoute.snapshot.queryParams.categoryId;
    categoryId = (categoryId === undefined) ? localStorage.getItem('categoryId') : categoryId;
    console.log(categoryId);

    this.productService.getBrowsingHistoryProducts(categoryId).subscribe(productHistoryResult =>{
      productHistoryResult.inspiredProducts = productHistoryResult.inspiredProducts.splice(0,5);
      productHistoryResult.browsingProductsImages = productHistoryResult.browsingProductsImages.splice(0,6);
      this.browsingProductHistoryResult = productHistoryResult;
    });
  }

}
