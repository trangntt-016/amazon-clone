import { Component, ViewChild, ElementRef, HostListener, OnInit, ViewContainerRef, TemplateRef, Input } from '@angular/core';
import {
  animate,
  AnimationBuilder,
  AnimationFactory,
  AnimationPlayer,
  style,
} from '@angular/animations';
import { Brand } from '../model/Brand';
import { CategoryService } from '../service/category.service';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  brands: Brand[];
  selectedBrand: Brand;

  addCustomUser = (term) => ({id: term, name: term});

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.categoryService.getBrandsFromCategoryId(1).subscribe(brands => {
      this.brands = brands;
    });
  }



}
