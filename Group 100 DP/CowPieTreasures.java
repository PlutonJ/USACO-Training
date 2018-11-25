/*
ID: plutonj1
LANG: JAVA
TASK: pie1
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

class pie1 {
    static String name = "pie1";
    static BufferedReader $in;
    static PrintWriter $out;
    static StringTokenizer input;
    
    public static void main(String[] args) throws IOException {
        $in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        $out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        
        read();
        int R = nextInt(), C = nextInt(), coin[][] = new int[R][C], dp[][] = new int[R][C];
        for(int i = 0; i < R; i++) {
            read();
            for(int j = 0; j < C; j++) {
                coin[i][j] = nextInt();
            }
        }
        dp[R - 1][C - 1] = coin[R - 1][C - 1];
        for(int j = C - 2; j >= 0; j--) {
            if(R - C + j <= 0) {
                dp[0][j] = coin[0][j] + max(dp[0][j + 1], dp[1][j + 1]);
            }
            for(int i = max(1, R - C + j); i < R - 1; i++) {
                dp[i][j] = coin[i][j] + max(dp[i][j + 1], max(dp[i - 1][j + 1], dp[i + 1][j + 1]));
            }
            dp[R - 1][j] = coin[R - 1][j] + max(dp[R - 1][j + 1], dp[R - 2][j + 1]);
        }
        
        println(dp[0][0]);
        close();
    }
    
    static final int IMAX = 2147483647;
    static void read() throws IOException {input = new StringTokenizer($in.readLine());}
    static String readLine() throws IOException {return $in.readLine();}
    static int nextInt() {return Integer.parseInt(input.nextToken());}
    static int readInt() throws IOException {return Integer.parseInt($in.readLine());}
    static void print(Object o) {$out.print(o);}
    static void println(Object o) {$out.println(o);}
    static void close() {$out.close();}
}