import java.io.*;
import java.util.*;  

public class Shop implements Serializable {
    final static int MAX_ITEM = 10, MAX_SHOP = 10;
    private int id = -1;
    private String name, type, password;
    static Shop shops[] = new Shop[MAX_SHOP];
    private static Scanner scanner = new Scanner(System.in);
    static Vector<Integer> emptyShopId = new Vector<Integer> (0);
    static Map<String, Integer> nameId = new HashMap<String, Integer> () {{}};
    
    Vector<Item> items = new Vector<Item> ();
    Vector<Order> orders = new Vector<Order> ();

    public Shop(int id, String name, String password, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }
    
    public void setShopId(int id) {
        this.id = id;
    }
    public void setShopType(String type) {
        this.type = type;
    }
    public void setShopName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getPassword() {
        return password;
    }
    public static int getNewShopId() {
        int ans = emptyShopId.get(emptyShopId.size() - 1);
        emptyShopId.remove(emptyShopId.size() - 1);
        return ans;
    }
    public static void createShop(String name, String password, String type) {
        int id = getNewShopId();
        shops[id] = new Shop(id, name, password, type);
        nameId.put(name, id);
        updateShopData(id);
    }
    
    // inits

    public static void init() {
        for(int i = 0; i < MAX_SHOP; i++) {
            if(shops[i].getId() == -1) {
                emptyShopId.add(i);
            }
            else {
                nameId.put(shops[i].getName(), shops[i].getId());
            }
        }
    }
    public static void initNew() {
        for(int i = 0; i < MAX_SHOP; i++) {
            shops[i] = new Shop(-1, "", "", "");
            shops[i].setShopId(-1);
            updateShopData(i);
        }
    }
    public static void initRestore() {
        for(int i = 0; i < MAX_SHOP; i++) {
            getShopData(i);
        }
    }

    // data base

    public static void updateShopData(int id) {
        try {
            String s = "Database/shops/" + id + ".txt";
            FileOutputStream file = new FileOutputStream(s);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(shops[id]);
            output.close();
        }
        catch (Exception e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }
    public static void getShopData(int id) {
        try {
            String s = "Database/shops/" + id + ".txt";
            FileInputStream fileStream = new FileInputStream(s);
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            shops[id] = (Shop)objStream.readObject();
            objStream.close();
        }
        catch (Exception e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }

    //

    public void printItems() {
        Vector<Integer> mark = new Vector<Integer> ();
        for(int i = 0; i < items.size(); i++) {
            mark.add(0);
        }
        System.out.println("Items : " + items.size());
        System.out.println("name | price | rate");
        for(int i = 0; i < mark.size(); i++) {
            if(mark.get(i) == 1) {
                continue ;
            }
            System.out.println(items.get(i).getType() + " :\n");
            for(int j = 0; j < items.size(); j++) {
                if(items.get(j).getType().equals(items.get(i).getType())) {
                    mark.set(j, 1);
                    System.out.println(items.get(j).getName() + " | " + items.get(j).getPrice() + " | " + items.get(j).getAverageRate());
                }
            }
            System.out.println();
        }
    }
    public void editItem() {
        String inp;
        while(true) {
            printItems();
            System.out.println("enter name of the item you want to edit");
            inp = scanner.nextLine();
            if(inp.equals("back")) {
                return ;
            }
            int validName = 0;
            for(Item item : items) {
                if(item.getName().equals(inp)) {
                    validName = 1;
                    item.edit();
                }
            }
            if(validName == 0) {
                System.out.println("not valid Name");
            }
        }
    }
    public void deleteItem() {
        String inp;
        while(true) {
            printItems();
            System.out.println("enter name of the item you want to delete");
            inp = scanner.nextLine();
            if(inp.equals("back")) {
                return ;
            }
            int validName = 0;
            for(int i =0; i < items.size(); i++) {
                if(items.get(i).getName().equals(inp)) {
                    validName = 1;
                    items.remove(items.get(i));
                    break ;
                }
            }
            if(validName == 0) {
                System.out.println("not valid Name");
            }
        }
    }
    public void items() {
        String inp;
        while(true) {
            System.out.println("new item | edit item | delete item");
            inp = scanner.nextLine();
            switch(inp) {
                case "new item":
                    items.add(Item.readNew());
                    System.out.println("item aded succsesfuly");
                    break ;
                case "edit item":
                    editItem();
                    break ;
                case "delete item": 
                    deleteItem();
                    break ;
                case "back":
                    return ;
                default :
                    System.out.println("not valid");
            }
        }
    }
    public void orders() {
        String inp;
        while(true) {
            System.out.println("orders : customername | price | state | address");
            for(Order order : orders) {
                System.out.println(order.getUser().getName() + " | " + order.getPrice() + " | " + order.getState() + " | " + order.getUser().getAddress());
            }
            System.out.println("eneter the name of the customer");
            inp = scanner.nextLine();
            if(inp.equals("back")) {
                return ;
            }
            int isValid = 0;
            for(Order order : orders) {
                if(order.getUser().getName() == inp) {
                    isValid = 1;
                    order.edit();
                }
            }
            if(isValid == 0) {
                System.out.println("not valid order");
            }
        }
    }
    public void setting() {
        while(true) {
            String inp, newinp;
            System.out.println("edit name | edit password | edit type");
            inp = scanner.nextLine();
            switch(inp) {
                case "edit name":
                    System.out.println("enter a name");
                    newinp = scanner.nextLine();
                    this.setShopName(newinp);
                    break ;
                case "edit password":
                    System.out.println("enter a password");
                    newinp = scanner.nextLine();
                    this.setPassword(newinp);
                    break ;
                case "edit type":
                    System.out.println("enter a type");
                    newinp = scanner.nextLine();
                    this.setShopType(newinp);
                    break ;
                case "back":
                    return ;
                default :
                    System.out.println("not valid");
            }
        }
    }
    // seller

    public void seller(Client client) {
        System.out.println("welcome " + this.getName() + this.getId() + "!");
        while(true) {
            String inp;
            System.out.println("logout | items | orders | shop setting");
            inp = scanner.nextLine();
            switch(inp) {
                case "logout":
                    return ;
                case "items":
                    items();
                    break ;
                case "orders":
                    orders();
                    break ;
                case "shop setting":
                    setting();
                    break;
                default :
                    System.out.println("not valid");
            }
        }
    }
}