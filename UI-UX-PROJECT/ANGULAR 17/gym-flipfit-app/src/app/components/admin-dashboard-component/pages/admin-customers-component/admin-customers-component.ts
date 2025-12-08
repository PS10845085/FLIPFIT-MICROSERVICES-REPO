// src/app/components/admin-dashboard-component/pages/admin-customers.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CustomerService, Customer } from '../../../../services/customer.service';

import { GymAdminService } from '../../../../services/admin.service';
import { ChangeStatusDialogComponent, UserStatus } from '../change-status-dialog-component/change-status-dialog-component';



type UsersRow = {
  id: number;
  name: string;
  email: string;
  mobile: string;
  center: string;
  city: string;
  status: string;
  role: string;
  createdAt: string;
  updatedAt: string;
  username: string;
  userId: number; // GymUser.id
};

@Component({
  selector: 'app-admin-customers',
  templateUrl: './admin-customers-component.html',
  styleUrls: ['./admin-customers-component.css'],
  standalone: false
})
export class AdminCustomersComponent implements OnInit {
  loading = false;
  error: string | null = null;

  displayedColumns = [
    'id', 'name', 'email', 'mobile', 'center', 'city',
    'status', 'role', 'createdAt', 'updatedAt', 'username', 'actions'
  ];

  dataSource = new MatTableDataSource<UsersRow>([]);
  total = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  
constructor(
    private customers: CustomerService,
    private usersApi: GymAdminService,
    private dialog: MatDialog,
    private snack: MatSnackBar

  ) {}


  ngOnInit(): void {
    this.load();
    this.setupFilter();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  load(): void {
    this.loading = true;
    this.error = null;

    this.customers.list().subscribe({
      next: (list) => {
        const rows = list.map(c => this.toRow(c));
        this.dataSource.data = rows;
        this.total = rows.length;
        this.loading = false;
        // Re-attach paginator/sort after data set (defensive)
        if (this.paginator) this.dataSource.paginator = this.paginator;
        if (this.sort) this.dataSource.sort = this.sort;
      },
      error: (err) => {
        this.loading = false;
        this.error = err.message ?? 'Unable to load customers';
      }
    });
  }

  refresh(): void {
    this.load();
  }

  applyFilter(value: string): void {
    this.dataSource.filter = value.trim().toLowerCase();
  }

  private setupFilter(): void {
    this.dataSource.filterPredicate = (row, filter) => {
      const f = filter.toLowerCase();
      return (
        row.name.toLowerCase().includes(f) ||
        row.email.toLowerCase().includes(f) ||
        row.username.toLowerCase().includes(f) ||
        row.city.toLowerCase().includes(f) ||
        row.status.toLowerCase().includes(f) ||
        row.role.toLowerCase().includes(f) ||
        row.center.toLowerCase().includes(f) ||
        row.mobile.includes(f) 
      );
    };
  }

  private toRow(c: Customer): UsersRow {
    const createdAt = this.arrayToDateString(c.createdAt);
    const updatedAt = this.arrayToDateString(c.updatedAt);
    const role = this.roleName(c.user?.roleid);
    const mobile = (c.mobile ?? '').toString();
    return {
      id: c.id,
      name: `${c.firstname} ${c.lastname}`.trim(),
      email: c.email,
      mobile,
      center: c.center?.name ?? '', 
      city: c.address?.city ?? '',
      status: c.user?.status ?? '',
      role,
      createdAt,
      updatedAt,
      username: c.user?.username ?? '',
      userId: c.user?.id ?? 0

    };
  }

  private roleName(roleid?: 1 | 2 | 3): string {
    switch (roleid) {
      case 1: return 'OWNER';
      case 2: return 'ADMIN';
      case 3: return 'CUSTOMER';
      default: return '';
    }
  }

  /**
   * Backend gives timestamps as arrays like:
   * [year, month, day, hour, minute, second, nanos]
   * We convert to local Date and format.
   */
  private arrayToDateString(arr?: number[]): string {
    if (!arr || arr.length < 6) return '';
    const [y, m, d, hh, mm, ss] = arr;
    const dt = new Date(y, (m ?? 1) - 1, d ?? 1, hh ?? 0, mm ?? 0, ss ?? 0);
    return new Intl.DateTimeFormat(undefined, {
      year: 'numeric', month: 'short', day: '2-digit',
      hour: '2-digit', minute: '2-digit'
    }).format(dt);
  }


  
// ===== Actions =====

  changeStatus(row: UsersRow): void {
    const dialogRef = this.dialog.open(ChangeStatusDialogComponent, {
      width: '360px',
      data: {
        currentStatus: row.status,
        username: row.username
      }
    });

    dialogRef.afterClosed().subscribe((newStatus: UserStatus | null) => {
      if (!newStatus || newStatus === row.status) return; // cancelled or no change

      // Call API
      this.loading = true;
      this.usersApi.updateStatus({ id: row.userId, status: newStatus }).subscribe({
        next: (updated) => {
          this.loading = false;
          // Update row locally for instant feedback
          row.status = updated.status as UserStatus;
          this.dataSource.data = [...this.dataSource.data]; // trigger table refresh

          this.snack.open(`Status updated to ${updated.status} for ${updated.username}`, 'OK', {
            duration: 2500
          });
        },
        error: (err) => {
          this.loading = false;
          this.snack.open(err.message ?? 'Failed to update status', 'Dismiss', {
            duration: 3000
          });
        }
      });
    });
  }
  
}

