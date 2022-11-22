import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  // hobbies = new FormControl('');
  hobbiesList: Array<string> = new Array();

  constructor() {
    this.hobbiesList.push("Fussball")
    this.hobbiesList.push("Velo")
    this.hobbiesList.push("Gym")
    this.hobbiesList.push("Boxen")
  }

  ngOnInit(): void {
  }

}
