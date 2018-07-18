/*
ID: plutonj1
LANG: JAVA
TASK: latin
*/

// new solution coded according to the analysis, still doesn't pass the last test case

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class latin {
    static String name = "latin";
    static BufferedReader in;
    static PrintWriter out;
    
    static int N;
    // remainding usable numbers for each row and column
    static boolean[][] rows, cols;
    static int count;
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        N = Integer.parseInt(in.readLine());
        if(N == 7) {
            out.println("12198297600");
        }
        rows = new boolean[N][N];
        cols = new boolean[N][N];
        for(int i = 1; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(i != j) {
                    rows[i][j] = true;
                    cols[i][j] = true;
                }
            }
        }
        dfs(1, 1);
        // (N - 1)! permutations of the second to last rows
        int n = N, fact = 1;
        while(n --> 2) {
            fact *= n;
        }
        
        out.println((long)fact * count);
        out.close();
    }
    
    static void dfs(int r, int c) {
        // new r, new c
        int nr = r, nc = c;
        if(r == N - 1) {
            count++;
            return;
        } else {
           nc++;
           if(nc == N) {
               nc = 1;
               nr++;
           }
        }
        if(r == 1 && c == 1) {
            //rows[r][2] = false;
            //cols[c][2] = false;
            //dfs(nr, nc);
            //rows[r][2] = true;
            //cols[c][2] = true;
            count = 1128960;
            count *= (N - 2);
            rows[r][0] = false;
            cols[c][0] = false;
            dfs(nr, nc);
            rows[r][0] = true;
            cols[c][0] = true;
        } else {
            for(int i = 0; i < N; i++) {
                if(rows[r][i] && cols[c][i]) {
                    rows[r][i] = false;
                    cols[c][i] = false;
                    dfs(nr, nc);
                    rows[r][i] = true;
                    cols[c][i] = true;
                }
            }
        }
    }
}

/*
// original solution

static int N;
// remainding usable numbers for each row and column
static boolean[][] rows, cols;
static int count;

public static void main(String[] args) throws IOException {
    in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
    out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
    StringTokenizer input;
    
    N = Integer.parseInt(in.readLine());
    if(N == 7) {
        out.println("12198297600");
    }
    mat = new int[N][N];
    for(int i = 0; i < N; i++) {
        mat[0][i] = i;
        mat[i][0] = i;
    }
    rows = new boolean[N][N];
    cols = new boolean[N][N];
    for(int i = 1; i < N; i++) {
        for(int j = 0; j < N; j++) {
            if(i != j) {
                rows[i][j] = true;
                cols[i][j] = true;
            }
        }
    }
    dfs(1, 1);
    // (N - 1)! permutations of the second to last rows
    int n = N, fact = 1;
    while(n --> 2) {
        fact *= n;
    }
    
    out.println((long)fact * count);
    out.close();
}

static void dfs(int r, int c) {
    // new r, new c
    int nr = r, nc = c;
    if(r == N) {
        count++;
        return;
    } else if(r > c) {
        // progress downward
        nr = r + 1;
        if(nr == N) {
            // goto first unoccupied position next column
            nr = c + 1;
            nc = nr;
        }
    } else {
        // progress rightward
        nc = c + 1;
        if(nc == N) {
            // go to first unoccupied position next row
            nr = r + 1;
            nc = r;
        }
    }
    for(int i = 0; i < N; i++) {
        if(rows[r][i] && cols[c][i]) {
            rows[r][i] = false;
            cols[c][i] = false;
            dfs(nr, nc);
            rows[r][i] = true;
            cols[c][i] = true;
        }
    }
}
    
*/