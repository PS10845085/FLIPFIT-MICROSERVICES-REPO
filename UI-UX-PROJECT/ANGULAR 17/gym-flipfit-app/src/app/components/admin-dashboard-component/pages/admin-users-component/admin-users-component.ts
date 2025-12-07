// src/app/components/admin-dashboard-component/pages/admin-users.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CustomerService, Customer } from '../../../../services/customer.service';

type UsersRow = {
  id: number;
  name: string;
  email: string;
  mobile: string;
  centerid: number;
  city: string;
  status: string;
  role: string;
  createdAt: string;
  updatedAt: string;
  username: string;
};

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users-component.html',
  styleUrls: ['./admin-users-component.css'],
  standalone: false
})
export class AdminUsersComponent implements OnInit {
  loading = false;
  error: string | null = null;

  displayedColumns = [
    'id', 'name', 'email', 'mobile', 'centerid', 'city',
    'status', 'role', 'createdAt', 'updatedAt', 'username'
  ];

  dataSource = new MatTableDataSource<UsersRow>([]);
  total = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private customers: CustomerService) {}

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
        this.error = err.message ?? 'Unable to load users';
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
        row.mobile.includes(f) ||
        String(row.centerid).includes(f)
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
      centerid: c.centerid,
      city: c.address?.city ?? '',
      status: c.user?.status ?? '',
      role,
      createdAt,
      updatedAt,
      username: c.user?.username ?? ''
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
}
