
// src/app/guards/role.guard.ts
import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const allowed: Array<1 | 2 | 3> = route.data?.['roles'] ?? [];
  const roleId = auth.getCurrentRoleId();

  // If user has a role and it's allowed, proceed
  if (roleId && allowed.includes(roleId)) {
    return true;
  }

  // Otherwise, redirect to user's own dashboard (no default/common dashboard)
  const target = auth.getDashboardPathForRole(roleId);
  return router.createUrlTree([target], { queryParams: { unauthorized: 'true' } });
};
