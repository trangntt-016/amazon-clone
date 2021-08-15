import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../service/category.service';
import { Category } from '../../model/Category';

@Component({
  selector: 'app-seller-newproduct',
  templateUrl: './seller-newproduct.component.html',
  styleUrls: ['./seller-newproduct.component.css']
})
export class SellerNewproductComponent implements OnInit {
  categories: Category[];
  shouldShowTree = false;
  tree = "";
  selectId = null;

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.categoryService.getRootCategories().subscribe((categories) =>{
      this.categories = categories;
      console.log(this.categories);
    })
  }

  selectCategory(category): void{
    this.shouldShowTree = true;

    if (category.isHasChildren){
      this.categories = category.children;
    }
    else if (!category.isHasChildren){
      this.selectId = category.id;
    }

    if (this.tree.indexOf(category.name) < 0){
      if (this.tree !== ''){
        this.tree = this.tree + ' > ';
      }
      this.tree =  this.tree + category.name;
    }


  }

  clearTreeCategory(){
    this.tree = '';
    this.shouldShowTree = false;
    this.categoryService.getRootCategories().subscribe((categories) =>{
      this.categories = categories;
    })
    this.selectId = null;
  }

  getLastCategory(tree: string): string{
    const lastIdx = tree.lastIndexOf('>');
    return tree.substring(0, lastIdx).trim();
  }

}
