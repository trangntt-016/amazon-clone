import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { TestComponent } from './test/test.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { SellerStartComponent } from './seller/seller-start/seller-start.component';
import { SellerNewproductComponent } from './seller/seller-newproduct/seller-newproduct.component';
import { ProductsComponent } from './product/products/products.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { AddressComponent } from './address/address.component';
import { CartComponent } from './buy/cart/cart.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'products', component: ProductsComponent},
  {path: 'product/:productId', component: ProductDetailsComponent},
  {path: 'seller/start', component: SellerStartComponent},
  {path: 'seller/new-product', component: SellerNewproductComponent},
  {path: 'buy/addressselect', component: AddressComponent},
  {path: 'buy/cart/view', component: CartComponent},
  {path: 'test', component: TestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
