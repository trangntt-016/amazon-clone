import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { TestComponent } from './test/test.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { SellerStartComponent } from './seller/seller-start/seller-start.component';
import { SellerNewproductComponent } from './seller/seller-newproduct/seller-newproduct.component';
import { ProductsComponent } from './product/products/products.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'products', component: ProductsComponent},
  {path: 'seller/start', component: SellerStartComponent},
  {path: 'seller/new-product', component: SellerNewproductComponent},
  {path: 'test', component: TestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
