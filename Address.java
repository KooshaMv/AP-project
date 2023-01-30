import java.io.*;

public class Address implements Serializable {
    String address = new String();

    public Address(String address) {
        this.address = address;
    }
    public static String copyString(String a) {
        String b = new String();
        for(int i = 0; i < a.length(); i++) {
            b += a.charAt(i);
        }
        return b;
    }
    public static Address copy(Address address) {
        Address ans = new Address("");
        ans.address = copyString(address.address);
        return ans;
    }
    public void setAddress(String inp){
        address = copyString(inp);
    }
}