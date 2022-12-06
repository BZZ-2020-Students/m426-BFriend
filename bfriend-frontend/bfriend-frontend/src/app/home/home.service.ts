import {Injectable, OnInit} from '@angular/core';
import * as http from "http";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import { catchError, throwError } from 'rxjs';
import {Observable} from "rxjs";
import {User} from "./User";


@Injectable({
  providedIn: 'root'
})
export class HomeService implements OnInit{

  userName = 'Alexandra';
  userObject: User | any;
  errorMsg: string | undefined;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  getUsers(): Observable<User> {
    return this.http
      .get('http://localhost:8080/api/auth/infos')
      .pipe(
        catchError(error => {
          if (error.error instanceof ErrorEvent) {
            this.errorMsg = `Error: ${error.error.message}`;
          } else {
            this.errorMsg = this.getServerErrorMessage(error);
          }
          return throwError(this.errorMsg);
        })
      );
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

  getUser(): any{
    this.http.get('http://localhost:8080/api/auth/infos').subscribe(data =>
      this.userObject = data
    );
  }
}
