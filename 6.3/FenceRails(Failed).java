/*
ID: plutonj1
LANG: JAVA
TASK: fence8
*/

// dfsid attempt, times out on test case 4

import java.io.*;
import java.util.*;

class fence8 {
    static String name = "fence8";
    static BufferedReader in;
    static PrintWriter out;
    
    static int N, R;
    static int[] board, rail;

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        N = Integer.parseInt(in.readLine());
        board = new int[N];
        for(int i = 0; i < N; i++) {
            board[i] = Integer.parseInt(in.readLine());
        }
        R = Integer.parseInt(in.readLine());
        rail = new int[R];
        for(int i = 0; i < R; i++) {
            rail[i] = Integer.parseInt(in.readLine());
        }
        
        out.println(dfsid());
        out.close();
    }
    
    static int dfsid() {
        int max = 0, lastMax = 0;
        for(int limit = 1; limit <= R; limit += Math.max(Math.min(R - limit, 5), 1)) {
            int n;
            if((n = dls(new HashSet<>(), -1, board[0], 0, limit, 0)) > max) {
                max = n;
            }
            //if(max == lastMax) {
            //    break;
            //}
            lastMax = max;
        }
        return max;
    }
    
    // arguments: rails already made, last rail made from the current board, leftover length on the current board, current board, limit, # of rails made in total
    static int dls(Set<Integer> u, int i, int b, int j, int limit, int depth) {
        if(depth == limit) {
            return depth;
        }
        int max = depth;
        // if no more rails can be cut from the current board, use the next board
        boolean letsGoNext = true;
        // try rails of different lengths and prune any rail of a length that has already been searched
        boolean[] used = new boolean[128];
        if(b > 0) {
            // pruning: cutting rail[i] and rail[j] from board[k] has the same effect as cutting rail[j] and rail[i] from board[k]
            // thus start trying to make the (i + 1)st rail to avoid repeats
            for(int r = i + 1; r < R; r++) {
                if(!u.contains(r) && b >= rail[r] && !used[rail[r] - 1]) {
                    letsGoNext = false;
                    used[rail[r] - 1] = true;
                    u.add(r);
                    int n;
                    if((n = dls(u, r, b - rail[r], j, limit, depth + 1)) > max) {
                        max = n;
                    }
                    u.remove(r);
                }
            }
        }
        if(letsGoNext && j + 1 < N) {
            return dls(u, -1, board[j + 1], j + 1, limit, depth);
        } else {
            return max;
        }
    }
}