
// src/app/interceptors/auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);
  const isBrowser = isPlatformBrowser(platformId);

  let token: string | undefined;

  if (isBrowser) {
    try {
      const sessionRaw = localStorage.getItem('demo_session');
      const session = sessionRaw ? (JSON.parse(sessionRaw) as { token?: string }) : null;
      token = session?.token;
    } catch {
      // ignore parsing/storage errors
    }
  }

  if (token) {
    const authReq = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
    return next(authReq);
  }

  return next(req);
}