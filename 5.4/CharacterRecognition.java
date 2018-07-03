/*
ID: plutonj1
LANG: JAVA
TASK: charrec
*/

// ????????????????????
// transcribed from some c++ code found online

import java.io.*;
import java.util.*;

class charrec {
	static String name = "charrec";
	static BufferedReader inP, font;
	static PrintWriter out;
    
    static int N = 30, M = 1220, bad = 120;
	
	public static void main(String[] args) throws IOException {
		inP = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		font = new BufferedReader(new FileReader("font.in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
        char maz[][][] = new char[N][N][N], in[][] = new char[M][N], ans[] = new char[M];
        int f[][][] = new int[M][N][3], g[][][] = new int[M][N][N], dp[] = new int[M], from[] = new int[M], key[] = new int[M];
        int n, i, j, k, x, y, ff, tmp;
        font.readLine();
        for(i = 0; i <= 26; i++) {
            for(j = 0; j < 20; j++) {
                maz[i][j] = font.readLine().toCharArray();
            }
        }
        n = Integer.parseInt(inP.readLine());
        for(i = 0; i < n; i++) {
            in[i] = inP.readLine().toCharArray();
        }
        for(i = 0; i < n; i++) {
            for(j = 0; j <= 26; j++) {
                for(k = 0; k < 20; k++) {
                    tmp = 0;
                    for(x = 0; x < 20; x++) {
                        if(maz[j][k][x] != in[i][x]) {
                            tmp++;
                        }
                        g[i][j][k] = tmp;
                    }
                }
            }
        }
        for(int[][] b0 : f) {
            for(int[] b1 : b0) {
                Arrays.fill(b1, -1);
            }
        }
        for(i = 0; i < n; i++) {
            for(j = 0; j <= 26; j++) {
                if(i + 19 < n) {
                    tmp = 0;
                    for(x = 0; x < 20; x++) {
                        tmp += g[i + x][j][x];
                    }
                    if(tmp <= bad) {
                        f[i][j][1] = tmp;
                    }
                }
                if(i + 20 < n) {
                    for(k = 0; k < 20; k++) {
                        tmp = 0;
                        for(x = 0; x < 20; x++) {
                            ff = i + x;
                            if(x > k) {
                                ff++;
                            }
                            tmp += g[ff][j][x];
                        }
                        if(tmp <= bad && (f[i][j][2] == -1 || tmp < f[i][j][2])) {
                            f[i][j][2] = tmp;
                        }
                    }
                }
                if(i + 18 < n) {
                    for(k = 0; k < 20; k++) {
                        tmp = 0;
                        for(x = 0; x < 20; x++) {
                            if(x == k) {
                                continue;
                            }
                            ff = i + x;
                            if(x > k) {
                                ff--;
                            }
                            tmp += g[ff][j][x];
                        }
                        if(tmp <= bad && (f[i][j][0] == -1 || tmp < f[i][j][0])) {
                            f[i][j][0] = tmp;
                        }
                    }
                }
            }
            f[i][27][0] = f[i][27][1] = f[i][27][2] = bad + 10;
        }
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for(i = 0; i < n; i++) {
            if(dp[i] != -1) {
                for(j = 0; j <= 27; j++) {
                    for(k = 0; k <= 2; k++) {
                        if(f[i][j][k] != -1) {
                            if(dp[i + k + 19] == -1 || dp[i + k + 19] > dp[i] + f[i][j][k]) {
                                dp[i + k + 19] = dp[i] + f[i][j][k];
                                from[i + k + 19] = i;
                                key[i + k + 19] = j;
                            }
                        }
                    }
                }
            }
        }
        for(i = n, j = 0; i > 0; i = from[i], j++) {
            if(key[i] == 0) {
                ans[j] = ' ';
            } else if(key[i] == 27) {
                ans[j] = '?';
            } else {
                ans[j] = (char)('a' + key[i] - 1);
            }
        }
        for(i = j - 1; i >= 0; i--) {
            out.print(ans[i]);
        }
        out.println("");
        
		out.close();
	}
}