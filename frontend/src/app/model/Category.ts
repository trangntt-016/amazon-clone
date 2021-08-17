export class Category{
  id: number;
  name: string;
  allParentIDs: string;
  parent: Category;
  children: Category[];
  isHasChildren: boolean;
  constructor() {
    this.name = "";
    this.allParentIDs = "";
    this.parent = null;
    this.children = null;
    this.isHasChildren = false;
  }
}
