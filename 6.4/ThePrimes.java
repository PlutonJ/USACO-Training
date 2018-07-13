/*
ID: plutonj1
LANG: JAVA
TASK: prime3
*/

// rearranged dfs assignments

import java.io.*;
import java.util.*;

class prime3 {
    static String name = "prime3";
    static BufferedReader in;
    static PrintWriter out;
    
    static boolean[] p = new boolean[100000];
    static int[] r = {2, 1, 3, 4, 3, 4, 1, 0, 1, 2, 3, 1, 2, 3, 2, 2, 1, 3, 0, 0, 0, 4, 4, 4};
    static int[] c = {2, 1, 3, 4, 1, 0, 3, 4, 0, 0, 0, 4, 4, 4, 1, 3, 2, 2, 1, 2, 3, 1, 2, 3};
    static int[][] mat = new int[5][5];
    static Set<int[]> sols = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] == b[1] ? a[2] == b[2] ? a[3] == b[3] ? a[4] - b[4] : a[3] - b[3] : a[2] - b[2] : a[1] - b[1] : a[0] - b[0]);

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int sum = Integer.parseInt(input.nextToken()), head = Integer.parseInt(input.nextToken());
        mat[0][0] = head;
        Arrays.fill(p, true);
        for(int i = 2; i <= 316; i++) {
            if(p[i]) {
                for(int j = i * 2; j < 100000; j++) {
                    if(j % i == 0) {
                        p[j] = false;
                    }
                }
            }
        }
        for(int i = 2; i < 100000; i++) {
            if(p[i]) {
                if(i < 10000) {
                    p[i] = false;
                } else {
                    int x = i, s = 0;
                    s += x % 10;
                    x /= 10;
                    s += x % 10;
                    x /= 10;
                    s += x % 10;
                    x /= 10;
                    s += x % 10;
                    x /= 10;
                    s += x;
                    if(s != sum) {
                        p[i] = false;
                    }
                }
            }
        }
        
        dfs(0);
        if(sols.size() == 0) {
            out.println("NONE");
        } else {
            Iterator<int[]> iter = sols.iterator();
            int[] sol = iter.next();
            for(int i = 0; i < 5; i++) {
                out.println(sol[i]);
            }
            while(iter.hasNext()) {
                sol = iter.next();
                out.println("");
                for(int i = 0; i < 5; i++) {
                    out.println(sol[i]);
                }
            }
        }
        out.close();
    }
    
    static void dfs(int ind) {
        int i = r[ind], j = c[ind];
        switch(ind) {
            case 0: 
            case 1: 
            case 2:
                for(int n = 0; n < 10; n++) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 3: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    if(p(mat[0][0], mat[1][1], mat[2][2], mat[3][3], mat[4][4])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 4: 
                for(int n = 0; n < 10; n++) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 5: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 6: 
                for(int n = 0; n < 10; n++) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 7: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    if(p(mat[4][0], mat[3][1], mat[2][2], mat[1][3], mat[0][4])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 8: 
            case 9: 
                for(int n = 1; n < 10; n++) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 10: 
                for(int n = 1; n < 10; n++) {
                    mat[i][j] = n;
                    if(p(mat[0][0], mat[1][0], mat[2][0], mat[3][0], mat[4][0])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 11: 
            case 12: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 13: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    if(p(mat[0][4], mat[1][4], mat[2][4], mat[3][4], mat[4][4])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 14: 
                for(int n = 0; n < 10; n++) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 15: 
                for(int n = 0; n < 10; n++) {
                    mat[i][j] = n;
                    if(p(mat[2][0], mat[2][1], mat[2][2], mat[2][3], mat[2][4])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 16: 
                for(int n = 0; n < 10; n++) {
                    mat[i][j] = n;
                    if(p(mat[1][0], mat[1][1], mat[1][2], mat[1][3], mat[1][4])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 17: 
                for(int n = 0; n < 10; n++) {
                    mat[i][j] = n;
                    if(p(mat[3][0], mat[3][1], mat[3][2], mat[3][3], mat[3][4])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 18: 
            case 19:
                for(int n = 1; n < 10; n++) {
                    mat[i][j] = n;
                    dfs(ind + 1);
                }
            break;
            case 20: 
                for(int n = 1; n < 10; n++) {
                    mat[i][j] = n;
                    if(p(mat[0][0], mat[0][1], mat[0][2], mat[0][3], mat[0][4])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 21: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    if(p(mat[0][1], mat[1][1], mat[2][1], mat[3][1], mat[4][1])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 22: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    if(p(mat[0][2], mat[1][2], mat[2][2], mat[3][2], mat[4][2])) {
                        dfs(ind + 1);
                    }
                }
            break;
            case 23: 
                for(int n = 1; n < 10; n += 2) {
                    mat[i][j] = n;
                    if(p(mat[0][3], mat[1][3], mat[2][3], mat[3][3], mat[4][3]) && p(mat[4][0], mat[4][1], mat[4][2], mat[4][3], mat[4][4])) {
                        sols.add(new int[] {n(mat[0][0], mat[0][1], mat[0][2], mat[0][3], mat[0][4]), n(mat[1][0], mat[1][1], mat[1][2], mat[1][3], mat[1][4]), n(mat[2][0], mat[2][1], mat[2][2], mat[2][3], mat[2][4]), n(mat[3][0], mat[3][1], mat[3][2], mat[3][3], mat[3][4]), n(mat[4][0], mat[4][1], mat[4][2], mat[4][3], mat[4][4])});
                    }
                }
            break;
        }
    }
    
    static int n(int i, int j, int k, int l, int m) {
        return i * 10000 + j * 1000 + k * 100 + l * 10 + m;
    }
    
    static boolean p(int i, int j, int k, int l, int m) {
        return p[n(i, j, k, l, m)];
    }
}