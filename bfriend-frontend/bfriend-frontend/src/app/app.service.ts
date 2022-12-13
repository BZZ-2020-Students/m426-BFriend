//Use a shared service:
import {Injectable} from '@angular/core';
import {User} from "./home/model/User";

@Injectable()
export class AppService {
  user: User | null;

  constructor() {
    this.user = null;
  }

  setUser(val: User) {
    this.user = val;
    console.log("set user", this.user);
  }

  async getUser(): Promise<User> {
    // wait until user is set
    while (this.user === null) {
      await new Promise(resolve => setTimeout(resolve, 100));
    }
    return this.user;
  }
}
