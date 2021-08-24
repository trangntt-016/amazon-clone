export class QueryParams{
  categoryId: number;
  keyword: string;
  pageIdx: number;
  perPage: number;
  brandIds: number[];
  priceStart: number;
  priceEnd: number;
  sortType: string;

  constructor() {
    this.categoryId = null;
    this.keyword = null;
    this.pageIdx = 0;
    this.perPage = 40;
    this.brandIds = null;
    this.priceStart = null;
    this.priceEnd = null;
    this.sortType = null;
  }
}
