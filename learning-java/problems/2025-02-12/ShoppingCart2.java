package Aplication;

import java.util.ArrayList;

class Product {
    private String brand;
    private double price;

    public Product(String brand, double price) {
        this.brand = brand;
        this.price = price;
    }

    public String getBrand(){
        return this.brand;
    }

    public double getPrice(){
        return this.price;
    }

    public String toString(){
        return this.brand + ", " + String.format("%.2f", this.price);
    }
}

class ShoppingCart {
    private int costumerId;
    private ArrayList<Product> productList;


    public ShoppingCart(int costumerId) {
        this.costumerId = costumerId;
        this.productList = new ArrayList<Product>();
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void addProduct(Product p){
        if(p instanceof Product){
            this.productList.add(p);
        }
    }

    public void remove(Product p){
        if(p instanceof Product) {
            this.productList.remove(p);
        }
    }

    public String getContents() {
        if(this.productList.size() == 0){
            return "The cart is empty!";
        }

        StringBuilder sb = new StringBuilder("Brands and prices of products in the cart:\n");
        for (Product p : productList) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

    public int getCustumerId(){
        return this.costumerId;
    }

    public int getItemCount(){
        return this.productList.size();
    }


    public double getTotalPrice(){
        double sum = 0;
        for(int i = 0; i < this.productList.size(); i++){
            sum += this.productList.get(i).getPrice();
        }
        return sum;
    }
}

class Tv extends Product{
    private int inches;

    public Tv(String brand, double price, int inches) {
        super(brand, price);
        this.inches = inches;
    }

    public int getInches(){
        return inches;
    }
}

class Refrigerator extends Product{
    private int size;

    public Refrigerator(String brand, double price, int size) {
        super(brand, price);
        this.size = size;
    }

    public int getSize(){
        return size;
    }
}

class Stove extends Product{
    private int burners;

    public Stove(String brand, double price, int burnes) {
        super(brand, price);
        this.burners = burnes;
    }

    public int getBurners(){
        return burners;
    }
}

public class Supermarket {

    public static void main(String[] args) {
        Tv s25t = new Tv("Samsung", 500, 32);
        Refrigerator e12 = new Refrigerator("Eletrolux", 750, 12);
        Stove f21 = new Stove("Eletrolux", 500, 7);

        ShoppingCart myCart = new ShoppingCart(1);
        myCart.addProduct(s25t);
        myCart.addProduct(e12);
        myCart.addProduct(f21);

        System.out.println(myCart.getContents());
        System.out.println("Total amount: $" + String.format("%.2f", myCart.getTotalPrice()));

    }
}
