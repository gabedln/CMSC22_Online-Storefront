package user;

import product.*;
import java.util.ArrayList;

public class Seller extends User {

    private static final long serialVersionUID = 1L;

    private ArrayList<Product> productList = new ArrayList<>();             // visible products
    private ArrayList<Product> hiddenList = new ArrayList<>();              // hidden products
    private ArrayList<Vouchers> voucherList = new ArrayList<>();            // vouchers offered
    private ArrayList<TransactionHistory> transactions = new ArrayList<>();

    public Seller(String displayName, String username, String password, float balance, String location) {
        super(displayName, username, password, balance, location);
    }

    // Add an existing product to seller list
    public void sell(Product product) {
        if (product != null) {
            productList.add(product);
            // storeProducts.add(product); 
        }
    }

    public void addProduct(Product product) {
        if (product != null) productList.add(product);
    }

    public void hideProduct(Product product) {
        if (productList.remove(product)) hiddenList.add(product);
    }

    public void unhideProduct(Product product) {
        if (hiddenList.remove(product)) productList.add(product);
    }

    public void addVoucher(Vouchers voucher) {
        if (voucher != null) voucherList.add(voucher);
    }

    public void removeVoucher(Vouchers voucher) {
        voucherList.remove(voucher);
    }

    public void logTransaction(TransactionHistory transaction) {
        if (transaction != null) transactions.add(transaction);
    }

    public ArrayList<TransactionHistory> getTransactions() { return transactions; }

    // Lists
    public ArrayList<Product> getProductList() { return productList; }
    public ArrayList<Product> getHiddenList() { return hiddenList; }
    public ArrayList<Vouchers> getVoucherList() { return voucherList; }

    @Override
    public void displayDashboard() {
        System.out.println("=== Seller Dashboard ===");
        System.out.println("Name: " + getDisplayName());
        System.out.println("Balance: ₱" + getBalance()); // use parent getter
        System.out.println("Products Listed: " + productList.size());
        System.out.println("Hidden Products: " + hiddenList.size());
        System.out.println("Vouchers Offered: " + voucherList.size());
        System.out.println("========================");
    }
}
