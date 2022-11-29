import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {HomeServiceService} from "./home-service.service";
import {FormGroup} from "@angular/forms";
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
  loading = false;
  submitted = false;
  error = '';
  public email: string = '';

  constructor(private homeService: HomeServiceService) { }


  ngOnInit(): void {


  }

  onSubmit() {
    this.homeService.getData()
  }





}
