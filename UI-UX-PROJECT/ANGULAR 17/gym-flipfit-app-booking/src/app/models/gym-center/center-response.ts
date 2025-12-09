
// Represents a single center
export interface AddressResponse {
  id: number;
  line1: string;
  line2?: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
}

export interface SlotResponse {
  id: number;
  slotDate: string;
  startTime: string;
  endTime: string;
}

export interface Center {
  id: number;
  ownerId: number;
  name: string;
  emailId?: string;
  phoneNo?: string;
  status: 'ACTIVE' | 'INACTIVE' | 'SUSPENDED';
  address: AddressResponse;
  slots: SlotResponse[];
}

// Represents the API response wrapper
export interface ApiResponse {
  status: string;
  message: string;
  data: Center[];
}
