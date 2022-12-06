import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {MyLocation, RegistrationService} from "./service/registration.service";

interface DropDownItem {
  item_id: string;
  item_text: string;
  combination: string;
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
      locationSearch: new FormControl(''),
    }
  );
  @ViewChild('hobby_selection') hobbySelection: any;
  selectedHobbies = [];
  dropdownSettingsHobbies = {};

  selectedLocation: DropDownItem[] = [];
  foundLocations: DropDownItem[] = [];
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
  }

  getNewLocationData(event: any) {
    clearTimeout(this.locationQueryInterval);
    if (event.length > 0) {
      this.locationQueryInterval = setTimeout(() => {
        this.registrationService.getLocations(event).subscribe({
          next: data => {
            let locations: DropDownItem[] = [];
            data.data.forEach((location: MyLocation) => {
              let location_item = {
                item_id: location.wikiDataId,
                item_text: location.name,
                combination: `${location.wikiDataId};${location.name}`
              };
              locations.push(location_item);
            });

            this.foundLocations = locations;
          }
        });
      }, 500);
    }
  }

  onLocationSelect(event: any) {
    console.log(event);
  }
}
