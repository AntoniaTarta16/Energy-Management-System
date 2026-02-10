import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { DeviceService } from '../../../../core/service/device/device.service';
import { DeviceDTO } from '../../../../shared/dto/deviceDTO.model';
import {PersonService} from "../../../../core/service/person/person.service";

@Component({
  selector: 'app-edit-device',
  templateUrl: './edit-device.component.html',
  styleUrl: './edit-device.component.scss'
})
export class EditDeviceComponent implements OnInit{
  saveForm: FormGroup;
  errorMessage: string | null = null;
  idDevice: string = '';
  idUsers: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private deviceService: DeviceService,
    private personService: PersonService
  ) {
    this.saveForm = this.fb.group({
      description: ['', Validators.required],
      address: ['', Validators.required],
      maxHourlyEnergyConsumption: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      idUser: ['']
    });
  }

  ngOnInit(): void {
    this.idDevice= this.route.snapshot.paramMap.get('idDevice') || '';
    this.loadDeviceData();
    this.loadIds();
  }

  loadIds() {
    this.personService.getAllIds().subscribe({
      next: (idUsers: string[]) => {
        this.idUsers = idUsers;
      },
      error: (err) => {
        this.errorMessage = 'Error loading user IDs';
      }
    });
  }

  loadDeviceData() {
    this.deviceService.getDevicesById(this.idDevice).subscribe({
      next: (device: DeviceDTO) => {
        this.saveForm.patchValue({
          description: device.description,
          address: device.address,
          maxHourlyEnergyConsumption: device.maxHourlyEnergyConsumption,
          idUser: device.idUser
        });
      },
      error: (err) => {
        this.errorMessage = 'Error loading device data';
      }
    });
  }


  onSubmit() {
    if (this.saveForm.valid) {
      const formData = this.saveForm.value;
      if (!formData.idUser || formData.idUser === "null") {
       formData.idUser = null;
      }

      this.deviceService.updateDevice(this.idDevice, formData).subscribe({
        next: () => this.router.navigate(['/dashboard/device']),
        error: () => {
          this.errorMessage = 'Error updating device';
        }
      });
    }
    else {
      this.errorMessage = 'Form is not valid. Please check the inputs.';
    }
  }

}
