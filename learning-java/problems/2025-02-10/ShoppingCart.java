package Aplication;

import java.util.ArrayList;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public String toString(){
        return this.name;
    }
}

class Cart {
    private int costumerId;
    private ArrayList<Product> productList;


    public Cart(int costumerId) {
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

        StringBuilder sb = new StringBuilder("Products in the cart:\n");
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

public class ShoppingCart {

    public static void main(String[] args) {
        Product cheese = new Product("Cheese", 5);
        Product strawberry = new Product("Strawberry", 2.5);
        Product pen = new Product("Pen", 1);

        Cart myCart = new Cart(1);
        myCart.addProduct(cheese);
        myCart.addProduct(strawberry);
        myCart.addProduct(pen);

        System.out.println(myCart.getContents());
        System.out.println("Total amount: $" + String.format("%.2f", myCart.getTotalPrice()));

    }
}
