package user;

import product.Product;
public class TransactionHistory {
   private Buyer buyer;
   private Seller seller;
   private Product product;
   private int quantity;
   private float totalCost;
   public TransactionHistory(Buyer buyer, Seller seller, Product product, int quantity, float totalCost) {
       this.seller = seller;
       this.buyer = buyer;
       this.product = product;
       this.quantity = quantity;
       this.totalCost = totalCost;
   }
  
   public void setTotalCost(float totalCost) {
       this.totalCost = totalCost;
   }
   // Getters for Buyer, Seller, Product, Quantity, TotalCost
   public Buyer getBuyer() { return buyer; }
   public Seller getSeller() { return seller; }
   public Product getProduct() { return product; }
   public int getQuantity() { return quantity; }
   public float getTotalCost() { return totalCost; }
  
  
}









