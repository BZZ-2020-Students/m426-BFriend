import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {HomeServiceService} from "./home-service.service";
import {FormControl, FormGroup} from "@angular/forms";

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']

})
export class HomeComponent implements OnInit {
  //authForm: FormControl = new FormControl('');
  loginForm: FormGroup = new FormGroup({
      email: new FormControl(''),
      password: new FormControl(''),
    }
  );
  //email: FormControl = new FormControl('', [Validators.required, Validators.email]);
  //password: FormControl = new FormControl('', [Validators.required]);
  //loginForm: FormGroup | any;


  loading = false;
  submitted = false;
  error = '';
  success = '';

  //public email: string = '';


  constructor(private homeService: HomeServiceService, private http: HttpClient) {
  }


  ngOnInit(): void {
    /*
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
*/
    console.log("init")
  }


  onSubmit() {
    let email = this.loginForm.controls['email'].value;
    let password = this.loginForm.controls['password'].value;

    this.homeService.postLogin(email, password)
      .subscribe((data) => {
          this.error = '';
          this.success = data.toString();
        },
        (err: HttpErrorResponse) => {
          this.error = err.message;
          this.success = "";
        }
      )
    /*
    this.submitted = true;
    this.homeService.getData()

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.error = error;
          this.loading = false;
        });
        */

  }

  get f() {
    return this.loginForm.controls;
  }


}