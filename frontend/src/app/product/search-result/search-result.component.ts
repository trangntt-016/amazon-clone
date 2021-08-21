import { Component, Input, OnInit } from '@angular/core';
import { ProductSearchDto } from '../../model/ProductSearchDto';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {
  @Input() products: ProductSearchDto[];
  constructor() { }

  ngOnInit(): void {
  }

  updateImgError(e){
    console.log(e.target);
  }

}
