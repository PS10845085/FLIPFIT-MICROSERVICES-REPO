
export interface SlotRequest {
  /** Java LocalTime as string "HH:mm:ss" */
  startTime: string;
  /** Java LocalTime as string "HH:mm:ss" */
  endTime: string;

  capacity: number;
  bookedCount: number;

  /** Java Date (TemporalType.DATE) as "yyyy-MM-dd" */
  slotDate: string;

  /** e.g., "AVAILABLE", "PARTIALLY_BOOKED", "FULLY_BOOKED", etc. */
  status: string;
}

export interface SlotResponse extends SlotRequest {
  id: number;
}
