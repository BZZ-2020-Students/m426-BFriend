import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/User";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  // get url from .env file
  public BACKEND_URL = `${environment.BACKEND_URL}`;

  constructor(private http: HttpClient) {

  }

  sendData(user: User) {
    this.http.post<User>('backendURL', user).subscribe(data => {})
  }

  getHobbies(): Observable<string[]> {
    return this.http.get<string[]>(this.BACKEND_URL + '/hobby/all');
  }
}
