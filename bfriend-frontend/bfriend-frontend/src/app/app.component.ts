import {Component} from '@angular/core';
import {AppService} from "./app.service";
import {Router} from "@angular/router";
import {HomeService} from "./home/home.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'bfriend-frontend';

  constructor(private myGlobals: AppService, private router:  Router, private homeService: HomeService, private globalService: AppService) {
    this.homeService
      .getUser()
      .pipe()
      .subscribe({
        next: (data) => {
          this.globalService.setUser(data);
          this.router.navigate(['/home']);
        },
        error: () => {
          // if url contains anything else than login, redirect to login, if it contains register, redirect to register
          if (window.location.href.indexOf('login') === -1 && window.location.href.indexOf('register') === -1) {
            this.router.navigate(['/']);
          } else if (window.location.href.indexOf('register') !== -1) {
            this.router.navigate(['/register']);
          }
        }
      });
  }
}
