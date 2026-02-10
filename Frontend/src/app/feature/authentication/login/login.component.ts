import { Component, DestroyRef, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/service/auth/auth.service';
import { UserService } from '../../../core/service/user/user.service';
import { LoginModel } from '../../../shared/models/login.model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({});
  errorMessage?: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private destroyRef: DestroyRef
  ) {
  }

  ngOnInit(): void {
    this.buildLoginForm();
  }

  login(): void {
    if (!this.loginForm?.valid) {
      this.errorMessage = 'Invalid form completion!';
      setTimeout(() => this.errorMessage = undefined, 3000);
      return;
    }

    const credentials: LoginModel = {
      name: this.loginForm?.get('name')?.value,
      password: this.loginForm?.get('password')?.value
    };
    this.authService.login(credentials)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (response:any) => {
          sessionStorage.setItem('jwt-token', response.jwtToken);
          this.getUserInfo();
        },
        error: () => this.errorMessage = 'Invalid credentials'
      });
  }

  private buildLoginForm(): void {
    this.loginForm = this.formBuilder.group({
      name: [ '', [ Validators.required] ],
      password: [ '', [ Validators.required ] ]
    });
  }

  private getUserInfo(): void {
    this.userService.getInfo()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(response => {
        sessionStorage.setItem('loggedUser', JSON.stringify(response));
        this.redirectUserBasedOnRole(response.role);
      });
  }

  private redirectUserBasedOnRole(role: string): void {
      if (role === 'ADMIN') {
        this.router.navigateByUrl('/dashboard/administrator');
      }
      else {
        this.router.navigateByUrl('/dashboard/client');
      }
   }
}
