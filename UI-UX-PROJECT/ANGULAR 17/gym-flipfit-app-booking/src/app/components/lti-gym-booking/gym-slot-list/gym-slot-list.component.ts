
import { Component, OnInit } from '@angular/core';
import { GymSlotService } from '../../../services/booking/gym-slot.service';
import { SlotRequest, SlotResponse } from '../../../models/gym-booking/slot-request';
import { Router } from '@angular/router';



@Component({
  selector: 'app-gym-slot-list',
  templateUrl: './gym-slot-list.component.html',
  styleUrls: ['./gym-slot-list.component.css']
})
export class GymSlotListComponent implements OnInit {
  slots: SlotResponse[] = [];
  loading = true;
  errorMessage = '';

  constructor(private slotService: GymSlotService, private router : Router) {}

  ngOnInit(): void {
    this.fetchSlots();
  }

  fetchSlots(): void {
    this.slotService.getAllSlots().subscribe({
      next: (data) => {
        this.slots = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to load slots';
        this.loading = false;
      }
    });
  }

  /** Prompt-driven update for quick testing (replace with your own edit form) */
  onUpdate(slot: SlotResponse): void {
     this.router.navigate(['/create-slot', slot.id], { state: { slot } });
  }

  
/** Delete with confirmation */
onDelete(id: number): void {
  if (!confirm(`Are you sure you want to delete slot #${id}?`)) return;

  this.loading = true;

  this.slotService.deleteSlot(id).subscribe({
    next: (message: string) => {
      // message is whatever your backend returned, e.g. "Deleted" or "Slot removed"
      alert(message || `Slot #${id} deleted successfully.`);
      this.loading = false;
      // (Optional) If you want to update UI list, do it here:
      // this.slots = this.slots.filter(s => s.id !== id);
    },
    error: (err) => {
      console.error('Delete error:', err);
      alert('Error deleting slot.');
      this.loading = false;
    }
  });
}

}
