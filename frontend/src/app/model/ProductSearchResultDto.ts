import { ProductSearchDto } from './ProductSearchDto';
import { SellerCheckbox } from './SellerCheckbox';
import { BrandCheckBox } from './BrandCheckBox';

export interface ProductSearchResultDto{
  keyword: string;
  totalResults: number;
  products: ProductSearchDto[];
  sellers: SellerCheckbox[];
  brands: BrandCheckBox[];
}
