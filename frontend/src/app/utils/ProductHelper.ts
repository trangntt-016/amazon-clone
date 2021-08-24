import { QueryParams } from '../model/QueryParams';

export class ProductHelper{
  constructor() {
  }

  public isUnique(numbers: number[], checkNum: number): boolean{
    if (numbers === null) return true;
    return numbers.filter(n => n === checkNum).length === 0 ;
  }

  public removeDuplicateInArray(numStr: string): number[]{
    let numsArray = numStr.split(",").map(s => parseInt(s)).sort((n1, n2) => n1 - n2);

    let slow = 0, countDup = 0;

    for(let fast = 1; fast < numsArray.length; fast++){
      if(numsArray[fast] !== numsArray[slow]){
        slow++;
        numsArray[slow] = numsArray[fast];
      }
      else{
        countDup++;
      }
    }

    if (countDup > 0){
      numsArray = numsArray.slice(0, numsArray.length - countDup - 1);
    }
    return numsArray;
  }

  public extractQueryParams(params): QueryParams{
    const qP = new QueryParams();

    qP.categoryId = (!isNaN(params.categoryId) ? params.categoryId : '');

    qP.keyword = (params.keyword !== undefined) ? params.keyword : '';

    qP.pageIdx = (!isNaN(params.pageIdx) ? params.pageIdx : 0);

    if (params.brandId !== '' && params.brandId !== undefined){
      qP.brandIds = this.removeDuplicateInArray(params.brandId.toString());
    }
    else{
      qP.brandIds = null;
    }


    qP.priceStart = (!isNaN(params.priceStart) ? params.priceStart : '');

    qP.priceEnd = (!isNaN(params.priceEnd) ? params.priceEnd : '');

    qP.sortType = (params.sortType !== undefined) ? params.sortType : '';

    return qP;
  }
}
