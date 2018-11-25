/*
ID: plutonj1
LANG: JAVA
TASK: cownum
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

class cownum {
    static String name = "cownum";
    static BufferedReader $in;
    static PrintWriter $out;
    static StringTokenizer input;
    
    public static void main(String[] args) throws IOException {
        $in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        $out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        
        read();
        int N = nextInt();
        long M = (long)nextInt();
        // filled to, [i]
        long dp[][] = new long[N + 3][N + 1], sum = 0;
        dp[N][N] = dp[N + 1][N] = dp[N + 2][N] = 1;
        for(int i = 0; i < N; i++) {
            dp[N - 1][i] = dp[N - 2][i] = 1;
        }
        for(int i = N - 3; i >= 0; i--) {
            long[][] mem = new long[N + 1][N + 1];
            boolean[][] flag = new boolean[N + 1][N + 1];
            for(int j = 0; j < N; j++) {
                for(int x = j + 1; x <= N; x++) {
                    for(int y = j + 1; y <= N; y++) {
                        if(!flag[x][y]) {
                            mem[x][y] = dp[i + 2][x] * dp[i + 3][y] % M;
                            flag[x][y] = true;
                        }
                        dp[i][j] = (dp[i][j] + mem[x][y]) % M;
                    }
                }
                dp[i][j] = dp[i][j] * N % M;
            }
        }
        
        for(long[] i : dp) {
            for(long j : i) {
                print(j + " ");
            }
            println("");
        }
        for(long i : dp[0]) {
            sum = (sum + i) % M;
        }
        println(sum);
        
        cnt(N, N - 1);
        println(cnt);
        close();
    }
    
    static int a[], cnt = 0;
    
    static void cnt(int x, int ind) {
        if(ind == x - 1) {
            a = new int[x + 3];
            a[x] = a[x + 1] = a[x + 2] = x;
        } else if(ind == -1) {
            cnt++;
            for(int i = 0; i < x; i++) {
                print(a[i] + 1 + " ");
            }
            println("");
            return;
        }
        for(int i = 0; i < min(a[ind + 2], a[ind + 3]); i++) {
            a[ind] = i;
            cnt(x, ind - 1);
        }
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