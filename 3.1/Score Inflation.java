/*
ID: plutonj1
LANG: JAVA
TASK: inflate
*/

// Optimization: dp is always non-decreasing, thus max(dp) == dp[dp.length - 1]

import java.io.*;
import java.util.*;

class inflate {
	static String name = "inflate";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int M = Integer.parseInt(input.nextToken()), N = Integer.parseInt(input.nextToken());
		int[] p = new int[N], t = new int[N];
		for(int i = 0; i < N ; i++) {
			input = new StringTokenizer(in.readLine());
			p[i] = Integer.parseInt(input.nextToken());
			t[i] = Integer.parseInt(input.nextToken());
		}
		
		// dp[i] = most points with contest of length at most i minutes
		// dp[i + t[j]] = max(dp[i + t[j]], dp[i] + p[j])
		// answer = max(dp)
		int[] dp = new int[M + 1];
		int maxPoints = -1;
		for(int j = 0; j < N; j++) {
			for(int i = 0; i <= M - t[j]; i++) {
				dp[i + t[j]] = Math.max(dp[i + t[j]], dp[i] + p[j]);
				maxPoints = Math.max(maxPoints, dp[i + t[j]]);
			}
		}
		
		out.println(maxPoints);
		out.close();
	}
}