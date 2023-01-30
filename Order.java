import java.io.*;
import java.util.*;  

public class Order implements Serializable {
    private int state = 0; // state -1 cancled | state 0 ordered | state 1 accepted | state 2 delivered
    private User user;
    private Shop shop;
    Vector<Item> items = new Vector<Item> ();
    private static Scanner scanner = new Scanner(System.in);
    
    public Order() {

    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void delItem(int index) {
        items.remove(index);
    }
    public User getUser() {
        return user;
    }
    public Shop getShop() {
        return shop;
    }
    public int getState() {
        return state;
    }
    public int getPrice() {
        int price = 0;
        for(Item item : items) {
            price += item.getPrice();
        }
        return price;
    }
    public void show() {
        System.out.println("shopname : " + shop.getName());
        System.out.println("customername : " + user.getName());
        System.out.println("Price : " + this.getPrice());
        System.out.println();
        System.out.println("name | type | price");
        for(Item item : items) {
            System.out.println(item.getName() + " | " + item.getType() + " | " + item.getPrice());
        }
    }
    public void edit() {
        String inp;
        this.show();
        System.out.println("cancle | accept | delivered");
        inp = scanner.nextLine();
        switch(inp) {
            case "cancle":
                if(this.getState() == 0) {
                    this.setState(-1);
                }
                else {
                    System.out.println("you cant do this");
                }
                break ;
            case "accept":
                if(this.getState() == 0) {
                    this.setState(1);
                }
                else {
                    System.out.println("you cant do this");
                }
                break ;
            case "delivered":
                if(this.getState() == 1) {
                    this.setState(2);
                }
                else {
                    System.out.println("you cant do this");
                }
                break ;
            default :
                System.out.println("not valid request");
        }
    }
}