import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../../core/service/user/user.service';
import { UserDTO } from '../../../shared/dto/userDTO.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit {
    saveForm: FormGroup = new FormGroup({});
    errorMessage: string | null = null;
    goodMessage: string | null = null;

    constructor(
      private userService: UserService,
      private formBuilder: FormBuilder,
     ) {}

     ngOnInit(): void {
        this.buildSaveForm();
     }

     buildSaveForm(): void {
        this.saveForm = this.formBuilder.group({
          name: ['', [Validators.required]],
          password: ['', [Validators.required, Validators.minLength(6)]]
        });
     }

      onSubmit(): void {
          if (this.saveForm.valid) {
            const newUser: UserDTO = this.saveForm.value;
            newUser.role = 'CLIENT';
            this.userService.createUser(newUser).subscribe({
              next: (response) => {
                console.log('Person saved successfully:', response);
                this.goodMessage = 'Register successfully!';
                this.errorMessage = '';
                this.saveForm.reset();
              },
              error: (error) => {
                console.error();
                this.goodMessage = '';
                this.errorMessage = 'Error. Please try again.';
              }
            });
          } else {
            console.log('Form is not valid');
            this.errorMessage = 'Form is not valid. Please try again.';
          }
        }
}
