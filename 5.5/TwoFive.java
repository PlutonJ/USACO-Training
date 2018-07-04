/*
ID: plutonj1
LANG: JAVA
TASK: twofive
*/

// ???????????????????????????

import java.io.*;
import java.util.*;

class twofive {
	static String name = "twofive";
	static BufferedReader in;
	static PrintWriter out;
    
    static int len[] = new int[5], t[] = new int[5], maxr[] = new int[5], maxc[] = new int[5];
	static long code, f[][][][][] = new long[6][6][6][6][6];
    static boolean[] v = new boolean[25];
    static char[][] chmat = new char[5][5];
    
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        char c = in.readLine().charAt(0);
        code = 0;
        int mode = 0;
        switch(c) {
            case 'N': 
                code = Long.parseLong(in.readLine());
            break;
            case 'W': 
                String cheese = in.readLine();
                mode = 1;
                chmat[0] = cheese.substring(0, 5).toCharArray();
                chmat[1] = cheese.substring(5, 10).toCharArray();
                chmat[2] = cheese.substring(10, 15).toCharArray();
                chmat[3] = cheese.substring(15, 20).toCharArray();
                chmat[4] = cheese.substring(20, 25).toCharArray();
            break;
        }
        Arrays.fill(v, true);
        Arrays.fill(maxr, -1);
        Arrays.fill(maxc, -1);
		for(int i = 0; i < 5; i++) {
            len[i] = 0;
            for(int j = 0; j < 5; j++) {
                len[i]++;
                if(mode == 0) {
                    for(int k = 0; k < 25; k++) {
                        if(v[k] && k > maxr[i] && k > maxc[j]) {
                            f = new long[6][6][6][6][6];
                            f[5][5][5][5][5] = 1;
                            v[k] = false;
                            maxr[i] = maxc[j] = k;
                            long tmp = dp(len[0], len[1], len[2], len[3], len[4], 0);
                            if(code <= tmp) {
                                chmat[i][j] = (char)('A' + k);
                                break;
                            } else {
                                v[k] = true;
                                code -= tmp;
                            }
                        }
                    }
                } else {
                    for(int k = 0; k < chmat[i][j] - 'A'; k++) {
                        if(v[k] && k > maxr[i] && k > maxc[j]) {
                            f = new long[6][6][6][6][6];
                            f[5][5][5][5][5] = 1;
                            v[k] = false;
                            maxr[i] = maxc[j] = k;
                            code += dp(len[0], len[1], len[2], len[3], len[4], 0);
                            v[k] = true;
                        }
                    }
                    v[chmat[i][j] - 'A'] = false;
                }
            }
        }
        if(mode == 0) {
            for(int i = 0; i < 5; i++) {
                out.print(chmat[i]);
            }
            out.println("");
        } else {
            out.println(code + 1);
        }
        
		out.close();
	}
    
    static void fill(long x) {
        for(long[][][][] i : f) {
            for(long[][][] j : i) {
                for(long[][] k : j) {
                    for(long[] l : k) {
                        Arrays.fill(l, x);
                    }
                }
            }
        }
    }
    
    static long dp(int a, int b, int c, int d, int e, int k) {
        //System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + k);
        if(f[a][b][c][d][e] > 0) {
            return f[a][b][c][d][e];
        }
        if(!v[k]) {
            return dp(a, b, c, d, e, k + 1);
        }
        if(a < 5 && k > maxr[0] && k > maxc[a]) {
            f[a][b][c][d][e] += dp(a + 1, b, c, d, e, k + 1);
        }
        if(b < a && k > maxr[1] && k > maxc[b]) {
            f[a][b][c][d][e] += dp(a, b + 1, c, d, e, k + 1);
        }
        if(c < b && k > maxr[2] && k > maxc[c]) {
            f[a][b][c][d][e] += dp(a, b, c + 1, d, e, k + 1);
        }
        if(d < c && k > maxr[3] && k > maxc[d]) {
            f[a][b][c][d][e] += dp(a, b, c, d + 1, e, k + 1);
        }
        if(e < d && k > maxr[4] && k > maxc[e]) {
            f[a][b][c][d][e] += dp(a, b, c, d, e + 1, k + 1);
        }
        return f[a][b][c][d][e];
    }
}