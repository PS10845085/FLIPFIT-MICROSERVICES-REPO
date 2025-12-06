
// src/app/guards/auth.guard.ts
import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state): boolean | UrlTree => {
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);
  const isBrowser = isPlatformBrowser(platformId);

  // Allow SSR to render; client-side will enforce auth
  if (!isBrowser) {
    return true;
  }

  // Permit access to the public login route
  if (state.url === '/login') {
    return true;
  }

  const auth = inject(AuthService);
  const user = auth.currentUser();

  if (user?.token) {
    return true;
  }

  // Redirect unauthenticated users to /login
  return router.parseUrl('/login');
};
