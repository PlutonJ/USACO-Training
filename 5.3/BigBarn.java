/*
ID: plutonj1
LANG: JAVA
TASK: bigbrn
*/

import java.io.*;
import java.util.*;

class bigbrn {
	static String name = "bigbrn";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(input.nextToken()), T = Integer.parseInt(input.nextToken());
        boolean[][] grid = new boolean[N][N];
        int[][] dp = new int[N][N];
        int max = 0;
        for(int i = 0; i < T; i++) {
            input = new StringTokenizer(in.readLine());
            grid[Integer.parseInt(input.nextToken()) - 1][Integer.parseInt(input.nextToken()) - 1] = true;
        }
        // dp[i][j] = max sidelength of possible square with top left corner (i, j)
        // dp[i][j] = tree ? 0 : min(dp[i + 1][j + 1], dp[i + 1][j], dp[i][j + 1]) + 1
        for(int i = 0; i < N; i++) {
            dp[i][N - 1] = grid[i][N - 1] ? 0 : 1;
            dp[N - 1][i] = grid[N - 1][i] ? 0 : 1;
        }
        for(int i = N - 2; i >= 0; i--) {
            for(int j = N - 2; j >= 0; j--) {
                dp[i][j] = grid[i][j] ? 0 : Math.min(dp[i + 1][j + 1], Math.min(dp[i + 1][j], dp[i][j + 1])) + 1;
                if(dp[i][j] > max) {
                    max = dp[i][j];
                }
            }
        }
		
        out.println(max);
		out.close();
	}
}