import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserDTO } from '../../../shared/dto/userDTO.model';
import { UserService } from '../../../core/service/user/user.service';


@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrl: './person.component.scss'
})
export class PersonComponent implements OnInit {

  saveForm: FormGroup = new FormGroup({});
  errorMessage: string | null = null;

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
        password: ['', [Validators.required, Validators.minLength(6)]],
        role: ['', [Validators.required]]
      });
   }

    onSubmit(): void {
        if (this.saveForm.valid) {
          const newUser: UserDTO = this.saveForm.value;
          this.userService.createUser(newUser).subscribe({
            next: (response) => {
              console.log('User saved successfully:', response);
              this.saveForm.reset();
              this.errorMessage='';
            },
            error: () => {
              console.error();
              this.errorMessage = 'Error. Please try again.';
            }
          });
        }
        else {
          console.log('Form is not valid');
          this.errorMessage = 'Form is not valid. Please try again.';
        }
      }
}
