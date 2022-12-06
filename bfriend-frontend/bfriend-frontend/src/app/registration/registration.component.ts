import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {RegistrationService} from "./service/registration.service";


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  // hobbies = new FormControl('');
  hobbiesList: Array<string> = [];

  constructor(private registrationService: RegistrationService) {
    this.registrationService.getHobbies().subscribe(data => {
      console.log(data);
      this.hobbiesList = data;
    });
  }

  ngOnInit(): void {
  }

}
