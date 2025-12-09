
// src/app/services/booking/gym-scheduler.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SchedulerRequest, SchedulerResponse } from '../../models/gym-booking/scheduler-request';

@Injectable({ providedIn: 'root' })
export class GymSchedulerService {
  /** Base URL up to /api (per your note) */
  private readonly baseUrl = 'http://localhost:8082/api';

  /** Add headers if you need (e.g. JSON) */
  private readonly headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  /** CREATE: POST /createSchedule → returns plain string */
  createSchedule(payload: SchedulerRequest): Observable<SchedulerResponse> {
    return this.http.post<SchedulerResponse>(`${this.baseUrl}/createSchedule`, payload, {
      headers: this.headers
    });
  }

  /** UPDATE: PUT /updateSchedule/{id} on port 8083 → returns plain string */
  updateSchedule(id: number, payload: SchedulerRequest): Observable<SchedulerResponse> {
    return this.http.put<SchedulerResponse>(`${this.baseUrl}/updateSchedule/${id}`, payload, {
      headers: this.headers,
    });
  }

  /** READ ALL: GET /getAllSchedule → returns JSON array */
  getAllSchedules(): Observable<SchedulerResponse[]> {
    return this.http.get<SchedulerResponse[]>(`${this.baseUrl}/getAllSchedule`);
  }

  /** READ ONE: GET /getScheduleById/{id} → returns JSON */
  getScheduleById(id: number): Observable<SchedulerResponse> {
    return this.http.get<SchedulerResponse>(`${this.baseUrl}/getScheduleById/${id}`);
  }

  /** DELETE: DELETE /deleteScheduleById/{id} → returns string */
  deleteScheduleById(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/deleteScheduleById/${id}`, {
      headers: this.headers,
      responseType: 'text'
    });
  }
}
