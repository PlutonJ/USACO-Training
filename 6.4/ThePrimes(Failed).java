/*
ID: plutonj1
LANG: JAVA
TASK: prime3
*/

// dfs with pruning using a trie, times out on test case 3

import java.io.*;
import java.util.*;

class prime3 {
    static String name = "prime3";
    static BufferedReader in;
    static PrintWriter out;
    
    static List<Integer> p = new ArrayList<>((99991 - 10007) / 2 + 1);
    static Trie primes;
    static int sum, f;
    static int[][] mat = new int[5][5];
    static boolean first = true;

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        sum = Integer.parseInt(input.nextToken());
        f = Integer.parseInt(input.nextToken());
        mat[0][0] = f;
        // generate all 5 digit primes that have digits sum up to the given sum and add them to a trie
        for(int i = 10007; i <= 99991; i += 2) {
            p.add(i);
        }
        int sqrt = (int)Math.sqrt(99991);
        for(int i = 3; i <= sqrt; i++) {
            for(int j = p.size() - 1; j >= 0; j--) {
                if(p.get(j) % i == 0) {
                    p.remove(j);
                }
            }
        }
        for(int i = p.size() - 1; i >= 0; i--) {
            int prime = p.get(i), s = 0;
            s += prime % 10;
            prime /= 10;
            s += prime % 10;
            prime /= 10;
            s += prime % 10;
            prime /= 10;
            s += prime % 10;
            prime /= 10;
            s += prime % 10;
            if(s != sum) {
                p.remove(i);
            }
        }
        primes = new Trie();
        for(int prime : p) {
            primes.add(prime, 4);
        }
        
        if(primes.has(f)) {
            dfs(0, 1, primes.c[f]);
        }
        if(first) {
            out.println("NONE");
        }
        out.close();
    }
    
    static void dfs(int r, int c, Trie t) {
        if(r == 5) {
            // finally check the lower left -> upper right diagonal
            Trie tr = primes;
            for(int i = 0; i < 5; i++) {
                if(tr.has(mat[5 - i - 1][i])) {
                    tr = tr.c[mat[5 - i - 1][i]];
                } else {
                    return;
                }
            }
            // if valid, output
            if(!first) {
                out.println("");
            }
            first = false;
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    out.print(mat[i][j]);
                }
                out.println("");
            }
        } else {
            for(int i = 0; i < 10; i++) {
                if(t.has(i)) {
                    mat[r][c] = i;
                    if(c == 4) {
                        // check if it is possible for every column to be a prime
                        // if impossible, prune this branch
                        Trie tr;
                        for(int j = 0; j < 5; j++) {
                            tr = primes;
                            for(int k = 0; k <= r; k++) {
                                if(tr.has(mat[k][j])) {
                                    tr = tr.c[mat[k][j]];
                                } else {
                                    return;
                                }
                            }
                        }
                        // check the upper left -> lower right diagonal
                        tr = primes;
                        for(int j = 0; j <= r; j++) {
                            if(tr.has(mat[j][j])) {
                                tr = tr.c[mat[j][j]];
                            } else {
                                return;
                            }
                        }
                        dfs(r + 1, 0, primes);
                    } else {
                        dfs(r, c + 1, t.c[i]);
                    }
                }
            }
        }
    }
}

class Trie {
    Trie[] c = new Trie[10];
    
    boolean has(int d) {
        return c[d] != null;
    }
    
    void add(int x, int i) {
        if(i == -1) {
            return;
        }
        int n = x, b = 1, j = i;
        while(j --> 0) {
            n /= 10;
            b *= 10;
        }
        if(c[n] == null) {
            c[n] = new Trie();
        }
        c[n].add(x - n * b, i - 1);
    }
}