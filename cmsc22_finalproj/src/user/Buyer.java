package user;

import product.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Buyer extends User {

    private static final long serialVersionUID = 1L;
    private static final Scanner sc = new Scanner(System.in);
    
    // Constants for menu options
    private static final int YES_OPTION = 1;
    private static final int NO_OPTION = 2;

    private ArrayList<Product> wishlist = new ArrayList<>();
    private ArrayList<Vouchers> voucherList = new ArrayList<>();
    private HashMap<Product, Integer> cart = new HashMap<>();
    private ArrayList<TransactionHistory> transactions = new ArrayList<>();
    private float balance;

    public Buyer(String displayName, String username, String password, float balance, String location) {
        super(displayName, username, password, balance, location);
        this.balance = balance;
    }

    @Override
    public void displayDashboard() {
        System.out.println("\n===== BUYER DASHBOARD =====");
        System.out.println("Username: " + getUsername());
        System.out.println("Display Name: " + getDisplayName());
        System.out.println("Location: " + getLocation());
        System.out.println("Balance: ₱" + String.format("%.2f", balance));
        System.out.println("Cart Items: " + cart.size());
        System.out.println("Wishlist Items: " + wishlist.size());
        System.out.println("Total Transactions: " + transactions.size());
        System.out.println("===========================\n");
    }

    public void setBalance(float balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    // Wishlist
    public void addToWishlist(Product product) {
        if (!wishlist.contains(product)) {
            wishlist.add(product);
        } else {
            System.out.println("Product already in wishlist.");
        }
    }

    public void removeWishlist(Product product) {
        wishlist.remove(product);
    }
    
    public void wishlistToCart(Product product) {
        if (wishlist.contains(product)) {
            cart.put(product, 1);
        }
    }

    // Cart
    public void addToCart(Product product) {
        cart.put(product, cart.getOrDefault(product, 0) + 1);
    }

    public void addQuantity(Product product) {
        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) + 1);
        }
    }

    public void reduceQuantity(Product product) {
        if (cart.containsKey(product)) {
            int qty = cart.get(product);
            if (qty <= 1) {
                cart.remove(product);
            } else {
                cart.put(product, qty - 1);
            }
        }
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        float overallTotal = 0f;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            float total = p.getPrice() * qty;

            System.out.println("ID: " + p.getId()
                    + " | Name: " + p.getName()
                    + " | Qty: " + qty
                    + " | Price each: ₱" + p.getPrice()
                    + " | Total: ₱" + total);

            overallTotal += total;
        }

        System.out.println("-----------------------------------");
        System.out.println("Overall total: ₱" + overallTotal);
    }

    // Purchase
    public void checkStock(ArrayList<Product> toBuy) {
        ArrayList<Product> outOfStock = new ArrayList<>();

        for (Product product : toBuy) {
            if (product.getStock() <= 0) {
                outOfStock.add(product);
            }
        }

        if (!outOfStock.isEmpty()) {
            System.out.println("Items out of stock:");
            for (Product product : outOfStock) {
                System.out.println(product.getName());
            }
        }
    }

    public void buy(ArrayList<Product> toBuy) {
        checkStock(toBuy);

        ArrayList<Seller> sellersBoughtFrom = getUniqueSellers(toBuy);
        
        System.out.println("Do you want to apply the best voucher deals? [1] Yes [2] No");
        int choice = sc.nextInt();
        sc.nextLine();

        HashMap<Seller, Vouchers> appliedVouchers = new HashMap<>();
        float overallCost = calculateTotalWithVouchers(toBuy, sellersBoughtFrom, choice, appliedVouchers);

        if (this.getBalance() < overallCost) {
            System.out.println("Insufficient balance.");
            return;
        }

        processPurchase(toBuy, sellersBoughtFrom, appliedVouchers);
        System.out.println("Purchase successful!");
    }

    private ArrayList<Seller> getUniqueSellers(ArrayList<Product> products) {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (Product p : products) {
            if (!sellers.contains(p.getSeller())) {
                sellers.add(p.getSeller());
            }
        }
        return sellers;
    }

    private float calculateTotalWithVouchers(ArrayList<Product> toBuy, 
                                            ArrayList<Seller> sellers, 
                                            int applyVouchers,
                                            HashMap<Seller, Vouchers> appliedVouchers) {
        float overallCost = 0f;

        for (Seller seller : sellers) {
            float subtotal = calculateSubtotalForSeller(toBuy, seller);

            if (applyVouchers == YES_OPTION) {
                Vouchers bestVoucher = findBestVoucher(seller, subtotal);
                
                if (bestVoucher != null) {
                    float savings = applyVoucher(bestVoucher, subtotal);
                    System.out.println("Best voucher found for " + seller.getUsername() +
                                       " | Discount: " + (bestVoucher.getDiscount() * 100) + "%" +
                                       " | Savings: ₱" + savings);
                    
                    subtotal -= savings;
                    appliedVouchers.put(seller, bestVoucher);
                }
            } else if (applyVouchers == NO_OPTION) {
                // No vouchers applied - proceed with regular pricing
            }

            overallCost += subtotal;
        }

        return overallCost;
    }

    private float calculateSubtotalForSeller(ArrayList<Product> products, Seller seller) {
        float subtotal = 0f;
        for (Product product : products) {
            if (seller == product.getSeller()) {
                subtotal += product.getPrice();
            }
        }
        return subtotal;
    }

    private Vouchers findBestVoucher(Seller seller, float subtotal) {
        Vouchers bestVoucher = null;
        float bestSavings = 0f;

        for (Vouchers v : voucherList) {
            if (v.getSeller() != seller) continue;
            if (subtotal < v.getMin()) continue;

            float discountAmount = subtotal * v.getDiscount();
            float cappedDiscount = Math.min(discountAmount, v.getCap());

            if (cappedDiscount > bestSavings) {
                bestSavings = cappedDiscount;
                bestVoucher = v;
            }
        }

        return bestVoucher;
    }

    private float applyVoucher(Vouchers voucher, float amount) {
        float discountAmount = amount * voucher.getDiscount();
        float cappedDiscount = Math.min(discountAmount, voucher.getCap());
        
        voucher.reduceQuantity();
        if (voucher.getQuantity() == 0) {
            voucherList.remove(voucher);
        }
        
        return cappedDiscount;
    }

    private void processPurchase(ArrayList<Product> toBuy, 
                                ArrayList<Seller> sellers,
                                HashMap<Seller, Vouchers> appliedVouchers) {
        for (Seller seller : sellers) {
            float subtotal = 0f;
            ArrayList<Product> purchasedFromSeller = new ArrayList<>();

            // Process each product from this seller
            for (Product p : toBuy) {
                if (p.getSeller() == seller) {
                    subtotal += p.getPrice();
                    p.setStock(p.getStock() - 1);
                    purchasedFromSeller.add(p);

                    TransactionHistory th = new TransactionHistory(this, seller, p, 1, p.getPrice());
                    transactions.add(th);
                }
            }

            // Apply voucher discount if applicable
            if (appliedVouchers.containsKey(seller)) {
                Vouchers v = appliedVouchers.get(seller);
                float discountAmount = subtotal * v.getDiscount();
                float cappedDiscount = Math.min(discountAmount, v.getCap());
                subtotal -= cappedDiscount;
            }

            // Update balances
            seller.setBalance(seller.getBalance() + subtotal);
            this.setBalance(this.getBalance() - subtotal);

            // Update transaction costs to reflect discounted prices
            updateTransactionCosts(seller, purchasedFromSeller, subtotal);

            // Remove purchased items from toBuy list
            toBuy.removeAll(purchasedFromSeller);
        }
    }

    private void updateTransactionCosts(Seller seller, 
                                       ArrayList<Product> purchasedProducts, 
                                       float totalCost) {
        for (TransactionHistory th : transactions) {
            if (th.getSeller() == seller && purchasedProducts.contains(th.getProduct())) {
                float perProductCost = totalCost / purchasedProducts.size();
                th.setTotalCost(perProductCost);
            }
        }
    }

    // Transaction History
    public void viewTransactionHistory() {
        System.out.println(getUsername() + "'s Transaction History:");

        if (transactions.isEmpty()) {
            System.out.println("No recorded transactions yet.");
            return;
        }

        int totalItems = 0;
        float totalSpent = 0f;

        for (TransactionHistory th : transactions) {
            System.out.println("ID: " + th.getProduct().getId()
                    + " | Name: " + th.getProduct().getName()
                    + " | Price: ₱" + th.getProduct().getPrice());

            totalItems += th.getQuantity();
            totalSpent += th.getTotalCost();
        }

        System.out.println("-----------------------------------");
        System.out.println("Total items bought: " + totalItems);
        System.out.println("Total amount spent: ₱" + totalSpent);
    }
    
    // Getters for collections
    public ArrayList<Product> getWishlist() {
        return wishlist;
    }
    
    public ArrayList<Vouchers> getVoucherList() {
        return voucherList;
    }
    
    public HashMap<Product, Integer> getCart() {
        return cart;
    }
    
    public ArrayList<TransactionHistory> getTransactions() {
        return transactions;
    }
}
