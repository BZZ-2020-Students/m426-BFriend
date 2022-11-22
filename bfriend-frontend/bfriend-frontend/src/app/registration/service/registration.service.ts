import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) {

  }

  private sendData(user: User) {
    this.http.post<User>('backendURL', user).subscribe(data => {})
  }
}
