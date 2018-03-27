/*
ID: plutonj1
LANG: JAVA
TASK: stamps
*/

// Times out on test case 10

import java.io.*;
import java.util.*;

class stamps {
	static String name = "stamps";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int K = Integer.parseInt(input.nextToken()), N = Integer.parseInt(input.nextToken());
		int[] v = new int[N];
		input = new StringTokenizer(in.readLine());
		for(int i = 0; i < N; i++) {
			if(!input.hasMoreTokens()) {
				input = new StringTokenizer(in.readLine());
			}
			v[i] = Integer.parseInt(input.nextToken());
		}
		Arrays.sort(v);
		
		int[] dp = new int[K * v[N - 1] + 1];
		Arrays.fill(dp, -1);
		dp[0] = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < dp.length - v[i]; j++) {
				if(dp[j] >= 0 && dp[j] < K) {
					dp[j + v[i]] = dp[j + v[i]] >= 0 ? Math.min(dp[j + v[i]], dp[j] + 1) : dp[j] + 1;
				}
			}
		}
		
		int max = 0, cur = 0;
		for(int i = 1; i < dp.length; i++) {
			if(dp[i] > 0) {
				cur++;
			} else {
				max = Math.max(max, cur);
				cur = 0;
			}
		}
		max = Math.max(max, cur);
				
		out.println(max);
		out.close();
	}
}