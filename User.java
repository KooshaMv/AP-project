import java.io.*;
import java.util.*;  

public class User implements Serializable {
    final static int MAX_USER = 10;
    private int id, wallet;
    private String name, password;
    private Address address;
    static User users[] = new User[MAX_USER];
    //private static Scanner scanner = new Scanner(System.in);
    static Vector<Integer> emptyUserId = new Vector<Integer> (0);
    static Map<String, Integer> nameId = new HashMap<String, Integer> () {{}};
    
    public User(int id, int wallet, String name, String password, Address address) {
        this.id = id;
        this.wallet = wallet;
        this.name = name;
        this.password = password;
        this.address = address;
    }
    //
    public static int getNewUserId() {
        int ans = emptyUserId.get(emptyUserId.size() - 1);
        emptyUserId.remove(emptyUserId.size() - 1);
        return ans;
    }
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
    public void setName(String name) { 
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAddress(Address address) {
       this.address = address;
    }
    public String getName() { 
        return name;
    }
    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public Address getAddress() {
        return address;
    }
    public static void createUser(String name, String password, Address address) {
        int id = getNewUserId();
        users[id] = new User(id, 0, name, password, address);
        nameId.put(name, id);
        updateUserData(id);
    }

    // inits
    
    public static void init() {
        for(int i = 0; i < MAX_USER; i++) {
            if(users[i].getId() == -1) {
                emptyUserId.add(i);
            }
            else {
                nameId.put(users[i].getName(), users[i].getId());
            }
        }
    }
    public static void initNew() {
        for(int i = 0; i < MAX_USER; i++) {
            users[i] = new User(-1, -1, "-1", "-1", new Address(""));
            updateUserData(i);
        }
    }
    public static void initRestore() {
        for(int i = 0; i < MAX_USER; i++) {
            getUserData(i);
        }
    }

    // Data base
    
    public static void updateUserData(int id) {
        try {
            String s = "Database/users/" + id + ".txt";
            FileOutputStream file = new FileOutputStream(s);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(users[id]);
            // Reads data using the ObjectInputStream
            output.close();
        }
        catch (Exception e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }
    public static void getUserData(int id) {
        try {
            String s = "Database/users/" + id + ".txt";
            FileInputStream fileStream = new FileInputStream(s);
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            users[id] = (User)objStream.readObject();
            objStream.close();
        }
        catch (Exception e) {
            System.out.println(e);
            e.getStackTrace();
        }
    }

    // customer

    public void cutsomer() {

    }
    public static void main(String []args) {
    }
}