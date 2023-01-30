import java.io.*;
import java.util.*;  

public class Client implements Serializable {
    static private final int MAX_REQUEST = 6;
    static private final String DATA_PATH = "Database/client/myclient.txt";
    private int ban = 0, requestsFailed = 0, userId = -1, shopId = -1, adminAccess = 0;
    private String ip;
    private static Scanner scanner = new Scanner(System.in);
    public static String[] mainInp = new String[0];

    public static boolean isEqualString(String a, String b) {
        if(a.length() != b.length()) {
            return false;
        }
        for(int i = 0; i < a.length(); i++) {
            if(a.charAt(i) != b.charAt(i)){
                return false;
            }
        }
        return true;
    }
    public static String copyString(String a){
        String b = new String();
        for(int i = 0; i < a.length(); i++) {
            b += a.charAt(i);
        }
        return b;
    }
    public Client(int ban, int requestsFailed, int userId, int adminAccess, String ip) {
        this.ban = ban;
        this.requestsFailed = requestsFailed;
        this.userId = userId;
        this.adminAccess = adminAccess;
        this.ip = ip;
    }
    // set & get
    
    public void setBan(int ban) {
        this.ban = ban;
        updateData(this);
    }
    public void setRequestsFailed(int requestsFailed) {
        this.requestsFailed = requestsFailed;
        updateData(this);
    }
    public void setUserId(int userId) {
        this.userId = userId;
        updateData(this);
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
        updateData(this);
    }
    public void setAdminAccess(int adminAccess) {
        this.adminAccess = adminAccess;
        updateData(this);
    }
    public void setIp(String ip) {
        this.ip = ip;
        updateData(this);
    }
    public int getBan() {
        return ban;
    }
    public int getRequestsFailed() {
        return requestsFailed;
    }
    public int getUserId() {
        return userId;
    }
    public int getAdminAccess() {
        return adminAccess;
    }
    public String getIp() {
        return ip;
    }

    // Database

