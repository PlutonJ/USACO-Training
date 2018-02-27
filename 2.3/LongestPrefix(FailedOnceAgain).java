/*
ID: plutonj1
LANG: JAVA
TASK: prefix
*/

import java.io.*;
import java.util.*;

class prefix {
	static String name = "prefix";
	static PrintWriter out;
	
	static List<char[]> P = new LinkedList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		String line;
		while(!(line = in.readLine()).equals(".")) {
			input = new StringTokenizer(line);
			while(input.hasMoreElements()) {
				P.add(input.nextToken().toCharArray());
			}
		}
		for(int i = 0; i < P.size(); i++) {
			char[] p = P.get(i);
			if(longestPrefix(p, p) == p.length) {	// if primitive can be formed by combining other primitives
				P.remove(i--);														// remove primitive to reduce computation
			}
		}
		for(char[] p : P) {
			System.out.println(new String(p));
		}
		StringBuilder sBuilder = new StringBuilder();
		while((line = in.readLine()) != null && !line.equals("")) {
			sBuilder.append(line);
		}
		
		out.println(longestPrefix(sBuilder.toString().toCharArray(), null));
		out.close();
	}
	
	static int longestPrefix(char[] s, char[] exclude) {	// find length of longest prefix of the string excluding a given primitive
		// Below is almost straight from LongestPrefix(FailedAgain).java
		int[][] dp = new int[2][s.length + 1];				// (i, j) = (iteration index % 2, index), dp[i][j] = end index + 1(0 = not reached)
		boolean[] checked = new boolean[s.length + 1];
		int i = 0;
		dp[0][s.length] = s.length;
		out: while(true) {
			dp[(i + 1) % 2] = new int[s.length + 1];
			boolean anyReachable = false;
			for(int j = 0; j <= s.length; j++) {
				if(dp[i % 2][j] > 0) {					// if reached
					checked[j] = true;
					for(char[] primitive : P) {
						if(primitive == exclude) continue;
						int index;
						if((index = getNextIndex(s, j, primitive)) != -1) {
							anyReachable = true;
							dp[(i + 1) % 2][index] = Math.max(dp[i % 2][index], dp[i % 2][j]);		// Math.max needed? or just dp[i % 2][j]
							if(index == 0) {
								i++;
								break out;
							}
						}
					}
				}
			}
			if(!anyReachable) {
				int index = s.length - 1;
				while(checked[index]) index--;
				if(index > 0) {
					dp[(i + 1) % 2][index] = index;
				} else {
					i++;
					break;
				}
			}
			i++;
		}
		return dp[i % 2][0];
	}
	
	// Also almost straight from LongestPrefix(FailedAgain).java
	static int getNextIndex(char[] s, int i, char[] primitive) {
		int len = primitive.length;
		if(len > 0 && i - len >= 0) {
			for(int j = 1; j <= len; j++) {
				if(s[i - j] != primitive[len - j]) return -1;
			}
			return i - len;
		}
		return -1;
	}
}