import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Observable} from 'rxjs';
import {User} from "./User";


@Injectable({
  providedIn: 'root'
})
export class HomeService implements OnInit {

  userName = 'Alexandra';
  userObject: User | undefined;
  errorMsg: string | undefined;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  getUser(): Observable<User> {
    let headers = new HttpHeaders();
    headers = headers
      .set('Content-Type', 'application/json; charset=utf-8')
      .set('Accept', 'application/json');
    return this.http.get<User>('http://localhost:8080/api/auth/infos', {headers, withCredentials: true});
  }

  private getServerErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 400: {
        return `Cookie wrong: ${error.message}`;
      }
      case 404: {
        return `Not Found: ${error.message}`;
      }
      case 403: {
        return `Access Denied: ${error.message}`;
      }
      case 500: {
        return `Internal Server Error: ${error.message}`;
      }
      default: {
        return `Unknown Server Error: ${error.message}`;
      }
    }
  }
}
