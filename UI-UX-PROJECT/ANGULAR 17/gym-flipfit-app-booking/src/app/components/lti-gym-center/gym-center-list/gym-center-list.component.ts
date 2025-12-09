import { Component } from '@angular/core';
import { GymCenterService } from '../../../services/center/gym-center.service';
import { CenterResponse } from '../../../models/gym-center/center-response';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-gym-center-list',
  templateUrl: './gym-center-list.component.html',
  styleUrl: './gym-center-list.component.css'
})
export class GymCenterListComponent {

  centers: CenterResponse[] = [];
  filteredCenters: CenterResponse[] = []
  loading = true;
  errorMessage = '';
  searchTerm = '';

  constructor(private gymCenterService: GymCenterService,
    private router: Router,
    private route : ActivatedRoute
  ) { }

 
 ngOnInit(): void {
    this.fetchCenters();
  }

  fetchCenters(): void {
    this.gymCenterService.getAllCenters().subscribe({
      next: (data) => {
        this.centers = data;
        this.filteredCenters = [...data]; 
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load centers';
        console.error(err);
        this.loading = false;
      }
    });
  }

  filterCenters(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredCenters = [...this.centers]; 
      return;
    }

    this.filteredCenters = this.centers.filter(center =>
      center.name.toLowerCase().includes(term) ||
      center.id.toString().includes(term) ||
      center.ownerId.toString().includes(term)
    );
  }

// onUpdate(id: number): void {
//   console.log('Update center with ID:', id);
//   // Navigate to update page
//   // Example: this.router.navigate(['/update-center', id]);
// }

 onUpdate(center: CenterResponse): void {
    this.router.navigate(['/create-center', center.id], { state: { center } });

  }


 /** ✅ Delete with confirmation + UI update */
  onDelete(id: number): void {
    const agree = window.confirm(`Are you sure you want to delete center #${id}?`);
    if (!agree) return;

    // optimistic: show loading state for this action (optional)
    this.loading = true;

    this.gymCenterService.deleteCenter(id).subscribe({
      next: () => {
        // Remove from local arrays
        this.centers = this.centers.filter(c => c.id !== id);
        this.filteredCenters = this.filteredCenters.filter(c => c.id !== id);
        this.loading = false;
        alert(`Center #${id} deleted successfully.`);
      },
      error: (err) => {
        console.error('Delete failed', err);
        this.loading = false;
        alert('Error deleting center. Please try again.');
      }
    });
  }


onView(id: number): void {
  console.log('View center details with ID:', id);
  // Navigate to view page or open modal
  // Example: this.router.navigate(['/view-center', id]);
}

}
