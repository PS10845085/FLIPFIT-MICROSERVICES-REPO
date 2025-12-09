
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Route } from '@angular/router';
import { GymSlotService } from '../../../services/booking/gym-slot.service';
import { SlotRequest, SlotResponse } from '../../../models/gym-booking/slot-request';


@Component({
  selector: 'app-gym-slot-create',
  templateUrl: './gym-slot-create.component.html',
  styleUrls: ['./gym-slot-create.component.css']
})
export class GymSlotCreateComponent implements OnInit {
  slotForm!: FormGroup;
  isUpdateMode = false;
  slotId?: number;

  /** Dropdown time options like "05:00:00", "05:15:00", ... */
  timeOptions: string[] = [];

  /** (Optional) status list; adjust to your enum values */
  statusOptions = ['AVAILABLE', 'NOT_AVAILABLE', 'FULLY_BOOKED', 'CANCELED'];

  constructor(
    private fb: FormBuilder,
    private slotService: GymSlotService,
    private router: Router,
    private route : ActivatedRoute

  ) { }

  ngOnInit(): void {
    this.timeOptions = this.generateTimeOptions('05:00', '23:00', 15);

    this.slotForm = this.fb.group({
      startTime: ['', [Validators.required]],
      endTime: ['', [Validators.required]],
      capacity: [0, [Validators.required, Validators.min(1)]],
      bookedCount: [0, [Validators.required, Validators.min(0)]],
      slotDate: ['', [Validators.required]], // yyyy-MM-dd
      status: ['', [Validators.required]]
    });


    // 1) Try to read the full slot from router state (works without extra API call)
    const nav = this.router.getCurrentNavigation();
    const stateSlot = nav?.extras?.state?.['slot'] as SlotResponse | undefined;

    // 2) Always read :id from the URL so refresh still works
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.slotId = Number(idParam);
      this.isUpdateMode = true;
    }

    if (stateSlot) {
      // populate immediately from state
      this.patchForm(stateSlot);
    } else if (this.isUpdateMode && this.slotId) {
      // fallback: fetch by id when user refreshed the page (state is lost)
      this.loadSlotData(this.slotId);
    }

  }


  private patchForm(slot: SlotResponse) {
    this.slotForm.patchValue({
      startTime: normalizeTime(slot.startTime),  // 'HH:mm:ss' → 'HH:mm:ss' or 'HH:mm'
      endTime: normalizeTime(slot.endTime),
      capacity: slot.capacity,
      bookedCount: slot.bookedCount,
      slotDate: normalizeTime(slot.slotDate),   // epoch/Date → 'YYYY-MM-DD'
      status: slot.status
    });
  }


  /** Create dropdown values every N minutes between start and end (inclusive end hour) */
  private generateTimeOptions(startHHmm: string, endHHmm: string, stepMinutes: number): string[] {
    const pad = (n: number) => (n < 10 ? `0${n}` : `${n}`);

    const [startH, startM] = startHHmm.split(':').map(Number);
    const [endH, endM] = endHHmm.split(':').map(Number);

    let curMinutes = startH * 60 + startM;
    const endMinutes = endH * 60 + endM;

    const options: string[] = [];
    while (curMinutes <= endMinutes) {
      const h = Math.floor(curMinutes / 60);
      const m = curMinutes % 60;
      options.push(`${pad(h)}:${pad(m)}:00`);
      curMinutes += stepMinutes;
    }
    return options;
  }

  /** Populate form for update */
  private loadSlotData(id: number): void {
    this.slotService.getSlotById(id).subscribe({
      next: (slot: SlotResponse) => {
        // If backend returns arrays for time, normalize here as needed
        this.slotForm.patchValue({
          startTime: slot.startTime,     // expected "HH:mm:ss"
          endTime: slot.endTime,
          capacity: slot.capacity,
          bookedCount: slot.bookedCount,
          slotDate: slot.slotDate,      // "yyyy-MM-dd"
          status: slot.status
        });
      },
      error: (err) => {
        console.error('Failed to load slot', err);
        alert('Failed to load slot details.');
      }
    });
  }


  /** SAFETY: ensure start < end lexicographically in "HH:mm" format */
  private startBeforeEnd(): boolean {
    const start = this.slotForm.value.startTime as string;
    const end = this.slotForm.value.endTime as string;
    return !!start && !!end && start < end;
  }


  /** CREATE — called by form submit */
  onCreateSubmit(): void {
    if (this.slotForm.invalid) {
      alert('Please fill all required fields correctly.');
      return;
    }
    if (!this.startBeforeEnd()) {
      alert('Start Time must be earlier than End Time.');
      return;
    }

    // Build payload with "HH:mm" (backend expects LocalTime without seconds)
    const payload: SlotRequest = {
      startTime: this.slotForm.value.startTime,
      endTime: this.slotForm.value.endTime,
      capacity: this.slotForm.value.capacity,
      bookedCount: this.slotForm.value.bookedCount,
      slotDate: this.slotForm.value.slotDate,
      status: this.slotForm.value.status
    };

    this.slotService.createSlot(payload).subscribe({
      next: () => {
        alert('Slot created successfully.');
        this.slotForm.reset();
      },
      error: () => alert('Error creating slot.')
    });
  }


  /** UPDATE — called by form submit when isUpdateMode = true */
  onUpdateSubmit(): void {
    if (this.slotForm.invalid) {
      alert('Please fill all required fields correctly.');
      return;
    }
    if (!this.startBeforeEnd()) {
      alert('Start Time must be earlier than End Time.');
      return;
    }

    if (!this.slotId) {
      alert('Missing slot ID for update.');
      return;
    }

    // Build payload similar to create
    const payload: SlotRequest = {
      startTime: this.slotForm.value.startTime,
      endTime: this.slotForm.value.endTime,
      capacity: this.slotForm.value.capacity,
      bookedCount: this.slotForm.value.bookedCount,
      slotDate: this.slotForm.value.slotDate,
      status: this.slotForm.value.status
    };

    this.slotService.updateSlot(this.slotId, payload).subscribe({
      next: () => {
        alert('Slot updated successfully.');
        // Optionally navigate back to list or reset form
        // this.router.navigate(['/gymslots']);
      },
      error: () => alert('Error updating slot.')
    });
  }

}
function normalizeTime(startTime: string): any {
  throw new Error('Function not implemented.');
}

