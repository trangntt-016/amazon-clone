export class ProductHelper{
  constructor() {
  }

  public isUnique(numbers: number[], checkNum: number): boolean{
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
}
