
// src/app/guards/guest.guard.ts
import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { AuthService } from '../services/auth.service';

export const guestGuard: CanActivateFn = (route, state): boolean | UrlTree => {
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);
  const isBrowser = isPlatformBrowser(platformId);

  // Allow SSR to render; client will enforce auth state
  if (!isBrowser) {
    return true;
  }

  const auth = inject(AuthService);
  const user = auth.currentUser();

  // If already logged in, redirect away from login to dashboard
  if (user?.token) {
    const roleId = auth.getCurrentRoleId();
    const target = auth.getDashboardPathForRole(roleId);

    return router.parseUrl(target);
  }

  // Not logged in => allow access to login
  return true;
}