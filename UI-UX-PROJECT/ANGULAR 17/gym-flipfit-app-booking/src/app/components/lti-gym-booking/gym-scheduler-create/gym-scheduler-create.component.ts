
// components/scheduler/scheduler-create.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GymSchedulerService } from '../../../services/booking/gym-scheduler.service';
import { SchedulerRequest, SchedulerResponse } from '../../../models/gym-booking/scheduler-request';

@Component({
  selector: 'app-gym-scheduler-create',
  templateUrl: './gym-scheduler-create.component.html',
  styleUrls: ['./gym-scheduler-create.component.css']
})
export class GymSchedulerCreateComponent implements OnInit {
  scheduleForm!: FormGroup;
  isUpdateMode = false;
  scheduleId?: number;

  constructor(
    private fb: FormBuilder,
    private schedulerService: GymSchedulerService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.scheduleForm = this.fb.group({
      validFrom: ['', [Validators.required]], // "YYYY-MM-DD"
      validTill: ['', [Validators.required]]  // "YYYY-MM-DD"
    });

    // 1) Try to hydrate from router state (no extra API call)
    const nav = this.router.getCurrentNavigation();
    const stateSchedule = nav?.extras?.state?.['schedule'] as SchedulerResponse | undefined;

    // 2) Always read :id so refresh still works
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.scheduleId = Number(idParam);
      this.isUpdateMode = true;
    }

    if (stateSchedule) {
      this.patchForm(stateSchedule);
    } else if (this.isUpdateMode && this.scheduleId) {
      this.loadScheduleData(this.scheduleId);
    }
  }

  /** Populate form for update (normalize incoming date formats to YYYY-MM-DD) */
  private patchForm(sch: SchedulerResponse & { validFrom?: string; validTill?: string }): void {
    this.scheduleForm.patchValue({
      validFrom: toDateInput(sch.validFrom),
      validTill: toDateInput(sch.validTill)
    });
  }

  /** Fallback load by id if route state is not present (e.g., after refresh) */
  private loadScheduleData(id: number): void {
    this.schedulerService.getScheduleById(id).subscribe({
      next: (sch) => {
        // Assume API returns { id, validFrom, validTill, ... }
        this.patchForm(sch as SchedulerResponse & { validFrom: string; validTill: string });
      },
      error: (err) => {
        console.error('Failed to load schedule', err);
        alert('Failed to load schedule details.');
      }
    });
  }

  /** Ensure validFrom <= validTill */
  private isDateRangeValid(): boolean {
    const { validFrom, validTill } = this.scheduleForm.value;
    if (!validFrom || !validTill) return false;
    return new Date(validFrom) <= new Date(validTill);
  }

  /** Single submit path that branches by mode */
  onSubmit(): void {
    if (this.scheduleForm.invalid) {
      alert('Please fill all required fields.');
      return;
    }
    if (!this.isDateRangeValid()) {
      alert('Valid From must be earlier than or equal to Valid Till.');
      return;
    }

    const raw = this.scheduleForm.value;
    const payload: SchedulerRequest = {
      // If backend wants ISO date: toISODateString(raw.validFrom) / toISODateString(raw.validTill)
      validFrom: String(raw.validFrom),
      validTill: String(raw.validTill)
    };

    if (!this.isUpdateMode) {
      this.schedulerService.createSchedule(payload).subscribe({
        next: (res) => {
          console.log('create response', res);

          this.scheduleForm.reset();
        },
        error: () => alert('Error creating schedule.')
      });
      return;
    }

    // UPDATE branch
    if (!this.scheduleId && this.route.snapshot.paramMap.get('id')) {
      this.scheduleId = Number(this.route.snapshot.paramMap.get('id'));
    }
    if (!this.scheduleId) {
      alert('Missing schedule ID for update.');
      return;
    }

    this.schedulerService.updateSchedule(this.scheduleId, payload).subscribe({
      next: (res) => {
        console.log('update response', res);
        alert("updated successfully");
      },
      error: () => alert('Error updating schedule.')
    });
  }

  /** Cancel edit back to create mode */
  onCancelEdit(): void {
    this.isUpdateMode = false;
    this.scheduleId = undefined;
    this.scheduleForm.reset();
  }
}

/** Helpers: normalize to 'YYYY-MM-DD' for <input type="date"> */
function toDateInput(value?: string): string {
  if (!value) return '';
  // Already 'YYYY-MM-DD'
  if (/^\d{4}-\d{2}-\d{2}$/.test(value)) return value;
  // ISO or other parseable formats
  const d = new Date(value);
  if (Number.isNaN(d.getTime())) return '';
  return new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate())).toISOString().slice(0, 10);
}

function toISODateString(dateInput: string): string {
  // Convert 'YYYY-MM-DD' to ISO with UTC midnight
  const [y, m, d] = dateInput.split('-').map(Number);
  const iso = new Date(Date.UTC(y, (m ?? 1) - 1, d ?? 1)).toISOString();
  return iso;
}
