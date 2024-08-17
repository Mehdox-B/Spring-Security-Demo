import {Injectable} from '@angular/core';
import {KeycloakEventType, KeycloakService} from "keycloak-angular";
import {KeycloakProfile} from "keycloak-js"

@Injectable({
  providedIn: 'root'
})
export class SecuritysvcService {
  public keycloakProfile!:KeycloakProfile
  constructor(public kcService:KeycloakService) {
    this.init()
  }
  init(){
    this.kcService.keycloakEvents$.subscribe(
      {
        next:(event)=>{
          if(event.type == KeycloakEventType.OnAuthSuccess){
            this.kcService.loadUserProfile().then((profile)=>{
              this.keycloakProfile = profile;
            })
          }
        },
        error:(err)=>{
          console.log(`The Operation is failed due this error : ${err}`)
        },
        complete:()=>{
          console.log("The Keycloak Profile is Fetched Correctly")
        }
      }
    )
  }
  public hasRolesIn(roles:string[]):boolean{
    let userRoles:string[] = this.kcService.getUserRoles();
    for (let role in userRoles) {
      if(userRoles.includes(role))
        return true;
    }
    return false;
  }
}
