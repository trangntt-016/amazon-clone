export class CartItemDto {
  userId: string;
  productId: number;
  quantity: number;
  constructor(userId, productId){
    this.userId = userId;
    this.productId = productId;
    this.quantity = 1;
  }
}
