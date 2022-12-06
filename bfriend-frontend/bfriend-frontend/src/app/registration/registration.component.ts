import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {MyLocation, RegistrationService} from "./service/registration.service";

interface DropDownItem {
  item_id: string;
  item_text: string;
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
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
  selectedHobbies = [];
  dropdownSettingsHobbies = {};

  @ViewChild('location_selection') locationSelection: any;
  selectedLocation = [];
  dropdownSettingsLocation = {};
  locationQueryInterval: any;

  constructor(private registrationService: RegistrationService) {
  }

  onSubmit() {
    console.log(this.registerForm.value);
    console.log(this.selectedHobbies);
    console.log(this.selectedLocation);
  }

  ngOnInit(): void {
    this.registrationService.getHobbies().subscribe({
      next: data => {
        this.hobbySelection.data = data;
      }
    });

    this.dropdownSettingsHobbies = {
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

    this.dropdownSettingsLocation = {
      singleSelection: true,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      allowSearchFilter: true,
      searchPlaceholderText: 'Search for location',
      noDataAvailablePlaceholderText: 'No Data Available',
      allowRemoteDataSearch: true,
    }
  }

  getNewLocationData(event: any) {
    clearTimeout(this.locationQueryInterval);
    if (event.length > 0) {
      this.locationQueryInterval = setTimeout(() => {
        console.log("lets go", event)
        this.registrationService.getLocations(event).subscribe({
          next: data => {
            let locations: DropDownItem[] = [];
            data.data.forEach((location: MyLocation) => {
              locations.push({item_id: location.wikiDataId, item_text: location.name});
            });
            this.locationSelection.data = locations;
          }
        });
      }, 500);
    }
  }
}
