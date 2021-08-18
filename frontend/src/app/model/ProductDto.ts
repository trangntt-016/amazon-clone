import { Category } from './Category';
import { Brand } from './Brand';

export class ProductDto {
  name: string;

  fullDescription: string;

  createdTime: Date;

  quantity: number;

  price: number;

  discountPrice: number;

  discountStart: Date;

  discountEnd: Date;

  mainImage: File;

  extraImages: File[];

  category: Category;

  brand: Brand;

  constructor(){
    this.name = '';
    this.fullDescription = '';
    this.createdTime = new Date();
    this.quantity = 0;
    this.price = 0.0;
    this.discountPrice = 0;
    this.discountStart = null;
    this.discountEnd = null;
    this.mainImage = null;
    this.extraImages = new Array(8);
    this.category = null;
    this.brand = null;
  }
}
