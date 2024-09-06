import { Component, OnInit } from '@angular/core';
import { Route, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/UserResponse';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../services/token.service';
import { NgbModule, NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, NgbModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  ngOnInit(): void {
    this.userResponse = this.userService.getUserDetailsFromLocalStorage();
  }
  constructor(
    private userService: UserService,
    private popoverConfig: NgbPopoverConfig,
    private tokenService: TokenService,
    private router: Router
  ) {}
  activeNavItem: number = 0;
  userResponse?: UserResponse | null;
  isPopoverOpen = false;

  togglePopover(event: Event): void {
    event.preventDefault();
    this.isPopoverOpen = !this.isPopoverOpen;
  }

  handleItemClick(index: number): void {
    //alert(`Clicked on "${index}"`);
    if (index == 0) {
      this.router.navigate(['/profile']);
    } else if (index == 1) {
    } else if (index == 2) {
      this.userService.removeUserDetailsFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse = this.userService.getUserDetailsFromLocalStorage();
      this.router.navigate(['/login']);
    }
    this.isPopoverOpen = false; // Close the popover after clicking an item
  }
  setActiveNavItem(index: number) {
    this.activeNavItem = index;
  }
}
