import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {MeasurementDTO} from "../../../shared/dto/measurementDTO.model";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {

  private apiUrl = 'measurements/consumption';

  constructor(private http: HttpClient) {
  }

  getConsumptionHistory(deviceId: string, date: string): Observable<MeasurementDTO[]> {
    return this.http.get<MeasurementDTO[]>(`${this.apiUrl}/${deviceId}/${date}`);
  }

}
