/*
ID: plutonj1
LANG: JAVA
TASK: subset
*/

import java.io.*;
import java.util.*;

class subset {
	static String name = "subset";
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine()), sum = N * (N + 1) / 4;
		if((N * (N + 1) / 2) % 2 == 1) {
			out.println(0);
			out.close();
			return;
		}
		long[][] dp = new long[N + 1][sum + 1];
		for(int i = 1; i <= N; i++) dp[i][0] = 1;
		dp[1][1] = 1;
		for(int i = 2; i <= N; i++) {
			for(int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j] + (j >= i ? dp[i - 1][j - i] : 0);
			}
		}
		
		out.println(dp[N][sum] / 2);
		out.close();
	}
}