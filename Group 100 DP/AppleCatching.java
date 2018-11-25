/*
ID: plutonj1
LANG: JAVA
TASK: bcatch
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

class bcatch {
    static String name = "bcatch";
    static BufferedReader $in;
    static PrintWriter $out;
    static StringTokenizer input;
    
    public static void main(String[] args) throws IOException {
        $in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        $out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        
        read();
        int T = nextInt(), W = nextInt(), seq[] = new int[T], dp[][] = new int[T][W + 1];
        for(int i = 0; i < T; i++) {
            seq[i] = readInt();
        }
        dp[0][0] = seq[0] == 1 ? 1 : 0;
        dp[0][1] = seq[0] == 2 ? 1 : 0;
        for(int i = 1; i < T; i++) {
            int tree[] = new int[] {seq[i] == 1 ? 1 : 0, seq[i] == 2 ? 1 : 0};
            dp[i][0] = dp[i - 1][0] + tree[0];
            for(int j = 1; j <= min(W, i + 1); j++) {
                dp[i][j] = max(dp[i - 1][j] + tree[j % 2], dp[i - 1][j - 1] + tree[j % 2]);
            }
        }
        
        int max = 0;
        for(int i = 0; i <= W; i++) {
            max = max(max, dp[T - 1][i]);
        }
        println(max);
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