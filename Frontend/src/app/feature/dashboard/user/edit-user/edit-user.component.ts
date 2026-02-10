import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UserService } from '../../../../core/service/user/user.service';
import { UserDTO } from '../../../../shared/dto/userDTO.model';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.scss'
})
export class EditUserComponent implements OnInit{
  saveForm: FormGroup;
  errorMessage: string | null = null;
  name: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private userService: UserService
  ) {
    this.saveForm = this.fb.group({
      name: [''],
      role: ['']
    });
  }

  ngOnInit(): void {
    this.name = this.route.snapshot.paramMap.get('name') || '';
    this.loadUserData();
  }

  loadUserData() {
    this.userService.getUserByName(this.name).subscribe({
      next: (user: UserDTO) => {
        this.saveForm.patchValue({
          name: user.name,
          role: user.role
        });
      },
      error: (err) => {
        this.errorMessage = 'Error loading user data';
      }
    });
  }

  onSubmit() {
    if (this.saveForm.valid) {
      this.userService.updateUser(this.name, this.saveForm.value).subscribe({
        next: () => this.router.navigate(['/dashboard/administrator']),
        error: (err) => {
          this.errorMessage = 'Error updating user';
        }
      });
    }
  }

}
