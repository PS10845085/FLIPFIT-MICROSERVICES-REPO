
// server.routes.ts
import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  // Dashboard needs auth; guards often rely on client-only state (e.g., localStorage),
  // so let the client render it to avoid SSR guard issues:
  { path: 'dashboard', renderMode: RenderMode.Client },

  // Prerender everything else as static (adjust as you need)
  { path: '**', renderMode: RenderMode.Prerender }
];
