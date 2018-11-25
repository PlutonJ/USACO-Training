/*
ID: plutonj1
LANG: JAVA
TASK: nocows
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class nocows {
    static String name = "nocows";
    static BufferedReader in;
    static PrintWriter out;
    
    static final int MOD = 9901;
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(input.nextToken()), K = Integer.parseInt(input.nextToken()), dp[][] = new int[K + 1][N + 1], dp2[][] = new int[K + 1][N + 1];
        dp[0][0] = dp2[0][0] = dp[1][1] = dp2[1][1] = 1;
        for(int i = 2; i <= K; i++) {
            for(int j = 1; j <= N; j++){
                dp2[i][j] += dp2[i - 1][j];
                for(int k = 1; k < j - 1; k++) {
                    dp[i][j] += dp[i - 1][k] * dp2[i - 1][j - k - 1] * 2;
                    if(dp[i][j] > 0 && k == j - k - 1) {
                        dp[i][j] -= dp[i - 1][k];
                    }
                    dp[i][j] %= MOD;
                }
                dp2[i][j] = (dp2[i][j] + dp[i][j]) % MOD;
            }
        }
        for(int i = 0; i <= K; i++){
            for(int j = 0; j <= N; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        for(int i = 0; i <= K; i++){
            for(int j = 0; j <= N; j++) {
                System.out.print(dp2[i][j] + " ");
            }
            System.out.println("");
        }
        
        out.println(dp[K][N]);
        out.close();
    }
}