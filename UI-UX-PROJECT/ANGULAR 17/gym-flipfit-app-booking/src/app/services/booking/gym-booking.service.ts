import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GymBookingResponse } from '../../models/gym-booking/gym-booking-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GymBookingService {

  /** Base URL up to /slots per your instruction */
  private readonly baseUrl = 'http://localhost:8082/api/bookings';

  private readonly headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) { }


  getBookingsByUserId(userId: number) {
    return this.http.get<GymBookingResponse[]>(`${this.baseUrl}/user/${userId}`, { headers: this.headers });

  }

 /** Create booking: POST /api/bookings/create?userId=2&scheduleId=3 */
  createBooking(userId: number, scheduleId: number): Observable<GymBookingResponse> {
    return this.http.post<GymBookingResponse>(
      `${this.baseUrl}/create?userId=${userId}&scheduleId=${scheduleId}`,
      {}, // request body not needed if params are enough
      { headers: this.headers }
    );
  }

}
