/*
ID: plutonj1
LANG: JAVA
TASK: nocows
*/

import java.io.*;
import java.util.*;

class nocows {
	static String name = "nocows";
	static PrintWriter out;
	
	static int N, K;
	static long[][] dp;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		N = Integer.parseInt(input.nextToken());
		K = Integer.parseInt(input.nextToken());
		dp = new long[N + 1][K + 1];
		
		for(int i = 1; i <= K; i++) {
			dp[1][i] = 1;
		}
		for(int i = 2; i <= N; i++) {
			for(int j = 1; j <= K; j++) {
				for(int l = 1; l < i - 1; l++) {
					dp[i][j] += dp[l][j - 1] * dp[i - l - 1][j - 1];
					dp[i][j] %= 9901;
				}
			}
		}
		
		out.println((dp[N][K] - dp[N][K - 1] + 9901) % 9901);
		out.close();
	}
}