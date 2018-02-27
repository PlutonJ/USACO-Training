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
	
	static char[] S;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		String line;
		char[][] P = new char[200][0];
		int i = 0;
		while(!(line = in.readLine()).equals(".")) {
			input = new StringTokenizer(line);
			while(input.hasMoreElements()) {
				P[i++] = input.nextToken().toCharArray();
			}
		}
		StringBuilder sBuilder = new StringBuilder();
		while((line = in.readLine()) != null && !line.equals("")) {
			sBuilder.append(line);
		}
		S = sBuilder.toString().toCharArray();
		//boolean[][] dp = new boolean[S.length + 1][S.length + 1];	// (i, j) = (# of primitive, index), dp[i][j] = reachable
		boolean[][] dp = new boolean[2][S.length + 1];				// (i, j) = (# of primitive % 2, index), dp[i][j] = reachable
		boolean[] allTimeReachable = new boolean[S.length + 1];
		dp[0][0] = true;
		i = 0;
		boolean cont;
		out: do {
			cont = false;
			dp[(i + 1) % 2] = new boolean[S.length + 1];
			for(int j = 0; j < S.length; j++) {
				if(dp[i % 2][j]) {								// if reachable
					//System.out.println("dp: " + i + ", " + j);
					for(char[] primitive : P) {
						if(primitive.length == 0) break;
						//System.out.println("primitive: " + new String(primitive));
						int index;
						if(j + primitive.length <= S.length && !allTimeReachable[j + primitive.length] && (index = fits(primitive, j)) != -1) {	// only check if destination not reachable yet
							//System.out.println("index: " + index);
							dp[(i + 1) % 2][index] = true;		// if fits then S[index] is reachable
							allTimeReachable[index] = true;
							cont = true;
							if(index == S.length) {
								i++;
								break out;
							}
						}
					}
				}
			}
			i++;
		} while(cont && i <= S.length);						// if no new reachable index or 
															// # of primitives = length of S
															// (this is maximum since min length of primitive = 1)
		
		int length = 0;
		for(int j = S.length; j >= 0; j--) {
			if(allTimeReachable[j]) {						// find furthest reachable
				length = j;
				break;
			}
		}
		out.println(length);
		out.close();
	}
	
	
	static int fits(char[] primitive, int i) {
		for(char c : primitive) {
			//if(c == ' ') break;
			if(i == S.length) return -1;
			if(S[i++] != c) return -1;
		}
		//System.out.println(i);
		return i;
	}
}