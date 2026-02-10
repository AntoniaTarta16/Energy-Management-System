import { Component, DestroyRef, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';
import { DeviceService } from '../../../core/service/device/device.service';
import { DeviceDTO } from '../../../shared/dto/deviceDTO.model';


@Component({
  selector: 'app-employee',
  templateUrl: './device.component.html',
  styleUrl: './device.component.scss'
})
export class DeviceComponent {

  devices: DeviceDTO[] = [];

  constructor(
    private deviceService: DeviceService,
    private router: Router,
    private destroyRef: DestroyRef,
  ) {}

  ngOnInit(): void {
    this.loadDevices();
  }

  loadDevices(): void {
          this.deviceService.getAllDevices()
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(response => this.devices = response);
  }

  logOut(): void {
          sessionStorage.removeItem('loggedUser');
          sessionStorage.removeItem('jwt-token');
          this.router.navigateByUrl('/auth/login');
  }

 deleteDevice(id: string): void {
    this.deviceService.deleteDeviceById(id).subscribe(() => {
      this.loadDevices();
    });
 }

}
