import { Component } from '@angular/core';
import { User } from './models/user';
import { UserService } from './services/user.service';
import { Router } from '@angular/router';
import { Role } from './models/role';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app-client';
  currentUser: User;

  constructor(private userService: UserService, private router: Router) {
    this.userService.currentUser.subscribe(
      data => this.currentUser = data
    );
  }

  logout() {
    this.userService.logout().subscribe(
      success => this.router.navigate(['/login'])
    );
  }

  isAdmin() {
    return this.currentUser && this.currentUser.role === Role.ADMIN;
  }

}
