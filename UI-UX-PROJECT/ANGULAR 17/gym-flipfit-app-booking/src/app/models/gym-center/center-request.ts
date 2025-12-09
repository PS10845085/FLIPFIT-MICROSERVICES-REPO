
export interface AddressRequest {
  line1: string;
  line2?: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
}


export interface SlotRequest {
  startTime: string;    // "HH:mm:ss"
  endTime: string;      // "HH:mm:ss"
  capacity: number;
  bookedCount: number;
  slotDate: string;     // "yyyy-MM-dd"
  status: string;       // e.g., "PARTIALLY_BOOKED"
}


export interface CenterRequest {
  name: string;
  emailId?: string;
  phoneNo?: string;
  ownerId: number;
  status? : string;
  address: AddressRequest;
  slots?: SlotRequest[];
}
