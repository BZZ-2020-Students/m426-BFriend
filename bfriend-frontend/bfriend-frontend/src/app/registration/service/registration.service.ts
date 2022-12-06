import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/User";
import {environment} from "../../../environments/environment";

interface LocationObject {
  data: MyLocation[];
  links: any;
  metadata: any;
}

export interface MyLocation {
  wikiDataId: string;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  // get url from .env file
  private BACKEND_URL = `${environment.BACKEND_URL}`;

  constructor(private http: HttpClient) {

  }

  sendData(user: User) {
    this.http.post<User>('backendURL', user).subscribe(data => {
    })
  }

  getHobbies(): Observable<string[]> {
    return this.http.get<string[]>(this.BACKEND_URL + '/hobby/all');
  }

  getLocations(query: string): Observable<LocationObject> {
    return this.http.get<LocationObject>(`${this.BACKEND_URL}/location/search/${query}`);
  }
}
