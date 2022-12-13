import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MyLocation, RegistrationService} from "./service/registration.service";
import {MatSelectBase} from "@angular/material/select";
import {User} from "./model/User";
import {Router} from "@angular/router";
import {HomeService} from "../home/home.service";

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
  isImageSaved: boolean = false;
  cardImageBase64: string = '';

  registerForm: FormGroup = new FormGroup({
      firstName: new FormControl('',[
        Validators.required,
        Validators.minLength(2)
      ]),
      lastName: new FormControl('',[
        Validators.required,
        Validators.minLength(2)
      ]),
      email: new FormControl('',[
        Validators.required,
        Validators.email
      ]),
      location: new FormControl(''),
      gender: new FormControl('',[
        Validators.required
      ]),
      age: new FormControl('',[
        Validators.required
      ]),
      password: new FormControl('',[
        Validators.required,
        Validators.minLength(6)
      ]),
      locationSearch: new FormControl(''),
      imageUpload: new FormControl('')
    }
  );
  @ViewChild('hobby_selection') hobbySelection: any;
  selectedHobbies = [];
  dropdownSettingsHobbies = {};

  @ViewChild('userRole_selection') userRoleSelection: any;
  selectedUserRoles = [];
  dropdownSettingsUserRoles = {};

  @ViewChild('location_selection') locationSelection: MatSelectBase | undefined;
  foundLocations: DropDownItem[] = [];
  locationQueryInterval: any;
  error = '';
  pp_error = '';
  location_error = '';

  constructor(private registrationService: RegistrationService, private router: Router, private homeService: HomeService) {
    this.homeService
      .getUser()
      .pipe()
      .subscribe({
        next: () => {
          this.router.navigate(['/home']);
        }
      });
  }

  onSubmit() {

    console.log(this.registerForm.value);

    let user: User = {
      age: this.registerForm.value.age,
      email: this.registerForm.value.email,
      firstname: this.registerForm.value.firstName,
      gender: this.registerForm.value.gender,
      hobbies: this.selectedHobbies,
      lastname: this.registerForm.value.lastName,
      location: this.registerForm.value.location,
      password: this.registerForm.value.password,
      role: this.selectedUserRoles,
      profilePicture: this.cardImageBase64
    }

    let pp_checked : boolean = false;
    let location_checked : boolean = false;

    if (!user.profilePicture.includes('data:image/png;base64,')){
      this.removeImage()
      this.pp_error = 'Something went wrong with the picture!!'
      pp_checked = false;
    } else {
      this.pp_error = ''
      pp_checked = true;
    }

    if (user.location.split(';').length < 2){
      this.location_error = 'Something went wrong with the location!!'
      location_checked = false;

    }else {
      this.location_error = '';
      location_checked = true;
    }

    if (pp_checked && location_checked) {

      this.registrationService.postRegister(user).subscribe({
        next: () => {
          this.router.navigate(['/home']);
        },
        error: (error) => {
          switch (error.status) {
            case 409:
              this.registerForm.controls['email'].setErrors({'incorrect': true});
              break
            case 400:
              this.error = 'Please enter data!!';
              break
            case 401:
              this.error = 'Incorrect login data!! Please enter the right credentials';
              break

          }

          console.log(error.error);
        }
      });
    }




  }

  ngOnInit(): void {
    this.registrationService.getHobbies().subscribe({
      next: data => {
        this.hobbySelection.data = data;
      }
    });

    this.registrationService.getUserRoles().subscribe({
      next: data => {
        this.userRoleSelection.data = data;
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

    this.dropdownSettingsUserRoles = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      allowSearchFilter: true,
      searchPlaceholderText: 'Search for role',
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
            if (locations.length == 1) {
              this.locationSelection?.ngControl?.control?.setValue(locations[0].combination);
            }
          }
        });
      }, 500);
    }
  }

  fileChangeEvent(evt: any) {
    console.log("helloooo")
    let files = evt.target.files;
    let file = files[0];

    if (files && file) {
      let reader = new FileReader();

      reader.onload = this._handleReaderLoaded.bind(this);
      this.isImageSaved = true;

      reader.readAsBinaryString(file);
    }
  }

  _handleReaderLoaded(readerEvt: any) {
    let binaryString = readerEvt.target.result;
    this.cardImageBase64 = `data:image/png;base64,${btoa(binaryString)}`;
  }

  removeImage() {
    this.cardImageBase64 = '';
    this.isImageSaved = false;
  }
}
