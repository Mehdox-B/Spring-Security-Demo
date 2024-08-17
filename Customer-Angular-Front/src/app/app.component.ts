import {Component, OnInit} from '@angular/core';
import {SecuritysvcService} from "./services/securitysvc.service";
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'Customer-Angular-Front';
  public keycloakProfile!:any;
  constructor(public keycloakService:KeycloakService) {
  }
  ngOnInit() {
    if(this.keycloakService.isLoggedIn()){
      this.keycloakService.loadUserProfile().then((profile)=>{
        this.keycloakProfile = profile;
      })
    }
  }
  public login():void{
    this.keycloakService.login()
      .then(()=>{
        console.log("Login Successful")
      })
  }
  public logout():void{
    this.keycloakService.logout("http://localhost:4200")
      .then(()=>{
        console.log("Logout Successful")
      })
  }
}