    public static void restoreData() {
        try {
            FileInputStream fileStream = new FileInputStream(DATA_PATH);
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            Project.myClient = (Client)objStream.readObject();
            objStream.close();
        }
        catch (Exception e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }
    public static void updateData(Client client) {
        try {
            FileOutputStream file = new FileOutputStream(DATA_PATH);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(client);
            output.close();
        }
        catch (Exception e) {
            System.out.println("catch!");
            e.getStackTrace();
        }
    }

    // ban things

    public void requestFailed() {
        this.setRequestsFailed(requestsFailed + 1);
        if(requestsFailed >= MAX_REQUEST) {
            this.banClient();
            this.showBanTime();
        }
    }
    public void requestAccepted() {
        requestsFailed = 0;
    }
    public void banClient() {
        ban = 1;
    }
    public void showBanTime() {
        System.out.println("you are ban forever!"); 
        System.exit(0);
    }

    // sign in - up

    public void customerSignin() {
        while(true) {
            String username, password;
            if(ban == 1) {
                showBanTime();
            }
            System.out.println("Enter your username!");
            username = scanner.nextLine();
            if(isEqualString(username, "back")) {
                return ;
            }
            System.out.println("Enter your password!");
            password = scanner.nextLine();
            if(isEqualString(password, "back")){
                return ;
            }
            if(Project.checkPassCustomer(this, username, password) == false) {  
                System.out.println("username or password is incorect!");
            }
            else {
                System.out.println("You sign in sucssesfuly");
                return ;
            }
        }
    }
    public void customerSignup(){
        String inp;
        while(true) {
            String username, password;
            System.out.println("Enter your username!");
            username = scanner.nextLine();
            if(isEqualString(username, "back")) {
                return ;
            }
            System.out.println("Enter your password!");
            password = scanner.nextLine();
            if(isEqualString(password, "back")){
                return ;
            }
            if(!Project.isValidUsername(username)){
                System.out.println("username is taken!");
                continue ;
            }
            if(!Project.isValidPassword(password)){
                System.out.println("password is not valid!");
                continue ;
            }
            System.out.println("Enter your address");
            inp = scanner.nextLine();
            Address address = new Address("");
            address.setAddress(inp);
            User.createUser(username, password, address);
            System.out.println("user constructed successfully");
            return ;
        }
    }

    public void sellerSignin() {
        while(true) {
            String shopname, password;
            if(ban == 1) {
                showBanTime();
            }
            System.out.println("Enter your shopname!");
            shopname = scanner.nextLine();
            if(isEqualString(shopname, "back")) {
                return ;
            }
            System.out.println("Enter your password!");
            password = scanner.nextLine();
            if(isEqualString(password, "back")){
                return ;
            }
            if(Project.checkPassSeller(this, shopname, password) == false) {  
                System.out.println("username or password is incorect!");
            }
            else {
                System.out.println("You sign in sucssesfuly");
                return ;
            }
        }
    }
    public void sellerSignup(){
        while(true) {
            String shopname, password;
            System.out.println("Enter your shop name!");
            shopname = scanner.nextLine();
            if(isEqualString(shopname, "back")) {
                return ;
            }
            System.out.println("Enter your password!");
            password = scanner.nextLine();
            if(isEqualString(password, "back")){
                return ;
            }
            if(!Project.isValidShopname(shopname)){
                System.out.println("shopname is taken!");
                continue ;
            }
            if(!Project.isValidPassword(password)){
                System.out.println("password is not valid!");
                continue ;
            }
            String type;
            System.out.println("Enter your shop type");
            type = scanner.nextLine();
            Shop.createShop(shopname, password, type);
            System.out.println("shop constructed successfully");
            return ;
        }
    }

    // sign

    public void adminSign() {
        String inp;
        while(true) {
            while(true) {
                System.out.println("Enter admins password or say -back- to go to the main page");
                inp = scanner.nextLine();
                if(isEqualString(inp, "back")) {
                    return ;
                }
                if(Project.checkPassAdmin(this, inp)) {
                    break ;
                }
                System.out.println("wrong password");
            }
            // go to admin page
            admin();
            return ;
        }
    }

    public void customerSign(){
        String inp;
        while(true) {
            if(userId != -1) {
                User.users[userId].cutsomer();
                userId = -1;
                return ;
            }
            else {
                System.out.println("sign-in or sign-up (as a customer)");
                for(inp = scanner.nextLine(); !isEqualString(inp, "sign-in") && !isEqualString(inp, "sign-up") && !isEqualString(inp, "back"); inp = scanner.nextLine()) {
                    System.out.println("not valid expected -sign-in- or -sign-up- or -back-");
                }
                if(isEqualString(inp, "back")) {
                    return ;
                }
                if(isEqualString(inp, "sign-in")){
                    customerSignin();
                }
                if(isEqualString(inp, "sign-up")){
                    customerSignup();
                }
            }
        }
    }

    public void sellerSign() {
        String inp;
        while(true) {
            if(shopId != -1) {
                Shop.shops[shopId].seller(this);
                shopId = -1;
                return ;
            }
            else {
                System.out.println("sign-in or sign-up (as a seller)");
                for(inp = scanner.nextLine(); !isEqualString(inp, "sign-in") && !isEqualString(inp, "sign-up") && !isEqualString(inp, "back"); inp = scanner.nextLine()) {
                    System.out.println("not valid expected -sign-in- or -sign-up- or -back-");
                }
                if(isEqualString(inp, "back")) {
                    return ;
                }
                if(isEqualString(inp, "sign-in")){
                    sellerSignin();
                }
                if(isEqualString(inp, "sign-up")){
                    sellerSignup();
                }
            }
        }
    }

    // admin

    public void admin() {
        System.out.println("Welcome back admin!");
        while(true) {
            String inp;
            inp = scanner.nextLine();
            if(inp.equals("log out")) {
                shopId = -1;
                return ;
            }
            System.exit(0);
        }
    }

    public void main() {
        String inp;
        while(true) {
            System.out.println("Are you admin or seller or customer");
            for(inp = scanner.nextLine(); !isEqualString(inp, "admin") && !isEqualString(inp, "seller") && !isEqualString(inp, "customer"); inp = scanner.nextLine()) {
                System.out.println("not valid expected -admin- or -seller- or -customer-");
            }
            if(isEqualString(inp, "admin")) {
                adminSign();
            }
            if(isEqualString(inp, "seller")) {
                sellerSign();
            }  
            if(isEqualString(inp, "customer")) {
                customerSign();
            }
        }
    }  
}