import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Login} from "./userLogin";

@Injectable({
  providedIn: 'root'
})
export class HomeServiceService implements OnInit {

  public email: string = '';
  public password: string = '';

  constructor(private http: HttpClient) {
  }

  ngOnInit() {

  }

  postLogin(email: String, password: String) {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/api/auth/login",
      `{
      "email": "${email}",
      "password": "${password}"
    }`, {headers : headers})
  }


}
