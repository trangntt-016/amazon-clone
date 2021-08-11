import { NgModule } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';

@NgModule({
  imports: [
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCheckboxModule
  ],
  exports: [
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCheckboxModule
  ]
})
export class MaterialModule {}
