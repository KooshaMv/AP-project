public class DatabaseThread extends Thread {
    public void run() {
        while(true) {
            Client.updateData(Project.myClient);
            for(int i = 0; i < User.MAX_USER; i++) {
                User.updateUserData(i);
            }
            for(int i = 0; i < Shop.MAX_SHOP; i++) {
                Shop.updateShopData(i);
            }
            try {
                sleep(1000);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
    }
  }