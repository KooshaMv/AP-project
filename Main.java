import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String args[]) {
        int[] m, n;
        int S, t;
        S = scanner.nextInt();
        t = scanner.nextInt();
        m = new int[t + 10];
        n = new int[t + 10];
        for(int i = 1; i <= t; i++) {
            m[i] = scanner.nextInt();
            n[i] = scanner.nextInt();
        }
        System.out.println(javab(S, t, n, m));
    }
    static int javab(int S, int j, int[] n, int[] m) {
        if(S == 0) {
            return 1;
        }
        if(j == 0) {
            return 0;
        }
        int ans = 0, alan = S;
        for(int i = 0; i <= n[j]; i++) {
            if(alan < 0) break ;
            ans += javab(alan, j - 1, n, m);
            alan -= m[j];
        }
        return ans;
    }
}
