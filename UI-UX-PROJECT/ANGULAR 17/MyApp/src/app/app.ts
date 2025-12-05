import { Component, signal , ChangeDetectionStrategy} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  imports: [MatCardModule, MatButtonModule],
  standalone: true,
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('MyApp');
}
