/*
ID: plutonj1
LANG: JAVA
TASK: clocks
*/

// brute force + pruning any branch that uses the same move more than 3 times
// also only search for different combinations as permutations are equivalent
// essentially the same as explained in the USACO analysis

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class clocks {
    static String name = "clocks";
    static BufferedReader in;
    static PrintWriter out;
    
    static int[] conv = {-1, -1, -1, 0, -1, -1, 1, -1, -1, 2, -1, -1, 3};
    static int[] next = {1, 2, 3, 0};
    static int[] prev = {3, 0, 1, 2};
    static int[][] move = {{0, 1, 3, 4}, {0, 1, 2}, {1, 2, 4, 5}, {0, 3, 6}, {1, 3, 4, 5, 7}, {2, 5, 8}, {3, 4, 6, 7}, {6, 7, 8}, {4, 5, 7, 8}};
    static int[] cnt = new int[9];
    static int[] clock = new int[9];
    // doing any move 4 times is useless, thus the length of solution is at most 27(roughly, the upper limit may be lower)
    static int[] sol = new int[27];
    static int p = 0, last = 0;
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        Arrays.fill(sol, -1);
        input = new StringTokenizer(in.readLine());
        clock[0] = conv[Integer.parseInt(input.nextToken())];
        clock[1] = conv[Integer.parseInt(input.nextToken())];
        clock[2] = conv[Integer.parseInt(input.nextToken())];
        input = new StringTokenizer(in.readLine());
        clock[3] = conv[Integer.parseInt(input.nextToken())];
        clock[4] = conv[Integer.parseInt(input.nextToken())];
        clock[5] = conv[Integer.parseInt(input.nextToken())];
        input = new StringTokenizer(in.readLine());
        clock[6] = conv[Integer.parseInt(input.nextToken())];
        clock[7] = conv[Integer.parseInt(input.nextToken())];
        clock[8] = conv[Integer.parseInt(input.nextToken())];
        dfs();
        
        out.print(sol[0] + 1);
        for(int i = 1; i < p; i++) {
            out.print(" " + (sol[i] + 1));
        }
        out.println("");
        out.close();
    }
    
    static boolean dfs() {
        if(clock[0] == 3 && clock[1] == 3 && clock[2] == 3 && clock[3] == 3 && clock[4] == 3 && clock[5] == 3 && clock[6] == 3 && clock[7] == 3 && clock[8] == 3) {
            return true;
        }
        // start dfs from last move since permutations of the same combinations of moves are equivalent
        for(int i = last; i < 9; i++) {
            if(cnt[i] < 3) {
                cnt[i]++;
                last = sol[p++] = i;
                for(int j = 0; j < move[i].length; j++) {
                    clock[move[i][j]] = next[clock[move[i][j]]];
                }
                // return on first solution found
                if(dfs()) {
                    return true;
                }
                for(int j = 0; j < move[i].length; j++) {
                    clock[move[i][j]] = prev[clock[move[i][j]]];
                }
                sol[--p] = -1;
                last = p == 0 ? 0 : sol[p - 1];
                cnt[i]--;
            }
        }
        return false;
    }
}