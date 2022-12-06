import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  public BACKEND_URL = 'localhost:8080/';

  constructor(private http: HttpClient) {

  }

  private sendData(user: User) {
    this.http.post<User>('backendURL', user).subscribe(data => {})
  }

  private getHobbies(): Observable<string[]> {
    let hobbies = [];
    return this.http.get<string[]>(this.BACKEND_URL + 'api/hobby/all');
  }
}
