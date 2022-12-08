import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
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

  postRegister(user: User) {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8')
      .set('Accept', 'application/json');

    let hobbiesStringArray = "[";
    user.hobbies.forEach(hobby => {
      hobbiesStringArray += `"${hobby.toUpperCase()}",`;
    });
    if (hobbiesStringArray.endsWith(",")) {
      hobbiesStringArray = hobbiesStringArray.substring(0, hobbiesStringArray.length - 1);
    }
    hobbiesStringArray += "]";

    let userRolesStringArray = "[";
    user.role.forEach(role => {
      userRolesStringArray += `"${role.toUpperCase()}",`;
    });
    if (userRolesStringArray.endsWith(",")) {
      userRolesStringArray = userRolesStringArray.substring(0, userRolesStringArray.length - 1);
    }
    userRolesStringArray += "]";

    return this.http.post(`${this.BACKEND_URL}/auth/register`, `{
        "firstname": "${user.firstname}",
        "lastname": "${user.lastname}",
        "hobbies": ${hobbiesStringArray},
        "location": "${user.location}",
        "gender": "${user.gender}",
        "age": ${user.age},
        "role": ${userRolesStringArray},
        "profilepicture": "${user.profilePicture}",
        "email": "${user.email}",
        "password": "${user.password}"
    }`, {headers: headers, withCredentials: true});
  }

  getHobbies(): Observable<string[]> {
    return this.http.get<string[]>(this.BACKEND_URL + '/hobby/all');
  }

  getUserRoles(): Observable<string[]> {
    return this.http.get<string[]>(this.BACKEND_URL + '/userRoles/all');
  }

  getLocations(query: string): Observable<LocationObject> {
    return this.http.get<LocationObject>(`${this.BACKEND_URL}/location/search/${query}`);
  }
}
