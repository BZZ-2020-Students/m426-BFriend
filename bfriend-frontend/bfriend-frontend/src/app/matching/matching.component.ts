import { Component, OnInit } from '@angular/core';
import {AppService} from "../app.service";
import {User} from "../home/model/User";

@Component({
  selector: 'app-matching',
  templateUrl: './matching.component.html',
  styleUrls: ['./matching.component.scss']
})
export class MatchingComponent implements OnInit {

  constructor(private globalService: AppService) {
    globalService.getUser().then((user: User) => {

    });
  }

  ngOnInit(): void {
  }

}
