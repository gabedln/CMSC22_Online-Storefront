package product;

import user.Seller;
import java.util.Scanner;

public class Product {
    private String name;
    private float price;
    private int stock;
    private Seller seller;
    private int id;
    private String category;
    private static int IDCounter = 0;

    public Product(String name, String category, float price, int stock, Seller seller) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.seller = seller;
        this.id = ++IDCounter;
    }

    public static Product fillInformation(Seller seller) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        String category = chooseCategory(sc);

        System.out.print("Enter price: ");
        float price = sc.nextFloat();

        System.out.print("Enter stock: ");
        int stock = sc.nextInt();

        Product product = new Product(name, category, price, stock, seller);
        System.out.println("Product created: " + product.getName() + " (ID: " + product.getId() + ")");
        return product;
    }

    private static String chooseCategory(Scanner sc) {
        System.out.println("Category options: ");
        System.out.println("[1] canned goods");
        System.out.println("[2] condiments");
        System.out.println("[3] snacks");
        System.out.println("[4] beverages");
        System.out.println("[5] health care");
        System.out.println("[6] cleaning");
        System.out.print("Choose the category of the product: ");

        int categoryChoice = sc.nextInt();
        sc.nextLine();

        switch (categoryChoice) {
            case 1: return "canned goods";
            case 2: return "condiments";
            case 3: return "snacks";
            case 4: return "beverages";
            case 5: return "health care";
            case 6: return "cleaning";
            default: return "unknown";
        }
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public float getPrice() { return price; }
    public int getStock() { return stock; }
    public Seller getSeller() { return seller; }
    public String getCategory() { return category; }

    public void setPrice(float price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}
