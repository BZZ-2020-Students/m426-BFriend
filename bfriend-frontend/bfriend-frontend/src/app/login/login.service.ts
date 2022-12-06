import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LoginService implements OnInit {
  private BACKEND_URL = `${environment.BACKEND_URL}`;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {

  }

  postLogin(email: String, password: String) {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8')
      .set('Accept', 'application/json');
    return this.http.post(`${this.BACKEND_URL}/auth/login`,
      `{
      "email": "${email}",
      "password": "${password}"
    }`, {headers: headers, withCredentials: true});
  }
}
