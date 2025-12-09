
// src/app/components/admin-dashboard-component/pages/admin-owners-component/admin-owners-component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { OwnerService, Owner } from '../../../../services/owner.service';
import { GymAdminService } from '../../../../services/admin.service'; // used for change status
import { ChangeStatusDialogComponent, UserStatus } from '../change-status-dialog-component/change-status-dialog-component';

type OwnersRow = {
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
  selector: 'app-admin-owners',
  templateUrl: './admin-owners-component.html',
  styleUrls: ['./admin-owners-component.css'],
  standalone: false
})
export class AdminOwnersComponent implements OnInit {
  loading = false;
  error: string | null = null;

  displayedColumns = [
    'id', 'name', 'email', 'mobile', 'center', 'city',
    'status', 'role', 'createdAt', 'updatedAt', 'username', 'actions'
  ];

  dataSource = new MatTableDataSource<OwnersRow>([]);
  total = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private owners: OwnerService,
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

    this.owners.list().subscribe({
      next: (list) => {
        const rows = list.map(o => this.toRow(o));
        this.dataSource.data = rows;
        this.total = rows.length;
        this.loading = false;
        if (this.paginator) this.dataSource.paginator = this.paginator;
        if (this.sort) this.dataSource.sort = this.sort;
      },
      error: (err) => {
        this.loading = false;
        this.error = err.message ?? 'Unable to load owners';
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

  private toRow(o: Owner): OwnersRow {
    const createdAt = this.arrayToDateString(o.createdAt);
    const updatedAt = this.arrayToDateString(o.updatedAt);
    const role = this.roleName(o.user?.roleid);
    const mobile = (o.mobile ?? '').toString();

    // Since center in your sample is an address-like object, pick a readable representation:
    const centerText =
      (o.center?.line1 ?? '') ||
      [o.center?.city, o.center?.state].filter(Boolean).join(', ') ||
      '';

    return {
      id: o.id,
      name: `${o.firstname} ${o.lastname}`.trim(),
      email: o.email,
      mobile,
      center: centerText,
      city: o.address?.city ?? o.center?.city ?? '',
      status: o.user?.status ?? '',
      role,
      createdAt,
      updatedAt,
      username: o.user?.username ?? '',
      userId: o.user?.id ?? 0
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
   * Convert to local Date and format human-friendly.
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
  changeStatus(row: OwnersRow): void {
    const dialogRef = this.dialog.open(ChangeStatusDialogComponent, {
      width: '360px',
      data: {
        currentStatus: row.status,
        username: row.username
      }
    });

    dialogRef.afterClosed().subscribe((newStatus: UserStatus | null) => {
      if (!newStatus || newStatus === row.status) return;

      this.loading = true;
      this.usersApi.updateStatus({ id: row.userId, status: newStatus }).subscribe({
        next: (updated) => {
          this.loading = false;
          row.status = updated.status as UserStatus;
          this.dataSource.data = [...this.dataSource.data];
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