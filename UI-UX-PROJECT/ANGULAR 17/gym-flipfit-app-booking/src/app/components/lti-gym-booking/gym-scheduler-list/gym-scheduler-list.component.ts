
import { Component, OnInit } from '@angular/core';
import { GymSchedulerService } from '../../../services/booking/gym-scheduler.service';
import { SchedulerRequest, SchedulerResponse } from '../../../models/gym-booking/scheduler-request';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-gym-scheduler-list',
  templateUrl: './gym-scheduler-list.component.html',
  styleUrls: ['./gym-scheduler-list.component.css']
})
export class GymSchedulerListComponent implements OnInit {
  schedules: SchedulerResponse[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private schedulerService: GymSchedulerService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.fetchSchedules();
  }

  /** Fetch all schedules from API */
  private fetchSchedules(): void {
    this.loading = true;
    this.schedulerService.getAllSchedules().subscribe({
      next: (data) => {
        this.schedules = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load schedules:', err);
        this.errorMessage = 'Error loading schedules.';
        this.loading = false;
      }
    });
  }

  /** Navigate to create form */
  onCreate(): void {
    // Example navigation logic
    // this.router.navigate(['/schedules/create']);
  }


  // components/scheduler/scheduler-list.component.ts (snippet)
  onUpdate(schedule: SchedulerResponse): void {
    this.router.navigate(['/create-schedule', schedule.id], { state: { schedule } });

  }


/** Delete schedule with confirmation + loading flag */
onDelete(id: number): void {
  if (!confirm(`Are you sure you want to delete schedule #${id}?`)) return;

  this.loading = true;

  this.schedulerService.deleteScheduleById(id).subscribe({
    next: (message: string) => {
      // message is whatever backend returns: "Deleted", "Schedule removed", etc.
      alert(message || `Schedule #${id} deleted successfully.`);
      this.loading = false;

      // Refresh the list (same as your existing fetch method)
      this.fetchSchedules();

      // OR update list locally:
      // this.schedules = this.schedules.filter(s => s.id !== id);
    },
    error: (err) => {
      console.error('Delete error:', err);

      // Try to surface backend message if provided
      this.loading = false;
    }
  });
}
}
