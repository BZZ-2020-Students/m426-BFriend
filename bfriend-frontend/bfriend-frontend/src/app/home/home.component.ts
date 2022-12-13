import {Component, OnInit} from '@angular/core';
import {User} from "./model/User";
import {AppService} from "../app.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  firstname = '';
  lastname = '';
  profilepicture = '';

  constructor(private globalService: AppService) {
    globalService.getUser().then((user: User) => {
      this.firstname = user.firstName;
      this.lastname = user.lastName;
      this.profilepicture = user.profilepicture;
    });
  }

  ngOnInit(): void {

  }
}
