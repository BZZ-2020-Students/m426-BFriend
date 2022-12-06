import { Component, OnInit } from '@angular/core';
import {HomeService} from "./home.service";
import {catchError, Observable, of} from "rxjs";
import {User} from "./User";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userName = 'Alexandra';

  users: Observable<User>;
  errorMsg: string | undefined;
  error = "404"

  constructor(private homeService: HomeService) {
    this.users = this.homeService
      .getUsers()
      .pipe(
        catchError(error => {
          if (error.error instanceof ErrorEvent) {
            this.errorMsg = `Error: ${error.error.message}`;
          } else {
            this.errorMsg = `Error: ${error.message}`;
          }
          return of([]);
        })
      );
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.users = this.homeService.getUser();
  }

}
