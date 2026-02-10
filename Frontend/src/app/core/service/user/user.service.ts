import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserModel } from '../../../shared/models/user.model';
import { UserDTO } from '../../../shared/dto/userDTO.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'users';

  constructor(private http: HttpClient) {
  }

  getInfo(): Observable<UserModel> {
    return this.http.get<UserModel>(`${this.apiUrl}/info`);
  }

  getAllUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(`${this.apiUrl}`);
  }

  getUserByName(name:string): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.apiUrl}/${name}`);
  }

  deleteUser(name: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${name}`);
  }

  createUser(newUser: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/new-user`, newUser);
  }

  updateUser(name: string, userDTO: UserDTO): Observable<UserDTO> {
    return this.http.put<UserDTO>(`${this.apiUrl}/${name}`, userDTO);
  }
}
