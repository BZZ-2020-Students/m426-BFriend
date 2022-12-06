import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {RegistrationService} from "./service/registration.service";


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  // hobbies = new FormControl('');
  hobbiesList: Array<string> = [];
  registerForm: FormGroup = new FormGroup({
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      email: new FormControl(''),
      location: new FormControl(''),
      profilePicture: new FormControl(''),
      gender: new FormControl(''),
      age: new FormControl(''),
      password: new FormControl(''),
    }
  );
  @ViewChild('hobby_selection') hobbySelection: any;
  dropdownList: Array<any> = [];
  selectedItems = [];
  dropdownSettings = {};


  constructor(private registrationService: RegistrationService) {
  }

  onSubmit() {
    console.log(this.registerForm.value);
    console.log(this.selectedItems);
  }

  ngOnInit(): void {
    this.registrationService.getHobbies().subscribe({
      next: data => {
        this.hobbySelection.data = data;
      }
    });

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      allowSearchFilter: true,
      searchPlaceholderText: 'Search for hobby',
      noDataAvailablePlaceholderText: 'No Data Available',
      allowRemoteDataSearch: false,
    }
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }

  onDeSelectAll(items: any) {
    console.log(items);
  }

  onDeSelect(item: any) {
    console.log(item);
  }

}
