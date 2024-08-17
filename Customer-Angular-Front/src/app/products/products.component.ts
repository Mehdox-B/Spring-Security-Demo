import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit{
  public products!:any
  constructor(private http:HttpClient) {

  }
  ngOnInit() {
    this.http.get("http://localhost:9091/products")
      .subscribe({
        next:(Data)=>{
          this.products = Data
        },
        error:(error)=>{
          console.log(error)
        },
        complete:()=>{
          console.log("The Data is Fetched Successfully")
        }
      })
  }
}
