/*
ID: plutonj1
LANG: JAVA
TASK: holstein
*/

// ~15 mins 9/21/2018
// dfs every combination: max = \sum_{n=1}^{15}\binom{15}{n} = 2 ** 15 - 1 = 32767

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class holstein {
    static String name = "holstein";
    static BufferedReader in;
    static PrintWriter out;
    
    static int V, G, p, v[], f[][], cur[], res[];
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        V = Integer.parseInt(in.readLine());
        v = new int[V];
        input = new StringTokenizer(in.readLine());
        for(int i = 0; i < V; i++) {
            v[i] = Integer.parseInt(input.nextToken());
        }
        G = Integer.parseInt(in.readLine());
        f = new int[G][V];
        for(int i = 0; i < G; i++) {
            input = new StringTokenizer(in.readLine());
            for(int j = 0; j < V; j++) {
                f[i][j] = Integer.parseInt(input.nextToken());
            }
        }
        cur = new int[V];
        res = new int[G];
        for(int i = 0; i < G; i++) {
            p = 0;
            if(solve(i + 1, 0)) {
                break;
            }
        }
        
        out.print(p);
        for(int i = 0; i < p; i++) {
            out.print(" " + (res[i] + 1));
        }
        out.println("");
        out.close();
    }
    
    static boolean solve(int n, int x) {
        if(x == n) {
            for(int i = 0; i < V; i++) {
                if(cur[i] < v[i]) {
                    return false;
                }
            }
            return true;
        } else {
            for(int i = p == 0 ? 0 : res[p - 1] + 1; i <= G - (n - x); i++) {
                res[p++] = i;
                for(int j = 0; j < V; j++) {
                    cur[j] += f[i][j];
                }
                if(solve(n, x + 1)) {
                    return true;
                }
                for(int j = 0; j < V; j++) {
                    cur[j] -= f[i][j];
                }
                p--;
            }
            return false;
        }
    }
}