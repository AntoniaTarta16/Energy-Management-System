import { Component, DestroyRef, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';
import { UserService } from '../../../core/service/user/user.service';
import { UserModel } from '../../../shared/models/user.model';


@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrl: './administrator.component.scss'
})

export class AdministratorComponent implements OnInit{

    users: UserModel[] = [];

    constructor(
      private userService: UserService,
      private router: Router,
      private destroyRef: DestroyRef
    ) {}

    ngOnInit(): void {
        this.loadUsers();
    }

    loadUsers(): void {
          this.userService.getAllUsers()
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(response => this.users = response);
      }

    logOut(): void {
          sessionStorage.removeItem('loggedUser');
          sessionStorage.removeItem('jwt-token');
          this.router.navigateByUrl('/auth/login');
    }

      deleteUser(name: string): void {
          this.userService.deleteUser(name).subscribe(() => {
            this.loadUsers();
          });
      }

}
