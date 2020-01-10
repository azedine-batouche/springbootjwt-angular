import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(private userService: UserService, private router: Router) { }


  ngOnInit() {
    if (this.userService.currentUserValue) {
        this.router.navigate(['/profile']);
        return;
    }
  }
  register() {
    this.userService.register(this.user).subscribe(
      success => this.router.navigate(['/login']),
      error => this.errorMessage = 'Username is ready exist.'
    );
  }
}
