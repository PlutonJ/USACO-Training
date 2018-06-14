/*
ID: plutonj1
LANG: JAVA
TASK: rockers
*/

import java.io.*;
import java.util.*;

class rockers {
	static String name = "rockers";
	static BufferedReader in;
	static PrintWriter out;
    
    static int N, T, M;
    static int[][] max;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        N = Integer.parseInt(input.nextToken());
        T = Integer.parseInt(input.nextToken());
        M = Integer.parseInt(input.nextToken());
        int[] len = new int[N];
        input = new StringTokenizer(in.readLine());
        for(int i = 0; i < N; i++) {
            len[i] = Integer.parseInt(input.nextToken());
        }
        // max # of songs using from len[i] to len[j] that have a total length of less than T
        max = new int[N][N];
        for(int i = 0; i < N; i++) {
            // if length of song i is less than the limit then max is 1, otherwise max is 0
            max[i][i] = len[i] <= T ? 1 : 0;
        }
        for(int i = 0; i < N - 1; i++) {
            // to get the maximum possible #, maintain a int array for the maximum # of songs for all possible sums in [0, T]
            int[] dp = new int[T + 1];
            dp[len[i]] = max[i][i];
            // current max
            int m = max[i][i];
            for(int j = i + 1; j < N; j++) {
                // reverse to avoid repeatedly adding len[j] to a possible sum
                for(int k = T - 1; k >= 0; k--) {
                    if((k == 0 || dp[k] > 0) && k + len[j] <= T) {
                        dp[k + len[j]] = Math.max(dp[k] + 1, dp[k + len[j]]);
                        // take max
                        if(dp[k + len[j]] > m) {
                            m = dp[k + len[j]];
                        }
                    }
                    max[i][j] = m;
                }
            }
            /*for(int x : dp) {
                System.out.print(x + " ");
            }
            System.out.println("");*/
        }
        /*for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.print(max[i][j]);
            }
            System.out.println("");
        }*/
		
        out.println(max(0, 0));
		out.close();
	}
    
    static int max(int n, int start) {
        // if already recursively determined M discs, no more can be used
        if(n == M) {
            return 0;
        }
        int m = 0;
        // here i is the right bound of te used songs in this disc
        for(int i = start; i < N; i++) {
            int s;
            if((s = max(n + 1, i + 1) + max[start][i]) > m) {
                m = s;
            }
        }
        return m;
    }
}