import { Component, DestroyRef, OnInit, ViewChild, ElementRef } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';
import { Chart, CategoryScale, LinearScale, LineController, LineElement, PointElement, Title, Tooltip, Legend } from 'chart.js';
import { DeviceService } from "../../../core/service/device/device.service";
import { DeviceDTO } from "../../../shared/dto/deviceDTO.model";
import { WebsocketService } from "../../../core/service/websocket/websocket.service";
import { MeasurementDTO } from "../../../shared/dto/measurementDTO.model";
import { MeasurementService } from "../../../core/service/measurement/measurement.service";


@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrl: './client.component.scss'
})
export class ClientComponent {

  devices: DeviceDTO[] = [];
  notifications: string[] = [];
  selectedDeviceId: string = '';
  selectedDate: string = '';

  @ViewChild('chart') chartRef!: ElementRef;
  chart: any;

  constructor(
      private deviceService: DeviceService,
      private router: Router,
      private destroyRef: DestroyRef,
      private webSocketService: WebsocketService,
      private measurementService: MeasurementService
  ) { Chart.register(CategoryScale, LinearScale, LineController, LineElement, PointElement, Title, Tooltip, Legend); }

  ngOnInit(): void {
    const loggedUser = JSON.parse(sessionStorage.getItem('loggedUser') || '{}');
    const userId = loggedUser.idUser;
    this.loadDevices(userId);
    this.webSocketService.getNotifications().subscribe((message: string) => {
      console.log('Notification received:', message);
      this.notifications.push(message);
      setTimeout(() => {
        this.removeNotification(message);
      }, 10000);
    });
  }

  private removeNotification(message: string): void {
    this.notifications = this.notifications.filter(notification => notification !== message);
  }

  loadDevices(idUser: string): void {
    this.deviceService.getDevicesByUserId(idUser)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(response => this.devices = response);
  }

  logOut(): void {
    sessionStorage.removeItem('loggedUser');
    sessionStorage.removeItem('jwt-token');
    this.router.navigateByUrl('/auth/login');
  }


  updateChart(): void {
    if (!this.selectedDeviceId || !this.selectedDate) {
      alert('Te rugăm să selectezi un dispozitiv și o dată!');
      return;
    }

    this.measurementService.getConsumptionHistory(this.selectedDeviceId, this.selectedDate).subscribe(data => {
      const consumptionsByHour = Array(24).fill(0);

      data.forEach((measurement: MeasurementDTO) => {
        const hour = +measurement.timestamp[3]; // Extragem ora
        consumptionsByHour[hour] += measurement.measurement_value; // Adunăm consumul
      });

      const labels = Array.from({ length: 24 }, (_, i) => i.toString());

      if (this.chart) {
        this.chart.destroy();
      }

      this.chart = new Chart(this.chartRef.nativeElement, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [{
            label: 'Consum (kWh)',
            data: consumptionsByHour,
            fill: false,
            borderColor: 'rgba(75, 192, 192, 1)',
            tension: 0.1,
          }]
        },
        options: {
          responsive: true,
          scales: {
            x: {
              title: {
                display: true,
                text: 'Ora'
              }
            },
            y: {
              title: {
                display: true,
                text: 'Consum (kWh)'
              },
              beginAtZero: true
            }
          }
        }
      });
    });
  }

}
