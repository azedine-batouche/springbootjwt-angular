import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
    if (this.userService.currentUserValue) {
      this.router.navigate(['/profile']);
      return;
    }
  }

  login() {
    this.userService.login(this.user).subscribe(
      success => { this.router.navigate(['/profile']); },
      errors => { this.errorMessage = 'Username or password is incorrect'; }
    );
  }
}
