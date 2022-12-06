import {Component, OnInit} from '@angular/core';
import {HomeService} from "./home.service";
import {User} from "./model/User";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  firstname = '';
  lastname = '';
  errorMsg: string = '';

  constructor(private homeService: HomeService) {
    let user: User;
    this.homeService
      .getUser()
      .pipe()
      .subscribe({
        next: data => {
          user = data;
          this.firstname = user.firstName;
          this.lastname = user.lastName;
        },
        error: error => {
          this.errorMsg = error.message;
          console.log(error);
        },
      });
  }

  ngOnInit(): void {
  }
}
