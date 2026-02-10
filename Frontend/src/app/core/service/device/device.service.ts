import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {DeviceDTO} from "../../../shared/dto/deviceDTO.model";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private apiUrl = 'devices';

  constructor(private http: HttpClient) {
  }

  getAllDevices(): Observable<DeviceDTO[]> {
    return this.http.get<DeviceDTO[]>(`${this.apiUrl}`);
  }

  addDevice(newDevice: DeviceDTO): Observable<DeviceDTO> {
    return this.http.post<DeviceDTO>(`${this.apiUrl}`, newDevice);
  }

  updateDevice(idDevice: string, deviceDTO: DeviceDTO): Observable<DeviceDTO> {
    return this.http.put<DeviceDTO>(`${this.apiUrl}/${idDevice}`, deviceDTO);
  }

  deleteDeviceById(idDevice: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idDevice}`);
  }

  getDevicesByUserId(idUser: string): Observable<DeviceDTO[]> {
    return this.http.get<DeviceDTO[]>(`${this.apiUrl}/${idUser}`);
  }

  getDevicesById(idDevice: string): Observable<DeviceDTO> {
    return this.http.get<DeviceDTO>(`${this.apiUrl}/device/${idDevice}`);
  }
}
