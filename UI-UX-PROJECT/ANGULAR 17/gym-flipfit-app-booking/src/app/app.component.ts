import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'MyApp';
  message: string = 'Initial message';

  handleClick(): void {
    this.message = 'Button clicked!';
    console.log('Button was clicked!');
  }
}
