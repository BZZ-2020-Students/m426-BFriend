import {Component, OnInit} from '@angular/core';
import {HomeService} from "./home.service";
import {User} from "./model/User";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  firstname = '';
  lastname = '';

  constructor(private homeService: HomeService, private router: Router) {
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
        error: () => {
          this.router.navigate(['/']);
        },
      });
  }

  ngOnInit(): void {
  }
}
