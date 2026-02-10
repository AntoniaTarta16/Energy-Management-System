import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private apiUrl = 'persons';

  constructor(private http: HttpClient) {
  }

  getAllIds(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/ids`);
  }
}
