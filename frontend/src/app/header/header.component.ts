import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { Category } from '../model/Category';
import { ProductService } from '../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductHelper } from '../utils/ProductHelper';
import { QueryParams } from '../model/QueryParams';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  rootCategories: Category[];
  queryParams: QueryParams;
  categoryPlaceHolder: string;

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const categoryId = this.activatedRoute.snapshot.queryParams["categoryId"];
    this.queryParams = new QueryParams();
    this.categoryService.getRootCategoriesForSearch().subscribe(c => {
      this.rootCategories = c;
      if (categoryId !== undefined) { this.categoryPlaceHolder = c.filter(c => c.id === parseInt(categoryId))[0].name; }
      else{
        this.categoryPlaceHolder = 'All Categories';
      }
    });
  }

  search(): void{
      this.router.navigate([`products`], {queryParams:
          {
            categoryId: this.queryParams.categoryId,
            keyword: this.queryParams.keyword,
            pageIdx: this.queryParams.pageIdx,
            perPage: this.queryParams.perPage
          }
      });

  }

}
