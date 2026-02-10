import { Component, DestroyRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DeviceDTO } from '../../../../shared/dto/deviceDTO.model';
import { DeviceService } from '../../../../core/service/device/device.service';

@Component({
  selector: 'app-object',
  templateUrl: './device-save.component.html',
  styleUrl: './device-save.component.scss'
})
export class DeviceSaveComponent implements OnInit {

  saveForm: FormGroup = new FormGroup({});
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private deviceService: DeviceService,
    private formBuilder: FormBuilder,
    private router: Router,
    private destroyRef: DestroyRef,
   ) {}

   ngOnInit(): void {
      this.buildSaveForm();
   }

   buildSaveForm(): void {
      this.saveForm = this.formBuilder.group({
        description: ['', [Validators.required]],
        address: ['', [Validators.required]],
        maxHourlyEnergyConsumption: ['', [Validators.required, Validators.pattern('^[0-9]*$')]]
      });
   }


  onSubmit(): void {
        if (this.saveForm.valid) {
          const newDevice: DeviceDTO = {
            idDevice: ' ',
            description: this.saveForm.value.description,
            address: this.saveForm.value.address,
            maxHourlyEnergyConsumption: this.saveForm.value.maxHourlyEnergyConsumption
          };
          this.deviceService.addDevice(newDevice).subscribe({
            next: (response) => {
              console.log('Device saved successfully:', response);
              this.successMessage = 'Device saved successfully.';
              this.errorMessage = null;
              this.saveForm.reset();
            },
            error: (error) => {
              console.error();
              this.errorMessage = 'Error saving device. Please try again.';
              this.successMessage = null;
            }
          });
        } else {
          console.log('Form is not valid');
          this.errorMessage = 'Form is not valid. Please try again.';
          this.successMessage = null;
        }
      }
}
