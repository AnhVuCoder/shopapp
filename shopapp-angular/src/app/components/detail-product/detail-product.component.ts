import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { ProductImage } from '../../models/product_image';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-detail-product',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule, CommonModule],
  templateUrl: './detail-product.component.html',
  styleUrl: './detail-product.component.scss',
})
export class DetailProductComponent implements OnInit {
  public productId: number = 0;
  public product!: Product;
  public selectedImageIndex: number = 0;
  public quantity: number = 1;
  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) {}
  ngOnInit(): void {
    const idParam = 1;
    if (idParam !== null) {
      this.productId = +idParam;
    }
    debugger;
    if (!isNaN(this.productId)) {
      this.productService.getDetailProduct(this.productId).subscribe({
        next: (response: Product) => {
          debugger;
          if (response.product_images && response.product_images.length > 0) {
            response.product_images.forEach((productImage: ProductImage) => {
              productImage.image_url = `${environment.apiBaseUrl}/products/images/${productImage.image_url}`;
            });
            this.product = response;
            this.showImage(0);
          }
        },
        error: (err) => console.log(err),
      });
    }
  }
  showImage(index: number) {
    let size = this.product.product_images.length;
    if (this.product && this.product.product_images && size) {
      if (index < 0) index = 0;
      if (index >= size) index = size - 1;
      this.selectedImageIndex = index;
    }
  }
  thumbnailClick(index: number) {
    this.selectedImageIndex = index;
  }
  nextImage() {
    this.showImage(this.selectedImageIndex + 1);
  }
  previousImage() {
    this.showImage(this.selectedImageIndex - 1);
  }
  plus() {
    this.quantity += 1;
  }
  subtract() {
    if (this.quantity >= 2) {
      this.quantity -= 1;
    }
  }
  onAddToCart(): void {
    console.log(this.quantity, this.productId);
    this.cartService.addToCart(this.productId, this.quantity);
  }
}
