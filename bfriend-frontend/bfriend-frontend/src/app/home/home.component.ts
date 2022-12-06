import {Component, OnInit} from '@angular/core';
import {HomeService} from "./home.service";
import {catchError, of} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userName = 'Alexandra';

  errorMsg: string | undefined;
  error = "404"

  constructor(private homeService: HomeService) {
    this.homeService
      .getUser()
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
  }
}
