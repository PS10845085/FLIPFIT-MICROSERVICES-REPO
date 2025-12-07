

// src/app/components/admin-dashboard-component/pages/admin-gyms.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CenterService, Center } from '../../../../services/center.service';

type GymRow = {
  id: number;
  name: string;
  ownerId: number;
  emailId: string;
  phoneNo: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
  status: 'ACTIVE' | 'INACTIVE' | 'PENDING';
  addressLine: string; // line1 + line2
};

@Component({
  selector: 'app-admin-gyms',
  templateUrl: './admin-gyms-component.html',
  styleUrls: ['./admin-gyms-component.css'],
  standalone: false
})
export class AdminGymsComponent implements OnInit {
  loading = false;
  error: string | null = null;

  displayedColumns = [
    'id', 'name', 'ownerId', 'emailId', 'phoneNo',
    'city', 'state', 'postalCode', 'country', 'status'
  ];

  dataSource = new MatTableDataSource<GymRow>([]);
  total = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private centers: CenterService) {}

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

    this.centers.getCenters().subscribe({
      next: (list) => {
        const rows = (list ?? []).map(c => this.toRow(c));
        this.dataSource.data = rows;
        this.total = rows.length;
        this.loading = false;

        // Defensive re-attach
        if (this.paginator) this.dataSource.paginator = this.paginator;
        if (this.sort) this.dataSource.sort = this.sort;
      },
      error: (err) => {
        this.loading = false;
        this.error = err.message ?? 'Unable to load centers';
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
        String(row.ownerId).includes(f) ||
        (row.emailId ?? '').toLowerCase().includes(f) ||
        (row.phoneNo ?? '').toLowerCase().includes(f) ||
        (row.city ?? '').toLowerCase().includes(f) ||
        (row.state ?? '').toLowerCase().includes(f) ||
        (row.postalCode ?? '').toLowerCase().includes(f) ||
        (row.country ?? '').toLowerCase().includes(f) ||
        (row.status ?? '').toLowerCase().includes(f) ||
        (row.addressLine ?? '').toLowerCase().includes(f)
      );
    };
  }

  private toRow(c: Center): GymRow {
    const a = c.address ?? ({} as any);
    const addressLine = [a.line1, a.line2].filter(Boolean).join(', ');
    return {
      id: c.id,
      name: c.name,
      ownerId: c.ownerId,
      emailId: c.emailId ?? '',
      phoneNo: c.phoneNo ?? '',
      city: a.city ?? '',
      state: a.state ?? '',
      postalCode: a.postalCode ?? '',
      country: a.country ?? '',
      status: c.status,
      addressLine
    };
  }
}
