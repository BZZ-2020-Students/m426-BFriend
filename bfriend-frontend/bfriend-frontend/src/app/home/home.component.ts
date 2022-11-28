import { Component, OnInit } from '@angular/core';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { FormGroup,FormControl, Validators } from '@angular/forms';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material'
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']

})
export class HomeComponent implements OnInit {
  //authForm: FormControl = new FormControl('');
  //loginForm: FormGroup = new FormGroup('');
    email: FormControl = new FormControl('', [Validators.required, Validators.email]);
    password: FormControl = new FormControl('', [Validators.required]);


  constructor() { }


  ngOnInit(): void {

  }

  onSubmit() {

  }



}
