import { Component, ViewChild, ElementRef, HostListener, OnInit, ViewContainerRef, TemplateRef, Input } from '@angular/core';
import {
  animate,
  AnimationBuilder,
  AnimationFactory,
  AnimationPlayer,
  style,
} from '@angular/animations';
import { Brand } from '../model/Brand';
import { CategoryService } from '../service/category.service';
import { HttpClient, HttpEvent, HttpEventType, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductService } from '../service/product.service';
import { ProductDto } from '../model/ProductDto';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit{
  title = 'File-Upload-Save';
  product: ProductDto;
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  selectedFile = null;
  changeImage = false;

  constructor(private uploadService: ProductService){}

  ngOnInit(): void {
    this.product = new ProductDto();
    console.log(this.product.extraImages.length);
    }

  downloadFile(){
    const link = document.createElement('a');
    link.setAttribute('target', '_blank');
    link.setAttribute('href', '_File_Saved_Path');
    link.setAttribute('download', 'file_name.pdf');
    document.body.appendChild(link);
    link.click();
    link.remove();
  }

  change($event) {
    this.changeImage = true;
  }

  changedImage(event) {
    this.selectedFile = event.target.files[0];
  }

  upload() {
    this.progress.percentage = 0;


    this.uploadService.pushFileToStorage(this.product.extraImages).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress.percentage = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          alert('File Successfully Uploaded');
        }


        this.selectedFiles = undefined;
      }
    );
  }

  selectFile(event, index) {
    this.selectedFiles = event.target.files;
    this.product.extraImages[index] = event.target.files.item(0);
  }


}
