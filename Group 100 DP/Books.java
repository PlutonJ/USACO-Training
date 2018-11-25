/*
ID: plutonj1
LANG: JAVA
TASK: books
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

class books {
    static String name = "books";
    static BufferedReader $in;
    static PrintWriter $out;
    static StringTokenizer input;
    
    public static void main(String[] args) throws IOException {
        $in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        $out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        
        read();
        int N = nextInt(), K = nextInt(), s[] = new int[N], d[] = new int[N];
        // current book, which book for current employee, current employee
        long[][][] dp = new long[N][N][K];
        boolean[][][] visited = new boolean[N][N][K];
        for(long[][] i : dp) {
            for(long[] j : i) {
                fill(j, (long)2e16 + 1);
            }
        }
        read();
        for(int i = 0; i < N; i++) {
            s[i] = nextInt();
        }
        read();
        for(int i = 0; i < N; i++) {
            d[i] = nextInt();
        }
        dp[0][0][0] = (long)d[0] * s[0];
        visited[0][0][0] = true;
        for(int i = 1; i < N; i++) {
            dp[i][i][0] = dp[i - 1][i - 1][0] + (long)s[i] * d[i];
            visited[i][i][0] = true;
            for(int k = 1; k < K; k++) {
                for(int lastJ = 0; lastJ < N; lastJ++) {
                    if(visited[i - 1][lastJ][k - 1]) {
                        dp[i][0][k] = min(dp[i][0][k], dp[i - 1][lastJ][k - 1] + (long)s[i] * d[0]);
                    }
                }
                if(dp[i][0][k] <= (long)2e16) {
                    visited[i][0][k] = true;
                }
                for(int j = 1; j < N; j++) {
                    if(visited[i - 1][j - 1][k]) {
                        dp[i][j][k] = dp[i - 1][j - 1][k] + (long)s[i] * d[j];
                        visited[i][j][k] = true;
                    }
                }
            }
        }
        
        long min = LMAX;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < K; j++) {
                min = min(min, dp[N - 1][i][j]);
            }
        }
        println(min);
        close();
    }
    
    static final int IMAX = 2147483647;
    static final long LMAX = 9223372036854775807L;
    static void read() throws IOException {input = new StringTokenizer($in.readLine());}
    static String readLine() throws IOException {return $in.readLine();}
    static int nextInt() {return Integer.parseInt(input.nextToken());}
    static int readInt() throws IOException {return Integer.parseInt($in.readLine());}
    static void print(Object o) {$out.print(o);}
    static void println(Object o) {$out.println(o);}
    static void close() {$out.close();}
}