import { Component, OnInit } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { CategoryService } from '../../service/category.service';
import { Category } from '../../model/Category';
import { Brand } from '../../model/Brand';
import { ProductDto } from '../../model/ProductDto';
import { ProductService } from '../../service/product.service';

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
  selectedBrand: string;
  placeHolderTextSelectBrand: string;
  product: ProductDto;
  urls: string[] | ArrayBuffer[] = [];
  selectedImageIdx: number;

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
    private categoryService: CategoryService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.placeHolderTextSelectBrand = 'Select a category\'s brand';
    this.product = new ProductDto();

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
      let rootSelectedName = null;
      if (this.tree.length > 0){
        rootSelectedName = this.tree.split(">")[0].trim();
      }
      else{
        rootSelectedName = category.name;
      }

      const rootSelectedCategory = this.rootCategories.filter(c => c.name === rootSelectedName)[0];

      this.categoryService.getBrandsFromCategoryId(rootSelectedCategory.id).subscribe((brands) => {
        this.brands = brands;
      })

      this.product.category = rootSelectedCategory;
      this.placeHolderTextSelectBrand = rootSelectedName + "'s brands";
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
    this.product.category = null;
  }



  onSelectFile(event): void {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]); // read file as data url

      reader.onload = (event) => { // called once readAsDataURL is completed
        this.urls[this.selectedImageIdx] = event.target.result;
      };
      this.product.extraImages[this.selectedImageIdx] = event.target.files.item(0);
    }
  }

  submit(): void{
    const data: FormData = new FormData();

    //this.product.brand = this.brands.filter(b => b.name == this.selectedBrand)[0];

    if (this.product.extraImages[0] !== null) {
     this.product.mainImage = this.product.extraImages[0];
     data.append('mainImage', this.product.mainImage);
    }
    // if (this.product.extraImages.length > 0) {
    //   this.product.extraImages = this.product.extraImages.filter(image => image.name !== null);
    //   this.product.extraImages.forEach(p => {
    //     data.append('mainImages',p);
    //   });
    // }
    //console.log(data.getAll('mainImages'));
    this.productService.createAProduct(data);
    }


  getIndex(i: number): void{

    this.selectedImageIdx = i;
  }
}
