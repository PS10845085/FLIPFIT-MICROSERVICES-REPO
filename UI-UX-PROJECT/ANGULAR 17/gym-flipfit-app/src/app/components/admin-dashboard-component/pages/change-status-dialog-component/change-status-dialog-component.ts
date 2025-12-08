
// src/app/components/admin-dashboard-component/pages/change-status-dialog.component.ts
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export type UserStatus = 'ACTIVE' | 'INACTIVE' | 'PENDING';

export interface ChangeStatusDialogData {
  currentStatus: UserStatus;
  username: string;
}

@Component({
  selector: 'app-change-status-dialog',
  templateUrl: './change-status-dialog-component.html',
  standalone: false
})
export class ChangeStatusDialogComponent implements OnInit {
  statuses: UserStatus[] = ['ACTIVE', 'INACTIVE', 'PENDING'];
  selected!: UserStatus;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: ChangeStatusDialogData,
    private dialogRef: MatDialogRef<ChangeStatusDialogComponent>
  ) {
  }

  
 ngOnInit(): void {
    // set selection after dialog is fully initialized
    this.selected = this.data.currentStatus;
  }


  confirm() {
    this.dialogRef.close(this.selected);
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
