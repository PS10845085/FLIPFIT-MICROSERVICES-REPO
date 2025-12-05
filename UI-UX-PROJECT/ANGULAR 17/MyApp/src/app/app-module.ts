import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay, bootstrapApplication, importProvidersFrom } from '@angular/platform-browser';


import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

// Import Material modules
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { RegisterComponent } from './components/register-component/register-component';
import { LoginComponent } from './components/login-component/login-component';

bootstrapApplication(App, {
  providers: [
    // Bring NgModule-based modules into the standalone app:
    importProvidersFrom(BrowserAnimationsModule),
    importProvidersFrom(AppRoutingModule),
    importProvidersFrom(ReactiveFormsModule), // only needed if you use forms in non-standalone components

    // Functional providers
    provideHttpClient(),
  ]
}).catch(err => console.error(err));


@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent
  ],
  imports: [
    App,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    
// Make Material components available to declared components
    MatCardModule,
    MatButtonModule


  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideClientHydration(withEventReplay()),
    provideHttpClient()

  ],
  bootstrap: [App]
})
export class AppModule { }
