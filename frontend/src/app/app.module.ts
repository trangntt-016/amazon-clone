import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule} from './material.module';
import { PackagesModule} from './packages.module';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { TestComponent } from './test/test.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { SellerStartComponent } from './seller/seller-start/seller-start.component';
import { SellerNewproductComponent } from './seller/seller-newproduct/seller-newproduct.component';
import { SellerHeaderComponent } from './seller/seller-header/seller-header.component';
import { ProductsComponent } from './product/products/products.component';
import { SearchResultComponent } from './product/search-result/search-result.component';
import { BrowsinghistoryComponent } from './product/browsinghistory/browsinghistory.component';
import { ProductDetailsComponent } from './product-details/product-details.component';





@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    TestComponent,
    LoginComponent,
    RegisterComponent,
    SellerStartComponent,
    SellerNewproductComponent,
    SellerHeaderComponent,
    ProductsComponent,
    SearchResultComponent,
    BrowsinghistoryComponent,
    ProductDetailsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    PackagesModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
