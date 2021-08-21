import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { Category } from '../model/Category';
import { ProductService } from '../service/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  selectedCategory = '';
  selectedKeyword = '';
  rootCategories: Category[];

  constructor(
    private categoryService: CategoryService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.categoryService.getRootCategoriesForSearch().subscribe(c => {
      this.rootCategories = c;
    });
  }

  search(): void{
    if(this.selectedKeyword != ''){
      this.router.navigate([`products`], {queryParams:
          {
            categoryId: this.selectedCategory,
            keyword: this.selectedKeyword,
            pageIdx: 0,
            perPage: 10
          }
      });
    }
  }

}
