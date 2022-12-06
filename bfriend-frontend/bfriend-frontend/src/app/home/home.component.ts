import {Component, OnInit} from '@angular/core';
import {HomeService} from "./home.service";
import {User} from "./User";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userName = '';
  errorMsg: string = '';

  constructor(private homeService: HomeService) {
    let user: User;
    this.homeService
      .getUser()
      .pipe()
      .subscribe({
        next: data => {
          user = data;
          this.userName = user.firstName;
          console.log(user);
        },
        error: error => {
          this.errorMsg = error.message;
          console.log(error);
        },
        complete: () => {
          console.log('complete');
        }
      });
  }

  ngOnInit(): void {
  }
}
