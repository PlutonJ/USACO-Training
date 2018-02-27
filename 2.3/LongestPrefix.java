/*
ID: plutonj1
LANG: JAVA
TASK: prefix
*/

// using Strings instead of char[]s
// N = length of string, M = # of primitives
// Time complexity = O(MN)

import java.io.*;
import java.util.*;

class prefix {
	static String name = "prefix";
	static PrintWriter out;
	
	static String S;
	static List<String> P = new LinkedList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		String line;
		while(!(line = in.readLine()).equals(".")) {
			input = new StringTokenizer(line);
			while(input.hasMoreElements()) {
				P.add(input.nextToken());
			}
		}
		StringBuilder sBuilder = new StringBuilder();
		while((line = in.readLine()) != null && !line.equals("")) {
			sBuilder.append(line);
		}
		S = sBuilder.toString();
		
		boolean[] dp = new boolean[S.length() + 1];
		dp[0] = true;
		for(int i = 0; i <= S.length(); i++) {
			if(dp[i]) {
				for(String p : P) {
					if(i + p.length() <= S.length() && p.equals(S.substring(i, i + p.length()))) {
						dp[i + p.length()] = true;
					}
				}
			}
		}
		
		for(int i = S.length(); i >= 0; i--) {
			if(dp[i]) {
				out.println(i);
				break;
			}
		}
		out.close();
	}
}