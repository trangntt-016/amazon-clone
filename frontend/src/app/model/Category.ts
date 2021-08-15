export interface Category{
  id: number;
  name: string;
  allParentIDs: string;
  parent: Category;
  children: Category[];
  isHasChildren: boolean;
}
