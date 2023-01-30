//import java.lang.*;
import java.util.*;  

public class Project {
    static private String adminPass = "why me?";
    static Scanner scanner = new Scanner(System.in);
    static Client myClient = new Client(0, 0, -1, 0, "1.1.1.1");
    //static private Map<String, Vector<Integer>> clientCustomerRequests = new HashMap<String, Vector<Integer>> () {{}};
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
    public static void init() {
        DatabaseThread databasethread = new DatabaseThread();
        databasethread.start();
        User.init();
        Shop.init();
    }
    public static void newProject() {
        User.initNew();
        Shop.initNew();
    }
    public static void restoreProject(){
        User.initRestore();
        Shop.initRestore();
        Client.restoreData();
    }
    
    // admins methods
    
    public static boolean checkPassAdmin(Client client,String pass) {
        if(client.getBan() == 1) {
            client.showBanTime();
            return false;
        }
        if(isEqualString(pass, adminPass)) {
            client.requestAccepted();
            client.setAdminAccess(1);
            return true;
        }
        client.requestFailed();
        return false;
    } 

    // custormer methods
    
    public static boolean isValidUsername(String username) {
        if(User.nameId.containsKey(username)) {
            return false;
        }
        return true;
    }
    public static boolean checkPassCustomer(Client client, String username, String password) {
        if(client.getBan() == 1) {
            client.showBanTime();
            return false;
        }
        if(User.nameId.containsKey(username) == false) {
            client.requestFailed();
            return false;
        }
        int userId = User.nameId.get(username);
        if(isEqualString(User.users[userId].getPassword(), password) == false) {
            client.requestFailed();
            return false;
        }
        client.setUserId(userId);
        client.requestAccepted();
        return true;
    }
    

    // seller methods

    public static boolean isValidShopname(String shopname) {
        if(Shop.nameId.containsKey(shopname)) {
            return false;
        }
        return true;
    }
    public static boolean checkPassSeller(Client client, String shopname, String password) {
        if(client.getBan() == 1) {
            client.showBanTime();
            return false;
        }
        if(Shop.nameId.containsKey(shopname) == false) {
            client.requestFailed();
            System.out.println("no such restaurant!");
            return false;
        }
        int shopId = Shop.nameId.get(shopname);
        if(isEqualString(Shop.shops[shopId].getPassword(), password) == false) {
            client.requestFailed();
            System.out.println("corect password is " + Shop.shops[shopId].getPassword());
            return false;
        }
        client.setShopId(shopId);
        client.requestAccepted();
        return true;
    }

    public static boolean isValidPassword(String password) {
        return true;
    }
    // main
    public static void main(String[] args) {
        String inp;
        System.out.println("new program or continue");
        for(inp = scanner.nextLine(); !isEqualString(inp, "new") && !isEqualString(inp, "continue"); inp = scanner.nextLine()) {
            System.out.println("not valid expected -new- or -continue-");
        }
        if(isEqualString(inp, "new")) {
            newProject();
        }
        if(isEqualString(inp, "continue")) {
            restoreProject();
        }
        init();
        myClient.main();
    }
}