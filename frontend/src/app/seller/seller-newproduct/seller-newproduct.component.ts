import { Component, OnInit } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { CategoryService } from '../../service/category.service';
import { Category } from '../../model/Category';
import { Brand } from '../../model/Brand';

@Component({
  selector: 'app-seller-newproduct',
  templateUrl: './seller-newproduct.component.html',
  styleUrls: ['./seller-newproduct.component.css']
})
export class SellerNewproductComponent implements OnInit {
  rootCategories: Category[];
  categories: Category[];
  shouldShowTree = false;
  brands: Brand[];
  tree = "";
  selectId = null;
  selectedBrand: Brand;
  placeHolderTextSelectBrand: string;

  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    minHeight: '250px',
    width: 'auto',
    translate: 'yes',
    enableToolbar: true,
    showToolbar: true,
    toolbarPosition: 'top',
    toolbarHiddenButtons: [
      ['insertVideo',
        'backgroundColor',
        'customClasses',
        'link',
        'unlink',
        'insertImage',
        'insertHorizontalRule',
      ]
    ]
  };

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.placeHolderTextSelectBrand = "Select a category's brand";

    this.categoryService.getRootCategories().subscribe((categories) =>{
      this.categories = categories;
      this.rootCategories = categories;
    });

  }

  selectCategory(category): void{
    this.shouldShowTree = true;

    if (category.isHasChildren){
      this.categories = category.children;
    }
    else if (!category.isHasChildren){
      this.selectId = category.id;
      const rootSelectedName = this.tree.split(">")[0].trim();
      this.placeHolderTextSelectBrand = rootSelectedName + "'s brands";
      const rootSelectedCategory = this.rootCategories.filter(c => c.name === rootSelectedName)[0];
      this.categoryService.getBrandsFromCategoryId(rootSelectedCategory.id).subscribe((brands) => {
        this.brands = brands;
      })
    }

    if (this.tree.indexOf(category.name) < 0){
      if (this.tree !== ''){
        this.tree = this.tree + ' > ';
      }
      this.tree =  this.tree + category.name;
    }


  }

  clearTreeCategory(): void{
    this.tree = '';
    this.shouldShowTree = false;
    this.categoryService.getRootCategories().subscribe((categories) =>{
      this.categories = categories;
    })
    this.selectId = null;
    this.placeHolderTextSelectBrand = "Select a category's brand";
    this.selectedBrand = null;
  }

  getLastCategory(tree: string): string{
    const lastIdx = tree.lastIndexOf('>');
    return tree.substring(0, lastIdx).trim();
  }

}
