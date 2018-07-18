/*
ID: plutonj1
LANG: JAVA
TASK: checker
*/

// simple brute force passed every test case??? what

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class checker {
    static String name = "checker";
    static BufferedReader in;
    static PrintWriter out;
    
    static int N, r = 0, cnt = 0;
    static int[] sol;
    // u[i] = column i is occupied, nwse[i] = northwest to southeast diagonal i is occupied, nesw[i] = noetheast to southwest diagonal i is occupied
    static boolean[] u, nwse, nesw;
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        N = Integer.parseInt(in.readLine());
        sol = new int[N];
        u = new boolean[N];
        nwse = new boolean[N + N - 1];
        nesw = new boolean[N + N - 1];
        dfs();
        
        out.println(cnt);
        out.close();
    }
    
    static void dfs() {
        if(r == N) {
            if(cnt++ < 3) {
                out.print(sol[0] + 1);
                for(int i = 1; i < N; i++) {
                    out.print(" " + (sol[i] + 1));
                }
                out.println("");
            }
            return;
        }
        for(int c = 0; c < N; c++) {
            if(!u[c] && !nesw[N - r - 1 + c] && !nwse[r + c]) {
                u[c] = true;
                nesw[N - r - 1 + c] = true;
                nwse[r + c] = true;
                sol[r++] = c;
                dfs();
                r--;
                nwse[r + c] = false;
                nesw[N - r - 1 + c] = false;
                u[c] = false;
            }
        }
    }
}