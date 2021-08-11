import { NgModule } from '@angular/core';

import {IvyCarouselModule} from 'angular-responsive-carousel';

@NgModule({
  imports: [
    IvyCarouselModule
  ],
  exports: [
    IvyCarouselModule
  ]
})
export class PackagesModule {
}
