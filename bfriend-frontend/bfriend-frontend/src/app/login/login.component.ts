import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {HomeService} from "../home/home.service";

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  //authForm: FormControl = new FormControl('');
  loginForm: FormGroup = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl('', [
        Validators.minLength(6),
        Validators.required
      ]),
    }
  );
  error = '';

  constructor(private loginService: LoginService, private router: Router, private homeService: HomeService) {
    this.homeService
      .getUser()
      .pipe()
      .subscribe({
        next: () => {
          this.router.navigate(['/home']);
        }
      });
  }


  ngOnInit(): void {
  }


  onSubmit() {
    let email = this.loginForm.controls['email'].value;
    let password = this.loginForm.controls['password'].value;
    let email_validation = this.loginForm.controls['email'].value;
    let password_validation = this.loginForm.controls['password'].value;


    this.loginService.postLogin(email, password)
      .pipe()
      // handle errors as well
      .subscribe({
        next: () => {
          this.router.navigate(['/home']);
        },
        error: error => {
          if (error.message.includes('401')) {
            this.error = 'Incorrect login data!! Please enter the right credentials';
          } else if (error.message.includes('400')) {
            this.error = 'Please enter data!!';
          }

        },
      });
  }


}



