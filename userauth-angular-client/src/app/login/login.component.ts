import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../authenticate.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  message:string='';
  userName: string='';
  password: string='';
  isAuthenticated: boolean=false;


  constructor(private service:AuthenticateService, private router:Router){

  }
  ngOnInit(): void {
    
  }
  doAuthenticate(){
    let response = this.service.authenticate(this.userName, this.password);
    
    response.subscribe( data => {
      console.log(data);
      if(data){
        this.isAuthenticated=true;
        sessionStorage.setItem('currentUser', data.firstName +' ' + data.lastName);
      }else{
        this.isAuthenticated=false;
        sessionStorage.clear();
      }
      this.router.navigate(['/email']);
    })
  }
}
