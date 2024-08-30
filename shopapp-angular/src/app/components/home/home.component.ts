import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { HttpClient } from '@angular/common/http';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';

import { CategoryService } from '../../services/category.service';
import { CommonModule } from '@angular/common';
import { Category } from '../../models/Category';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  public products!: Product[];
  public totalPages!: number;
  public itemsPerPage: number = 6;
  public currentPage: number = 1;
  public visiblePages: number[] = [];
  public categories: Category[] = [];
  public selectedCategoryId: number = 0;
  public txtSearchProducts: string = '';
  constructor(
    private productService: ProductService,
    private categoryService: CategoryService
  ) {}
  ngOnInit(): void {
    this.onGetProducts('', this.selectedCategoryId, 1, this.itemsPerPage);
    this.onGetCategories();
  }
  onGetProducts(
    keyword: string,
    categoryId: number,
    page: number,
    limit: number
  ) {
    this.productService
      .getProducts(keyword, categoryId, page, limit)
      .subscribe({
        next: (response: any) => {
          debugger;
          response.product_response_list.forEach((product: Product) => {
            product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          });
          this.products = response.product_response_list;
          this.totalPages = response.total_pages;
          this.visiblePages = this.generateVisiablePageArray(
            this.currentPage,
            this.totalPages
          );
        },
        complete: () => {},
        error: (err) => console.log(err),
      });
  }
  onPageChange(page: number) {
    this.currentPage = page;
    this.onGetProducts(
      this.txtSearchProducts,
      this.selectedCategoryId,
      this.currentPage,
      this.itemsPerPage
    );
  }
  generateVisiablePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePage = 5;
    const haftVisiblePage = Math.floor(maxVisiblePage / 2);
    let startPage = Math.max(currentPage - haftVisiblePage, 1);
    let endPage = Math.min(startPage + maxVisiblePage - 1, totalPages);
    if (endPage - startPage + 1 < maxVisiblePage) {
      startPage = Math.max(endPage - maxVisiblePage + 1, 1);
    }
    return new Array(endPage - startPage + 1)
      .fill(0)
      .map((_, index) => startPage + index);
  }
  onGetCategories() {
    this.categoryService.getAllCategories().subscribe({
      next: (response) => {
        console.log(response);
        this.categories = response;
      },
      complete: () => {},
      error: (err) => {
        console.log(err);
      },
    });
  }
  onSearchProducts() {
    this.currentPage = 1;
    this.itemsPerPage = 6;
    this.onGetProducts(
      this.txtSearchProducts,
      this.selectedCategoryId,
      this.currentPage,
      this.itemsPerPage
    );
  }
}
