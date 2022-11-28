import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { FormGroup,FormControl, Validators } from '@angular/forms';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material'
declare var $:any;
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']

})
export class HomeComponent implements OnInit {
  //authForm: FormControl = new FormControl('');
  //loginForm: FormGroup = new FormGroup('');
    //email: FormControl = new FormControl('', [Validators.required, Validators.email]);
    //password: FormControl = new FormControl('', [Validators.required]);
  login:FormGroup|any;


  constructor(private _http:HttpClient, private _route:Router) { }


  ngOnInit(): void {
    this.login = new FormGroup({
      'email': new FormControl(),
      'password': new FormControl()
    })

  }

  onSubmit() {

  }

  logindata(login:FormGroup){
    this._http.get<any>("http://localhost:8080/")
      .subscribe(res=>{
        const user = res.find((a:any)=>{
          return a.email === this.login.value.email && a.password === this.login.value.password
        });

        if(user){
          alert('you are successfully login');
          this.login.reset();
          $('.form-box').css('display','none');
          this._route.navigate(['api/auth/login']);
        }else{
          alert('User Not Found');
          this._route.navigate(['home']);
        }

      }, err=>{
        alert('Something was wrong');
      })


  }



}
