import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product.service';
import { ProductDetailsDto } from '../model/ProductDetailsDto';
import { ActivatedRoute, Router } from '@angular/router';
import { CartItemService } from '../service/cart-item.service';
import { AuthService } from '../service/auth.service';
import { CartItemDto } from '../model/CartItemDto';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product: ProductDetailsDto;
  cart: CartItemDto;
  quantity = 1;

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private cartService: CartItemService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('productId');

    const userId = this.authService.readToken() ? this.authService.readToken().userId : '';
      this.cart = new CartItemDto(userId, id);

    this.productService.getProductByProductId(parseInt(id)).subscribe(p =>{
      this.product = p;
      this.product.largeImage = p.images[0];
      console.log(this.product);
    });
  }

  updateLargeImage(i): void{
    this.product.largeImage = i;
  }

  addToCart(): void{
    if (this.cart.userId !== undefined){
      this.cartService.addProductToCart(this.cart).subscribe((cart) => {
        console.log(cart);
      })
    }
  }

  viewcart(): void{
    this.router.navigate(['/buy/cart/view'])
  }

}
