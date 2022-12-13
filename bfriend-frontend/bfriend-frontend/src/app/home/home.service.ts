import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from 'rxjs';
import {User} from "./model/User";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class HomeService implements OnInit {
  private BACKEND_URL = `${environment.BACKEND_URL}`;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  getUser(): Observable<User> {
    let headers = new HttpHeaders();
    headers = headers
      .set('Content-Type', 'application/json; charset=utf-8')
      .set('Accept', 'application/json');
    return this.http.get<User>(`${this.BACKEND_URL}/auth/infos`, {headers, withCredentials: true});
  }
}
