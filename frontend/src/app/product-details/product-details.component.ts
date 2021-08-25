import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product.service';
import { ProductDetailsDto } from '../model/ProductDetailsDto';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product: ProductDetailsDto;
  images = [
    { path: 'https://images-na.ssl-images-amazon.com/images/G/15/kindle/journeys/MTkyOWU3NTIt/MTkyOWU3NTIt-M2M0Yjg3OGMt-w1500._CB664769611_.jpg' },
    { path: 'https://images-na.ssl-images-amazon.com/images/G/15/digital/video/Magellan_MLP/BRND_MTH21_GWBleedingHero_1500x600_Final_Final_en-CA_ENG_PVD7256._CB645030199_.jpg' },
    { path: 'https://images-na.ssl-images-amazon.com/images/G/15/kindle/journeys/MzRmNjQ5NDEt/MzRmNjQ5NDEt-ZWRiMjk3NmEt-w1500._CB664783629_.jpg' },
    { path: 'https://images-na.ssl-images-amazon.com/images/G/15/kindle/journeys/YzllYTYxOGQt/YzllYTYxOGQt-ODEzZmMyMTEt-w1500._CB663476491_.jpg' },
    { path: 'https://images-na.ssl-images-amazon.com/images/G/15/kindle/journeys/YTdjY2M2Mzct/YTdjY2M2Mzct-NTY0Mzg1MzMt-w1500._CB663467513_.jpg' }
  ];
  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get("productId");
    this.productService.getProductByProductId(parseInt(id)).subscribe(p =>{
      this.product = p;
      this.product.largeImage = p.images[0];
      console.log(p);
    });
  }

  updateLargeImage(i): void{
    this.product.largeImage = i;
  }

}
