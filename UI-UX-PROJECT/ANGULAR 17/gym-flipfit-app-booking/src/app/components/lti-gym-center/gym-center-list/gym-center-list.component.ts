import { Router } from "@angular/router";
import { Center } from "../../../models/gym-center/center-response";
import { GymCenterService } from "../../../services/center/gym-center.service";

export class GymCenterListComponent {
  centers: Center[] = [];
  filteredCenters: Center[] = [];
  loading = true;
  errorMessage = '';
  searchTerm = '';

  constructor(private gymCenterService: GymCenterService, private router: Router) {}

  ngOnInit(): void {
    this.fetchCenters();
  }

  fetchCenters(): void {
    this.gymCenterService.getAllCenters().subscribe({
      next: (response) => {
        console.log(response);
        this.centers = response.data; // ✅ extract array
        this.filteredCenters = [...this.centers]; // ✅ now iterable
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

  onUpdate(center: Center): void {
    this.router.navigate(['/create-center', center.id], { state: { center } });
  }

  onDelete(id: number): void {
    const agree = window.confirm(`Are you sure you want to delete center #${id}?`);
    if (!agree) return;

    this.loading = true;
    this.gymCenterService.deleteCenter(id).subscribe({
      next: () => {
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
}
