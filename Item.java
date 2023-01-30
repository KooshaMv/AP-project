import java.io.*;
import java.util.*;  

public class Item implements Serializable {
    private int price, available, num, rate;
    private String name, type;
    private static Scanner scanner = new Scanner(System.in);

    public Item() {
        this.setAvailable(1);
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setAvailable(int available) {
        this.available = available;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getNum() {
        return num;
    }
    public int getRate() {
        return rate;
    }
    public double getAverageRate() {
        double x = rate, y = Integer.max(1, num);
        return x / y;
    }
    public void edit() {
        String inp, newinp;
        System.out.println("edit name | edit type | edit price");
        inp = scanner.nextLine();
        switch(inp) {
            case "edit name":
                System.out.println("enter name");
                newinp = scanner.nextLine();
                this.setName(newinp);
                break ;
            case "edit type":
                System.out.println("enter type");
                newinp = scanner.nextLine();
                this.setType(newinp);
                break ;
            case "edit price":
                this.setPrice(readPrice());
                break ;
            case "back":
                return ;
            default :
                System.out.println("not valid");
        }
    }
    public static int readPrice() {
        System.out.println("enter the price");
        String inp;
        while(true) {
            inp = scanner.nextLine();
            try {
                int res = Integer.parseInt(inp);
                return res;
            }
            catch(Exception e) {
                System.out.println("please enter a number");
            }
        }
    }
    public static Item readNew() {
        Item item = new Item();
        String inp;
        System.out.println("enter the Item type");
        inp = scanner.nextLine();
        item.setType(inp);
        System.out.println("enter the Item name");
        inp = scanner.nextLine();
        item.setName(inp);
        item.setPrice(readPrice());
        return item;
    }
}
