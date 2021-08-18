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

  category_id: number;

  brand_id: number;

  constructor(){
    this.name = '';
    this.fullDescription = '';
    this.createdTime = new Date();
    this.quantity = null;
    this.price = null;
    this.discountPrice = null;
    this.discountStart = null;
    this.discountEnd = null;
    this.mainImage = null;
    this.extraImages = new Array(8);
    this.category_id = null;
    this.brand_id = null;
  }
}
