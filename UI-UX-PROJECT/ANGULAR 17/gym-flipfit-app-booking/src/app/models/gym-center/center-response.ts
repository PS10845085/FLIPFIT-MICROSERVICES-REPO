import { AddressRequest } from "./center-request";



export interface AddressResponse extends AddressRequest {
  id: number;
}

export interface SlotResponse {
  id: number;
  dayOfWeek: string;
  startTime: string;
  endTime: string;
}

export interface CenterResponse {
  id: number;
  ownerId: number;
  name: string;
  emailId?: string;
  phoneNo?: string;
  status: 'ACTIVE' | 'INACTIVE' | 'SUSPENDED'; // match your Status enum
  address: AddressResponse;
  slots: SlotResponse[];
}