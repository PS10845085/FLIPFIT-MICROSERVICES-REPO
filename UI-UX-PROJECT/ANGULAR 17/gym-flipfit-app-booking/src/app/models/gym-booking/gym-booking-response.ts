 
interface Address {
  id: number;
  line1: string;
  line2: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
}

interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNo: string;
  status: string;
  addresses: Address[];
}

interface Slot {
  id: number;
  startTime: string;
  endTime: string;
  capacity: number;
  bookedCount: number;
  slotDate: string;
  status: string;
}

interface Schedule {
  id: number;
  validFrom: string;
  validTill: string;
  slot: Slot;
}

export interface GymBookingResponse {
  id: number;
  schedule: Schedule;
  user: User;
  status: string;
  bookedAt: number;
}
