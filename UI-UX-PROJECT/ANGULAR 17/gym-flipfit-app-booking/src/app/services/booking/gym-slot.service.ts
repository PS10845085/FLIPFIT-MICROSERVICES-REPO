
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SlotRequest, SlotResponse } from '../../models/gym-booking/slot-request';

@Injectable({ providedIn: 'root' })
export class GymSlotService {
  /** Base URL up to /slots per your instruction */
  private readonly baseUrl = 'http://localhost:8082/slots';

  private readonly headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) { }

  /** CREATE: POST /slots/createSlot */
  createSlot(payload: SlotRequest): Observable<SlotResponse> {
    return this.http.post<SlotResponse>(
      `${this.baseUrl}/createSlot`,
      payload,
      { headers: this.headers }
    );
  }

  /** UPDATE: PUT /slots/updateSlot/{id} */
  updateSlot(id: number, payload: SlotRequest): Observable<SlotResponse> {
    return this.http.put<SlotResponse>(
      `${this.baseUrl}/updateSlot/${id}`,
      payload,
      { headers: this.headers }
    );
  }

  /** READ ALL: GET /slots/getAllSlots */
  getAllSlots(): Observable<SlotResponse[]> {
    return this.http.get<SlotResponse[]>(
      `${this.baseUrl}/getAllSlots`,
      { headers: this.headers }
    );
  }

  /** READ ONE: GET /slots/getSlotById/{id} */
  getSlotById(id: number): Observable<SlotResponse> {
    return this.http.get<SlotResponse>(
      `${this.baseUrl}/getSlotById/${id}`,
      { headers: this.headers }
    );
  }


  /** DELETE: DELETE /slots/deleteSlot/{id} */
  deleteSlot(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/deleteSlot/${id}`, {
      headers: this.headers,
      responseType: 'text' // <-- tell HttpClient the response is a string
    });
  }

}
