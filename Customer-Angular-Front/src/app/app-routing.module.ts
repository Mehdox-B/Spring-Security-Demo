import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerComponent} from "./customer/customer.component";
import {ProductsComponent} from "./products/products.component";
import {AuthGuard} from "./guard/auth-guard.guard";

const routes: Routes = [
  {
    path:"customers",
    component:CustomerComponent
  },
  {
    path:'products',
    component:ProductsComponent,
    canActivate:[AuthGuard],
    data:{
      roles:["Admin"]
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
