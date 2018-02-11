/*
ID: plutonj1
LANG: JAVA
TASK: numtri
*/

import java.io.*;
import java.util.*;

class numtri {
	static String name = "numtri";
	
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int R = Integer.parseInt(in.readLine());
		int[][] t = new int[R][R];
		for(int i = 0; i < R; i++) {
			input = new StringTokenizer(in.readLine());
			for(int j = 0; j <= i; j++) {
				t[i][j] = Integer.parseInt(input.nextToken());
			}
		}
		for(int i = R - 2; i >= 0; i--) {
			for(int j = 0; j < i + 1; j++) {
				t[i][j] += Math.max(t[i + 1][j], t[i + 1][j + 1]);
			}
		}
		
		out.println(t[0][0]);
		out.close();
	}
}