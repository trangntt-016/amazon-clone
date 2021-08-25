import { ProductSearchDto } from './ProductSearchDto';

export interface ProductBrowsingHistoryResult {
  browsingProductsImages: string[];
  inspiredProducts: ProductSearchDto[];
}
