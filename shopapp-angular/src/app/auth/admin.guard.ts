import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';
import { UserService } from '../services/user.service';

export const adminGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);
  const userService = inject(UserService);
  const userResponse = userService.getUserDetailsFromLocalStorage();
  const isAdmin = userResponse.role.id === 1;
  const isTokenExpired = tokenService.isTokenExpired();
  const isUserValid = tokenService.getUserId();
  if (!isTokenExpired && isUserValid && isAdmin) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
