import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';
import { inject } from '@angular/core';
import { UserService } from '../services/user.service';

export const authGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);
  const userService = inject(UserService);
  const isTokenExpired = tokenService.isTokenExpired();
  const isUserValid = tokenService.getUserId() > 0;
  if (!isTokenExpired && isUserValid) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
