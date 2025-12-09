
// src/app/services/gym-center.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CenterRequest } from '../../models/gym-center/center-request';
import { ApiResponse } from '../../models/gym-center/center-response';
import { json } from 'stream/consumers';


@Injectable({ providedIn: 'root' })
export class GymCenterService {
  // Match Spring Boot controller: @RequestMapping("/api/gyms") @PostMapping()
  private readonly baseUrl = 'http://localhost:8081/api';

  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private httpClient: HttpClient) {
    
  }

  createCenter(centerData: CenterRequest): Observable<ApiResponse> {
    // POST /api/gyms
    console.log("entered create center in service");
    console.log(JSON.stringify(centerData));
    return this.httpClient.post<ApiResponse>(`${this.baseUrl}/createCenter`, JSON.stringify(centerData), { headers: this.headers });
    console.log("data save success");
  }

  // Optional helper (not required, but useful)
  getCenterById(id: number): Observable<ApiResponse> {
    return this.httpClient.get<ApiResponse>(`${this.baseUrl}/${id}`);
  }

  getAllCenters(): Observable<ApiResponse> {
    return this.httpClient.get<ApiResponse>(`${this.baseUrl}/centers`, { headers: this.headers });
    console.log("entered getAllCenter service");
  }


  /** ✅ UPDATE: RESTful style */
  updateCenter(id: number, centerData: CenterRequest): Observable<ApiResponse> {
    return this.httpClient.put<ApiResponse>(
      `${this.baseUrl}/updateCenter/${id}`,
      JSON.stringify(centerData),
      { headers: this.headers }
    );
  }

  /** ✅ DELETE */
  deleteCenter(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/deleteCenter/${id}`);
  }

}
