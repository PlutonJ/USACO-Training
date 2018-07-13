/*
ID: plutonj1
LANG: JAVA
TASK: fence8
*/

// https://blog.csdn.net/damonhao/article/details/20290891
// crucial observations: 
//      to cut as many rails from the boards, start with the smallest rails, thus sort the rails by length, ascending
//      1. if the first k rails cannot be cut from the boards, then changing one of the rails P to another rail Q from the (k + 1)st to the Rth rail would not work either
//      2. if the first k rails can be cut from the boards, then the first k - 1 rails can be cut from the boards
//      3. if the first k rails cannot be cut from the boards, then the first k + 1 rails cannot be cut from the boards
//      thus enumerate k and find the maximum possible value(binary search)
// pruning: 
//      1. if it is possible to cut the first k rails, then the total length of the remains waste == boardSum - railSum[k]
//         when no other rails can be cut from a board, add the remains to currentWaste, and if currentWaste > waste, there is no solution for this branch
//      2. if there exists two rails r1 and r2 of which the lengths are equal, determining where r1 could be infers that r2 can only be after r1
//         (if the first possible board r1 can be cut from is i, then the first possible board r2 can be cut from after cutting r1 must >= i)
//      3. if railSum[k] > boardSum, these k rails cannot be cut from the boards
//      4. dfs from the longest rails to the shortest ones since it is harder to cut longer ones

import java.io.*;
import java.util.*;

class fence8 {
    static String name = "fence8";
    static BufferedReader in;
    static PrintWriter out;
    
    static int N, R, boardSum = 0, waste, currentWaste;
    static int[] board, rail, railSum, cut;

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
        rail = new int[R + 1];
        railSum = new int[R];
        // cut[i] = index of the board rail i is cut from
        cut = new int[R];
        for(int i = 0; i < R; i++) {
            rail[i] = Integer.parseInt(in.readLine());
        }
        Arrays.sort(rail, 0, R);
        railSum[0] = rail[0];
        for(int i = 1; i < R; i++) {
            railSum[i] = railSum[i - 1] + rail[i];
        }
        // sort board in descending order
        Arrays.sort(board);
        for(int i = 0; i < N; i++) {
            int swap = board[i];
            board[i] = board[N - i - 1];
            board[N - i - 1] = swap;
        }
        // discard boards that are shorter than the shortest rail
        while(N > 0 && board[N - 1] < rail[0]) {
            N--;
        }
        if(N == 0 || R == 0) {
            out.println(0);
            out.close();
            return;
        }
        for(int i = 0; i < N; i++) {
            boardSum += board[i];
        }
        // binary search
        int l = 0, r = R - 1;
        while(railSum[r] > boardSum) {
            r--;
        }
        while(l < r) {
            int m = (l + r + 1) / 2;
            Arrays.fill(cut, -1);
            waste = boardSum - railSum[m];
            if(dfs(m)) {
                l = m;
            } else {
                r = m - 1;
            }
        }
        
        out.println(l + 1);
        out.close();
    }
    
    static boolean dfs(int ind) {
        if(currentWaste > waste) {
            return false;
        }
        if(ind < 0) {
            return true;
        }
        // check if this rail has the same length as last rail, also the reason rail[] has length R + 1(avoid boundary checks)
        for(int i = rail[ind] == rail[ind + 1] ? Math.max(0, cut[ind + 1]) : 0; i < N; i++) {
            if(board[i] >= rail[ind]) {
                cut[ind] = i;
                board[i] -= rail[ind];
                if(board[i] < rail[0]) {
                    currentWaste += board[i];
                }
                boolean solution = dfs(ind - 1);
                if(board[i] < rail[0]) {
                    currentWaste -= board[i];
                }
                board[i] += rail[ind];
                cut[ind] = -1;
                if(solution) {
                    return true;
                }
            }
        }
        return false;
    }
}