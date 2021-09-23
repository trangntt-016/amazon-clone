import { BrandCheckBox } from './BrandCheckBox';
import { Category } from './Category';

export interface ProductDetailsDto{
  id: number;

  name: number;

  alias: string;

  fullDescription: string;

  quantity: number;

  discountPrice: number;

  price: number;

  largeImage: string;

  discountStart: number;

  discountEnd: Date;

  treeCategories: Category[];

  brand: BrandCheckBox;

  category: Category;

  images: string[];
}
