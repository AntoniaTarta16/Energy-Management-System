import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';


export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const router: Router = inject(Router);
  const jwtTokenPresent: boolean = route.data['jwtTokenPresent'];
  const redirectUrl: string = route.data['redirectUrl'];
  const hasToken: boolean = isTokenPresent();

  return jwtTokenPresent === hasToken
    ? true
    : router.navigateByUrl(redirectUrl);
  };

  const isTokenPresent = (): boolean => {
    return sessionStorage.getItem('jwt-token') !== null;
  };


