import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { ProductBrowsingHistoryResult } from '../../model/ProductBrowsingHistoryResult';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-browsinghistory',
  templateUrl: './browsinghistory.component.html',
  styleUrls: ['./browsinghistory.component.css']
})
export class BrowsinghistoryComponent implements OnInit {
  browsingProductHistoryResult: ProductBrowsingHistoryResult;
  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const categoryId = this.activatedRoute.snapshot.queryParams["categoryId"];

    this.productService.getBrowsingHistoryProducts(categoryId).subscribe(productHistoryResult =>{
      productHistoryResult.inspiredProducts = productHistoryResult.inspiredProducts.splice(0,5);
      productHistoryResult.browsingProductsImages = productHistoryResult.browsingProductsImages.splice(0,6);
      this.browsingProductHistoryResult = productHistoryResult;
    });
  }

}
