
// src/app/models/gym-booking/scheduler.model.ts
import { SlotResponse } from './slot-request'; // you already have this

/** Request payload used by create/update APIs */
export interface SchedulerRequest {
  validFrom: string;  // 'yyyy-MM-dd'
  validTill: string;  // 'yyyy-MM-dd'
  slotId?: number;     // link to the Slot (OneToOne)
}

/** Response returned by read APIs */
export interface SchedulerResponse {
  id: number;
  validFrom: string;  // 'yyyy-MM-dd'
  validTill: string;  // 'yyyy-MM-dd'
  slot: SlotResponse; // full slot object
}
