import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login.service";
import {FormControl, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  //authForm: FormControl = new FormControl('');
  loginForm: FormGroup = new FormGroup({
      email: new FormControl(''),
      password: new FormControl(''),
    }
  );
  error = '';

  constructor(private loginService: LoginService, private router: Router) {
  }


  ngOnInit(): void {
  }


  onSubmit() {
    let email = this.loginForm.controls['email'].value;
    let password = this.loginForm.controls['password'].value;

    this.loginService.postLogin(email, password)
      .pipe()
      // handle errors as well
      .subscribe({
        next: () => {
          this.router.navigate(['/home']);
        },
        error: error => {
          this.error = error.message;
          console.log(error);
        },
        complete: () => {
          console.log('complete');
        }
      });
  }
}
