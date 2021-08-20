import { Brand } from './Brand';
import { BrandCheckBox } from './BrandCheckBox';
import { SellerCheckbox } from './SellerCheckbox';

export interface ProductSearchDto {
  id: number;

  name: string;

  alias: string;

  price: number;

  discountPrice: number;

  discountStart: Date;

  discountEnd: Date;

  mainImage: string;

  brand: BrandCheckBox;

  seller: SellerCheckbox;
}
