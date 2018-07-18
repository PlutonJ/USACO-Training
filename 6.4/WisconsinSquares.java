/*
ID: plutonj1
LANG: JAVA
TASK: wissqu
*/

import java.io.*;
import java.util.*;

class wissqu {
    static String name = "wissqu";
    static BufferedReader in;
    static PrintWriter out;
    
    static int[] conv = {'A', 'B', 'C', 'D', 'E', -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4};
    static char[][] mat = new char[4][4];
    // pos[i(c, i, j)] is true if conv[c] cannot be assigned to (i, j) because of neighbors
    static boolean[] pos;
    // put[i][j] is true if a new herd is already assigned to (i, j)
    static boolean[][] put = new boolean[4][4];
    // # of possible placements for each type
    static int[] posCnt;
    // # of each type left
    static int[] cnt = {3, 3, 3, 4, 3};
    // # of solutions
    static int count = 0;
    static char[] move = new char[48];
    static int p = 0;
    static String minMove = "F";
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        mat[0] = in.readLine().toCharArray();
        mat[1] = in.readLine().toCharArray();
        mat[2] = in.readLine().toCharArray();
        mat[3] = in.readLine().toCharArray();
        dfs(conv['D']);
        
        for(int i = 0; i < 48; i++) {
            switch(i % 3) {
                case 0: 
                case 1: 
                    out.print(minMove.charAt(i) + " ");
                break;
                case 2: 
                    out.println(minMove.charAt(i));
                break;
            }
        }
        out.println(count);
        out.close();
    }
    
    static void dfs(int i) {
        count();
        // create a copy since it is expensive to call count()
        boolean[] curPos = Arrays.copyOfRange(pos, 0, 80);
        char c = (char)conv[i];
        for(int j = 0; j < 4; j++) {
            for(int k = 0; k < 4; k++) {
                if(!curPos[i(i, j, k)]) {
                    char buf = mat[j][k];
                    // record move
                    move[p++] = c;
                    move[p++] = (char)(j + '1');
                    move[p++] = (char)(k + '1');
                    mat[j][k] = c;
                    put[j][k] = true;
                    cnt[i]--;
                    dfs();
                    // revert changes
                    cnt[i]++;
                    put[j][k] = false;
                    p -= 3;
                    mat[j][k] = buf;
                }
            }
        }
    }
    
    static void dfs() {
        if(p == 48) {
            count++;
            String m = new String(move);
            //System.out.println(m);
            if(m.compareTo(minMove) < 0) {
                minMove = m;
            }
        }
        count();
        boolean[] curPos = Arrays.copyOfRange(pos, 0, 80);
        // order dfs assignments from most to least constrained(sort types by (possible placements - placements left), ascending)
        Set<Integer> ord = new TreeSet<>((a, b) -> posCnt[a] - cnt[a] == posCnt[b] - cnt[b] ? a - b : posCnt[a] - cnt[a] - posCnt[b] + cnt[b]);
        for(int i = 0; i < 5; i++) {
            if(cnt[i] > 0) {
                ord.add(i);
            }
        }
        for(int i : ord) {
            char c = (char)conv[i];
            for(int j = 0; j < 4; j++) {
                for(int k = 0; k < 4; k++) {
                    if(!put[j][k] && !curPos[i(i, j, k)]) {
                        char buf = mat[j][k];
                        // record move
                        move[p++] = c;
                        move[p++] = (char)(j + '1');
                        move[p++] = (char)(k + '1');
                        mat[j][k] = c;
                        put[j][k] = true;
                        // revert changes
                        cnt[i]--;
                        dfs();
                        cnt[i]++;
                        put[j][k] = false;
                        p -= 3;
                        mat[j][k] = buf;
                    }
                }
            }
        }
    }
    
    static void count() {
        pos = new boolean[80];  // [5][4][4]
        posCnt = new int[] {16, 16, 16, 16, 16};
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                int c = conv[mat[i][j]];
                for(int k = Math.max(0, i - 1); k <= Math.min(3, i + 1); k++) {
                    for(int l = Math.max(0, j - 1); l <= Math.min(3, j + 1); l++) {
                        if(!pos[i(c, k, l)]) {
                            pos[i(c, k, l)] = true;
                            posCnt[c]--;
                        }
                    }
                }
            }
        }
    }
    
    static int i(int i, int j, int k) {
        return i * 16 + j * 4 + k;
    }
}