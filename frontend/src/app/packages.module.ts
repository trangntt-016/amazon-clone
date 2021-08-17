import { NgModule } from '@angular/core';

import {IvyCarouselModule} from 'angular-responsive-carousel';
import { NgSelectModule } from '@ng-select/ng-select';
import { AngularEditorModule } from '@kolkov/angular-editor';


@NgModule({
  imports: [
    IvyCarouselModule,
    NgSelectModule,
    AngularEditorModule
  ],
  exports: [
    IvyCarouselModule,
    NgSelectModule,
    AngularEditorModule
  ]
})
export class PackagesModule {
}
