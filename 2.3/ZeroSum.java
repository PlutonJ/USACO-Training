/*
ID: plutonj1
LANG: JAVA
TASK: zerosum
*/

import java.io.*;
import java.util.*;

class zerosum {
	static String name = "zerosum";
	static BufferedReader in;
	static PrintWriter out;
	
	static int N;
	
	public static void main(String[] args) throws IOException {
		//in = new BufferedReader(new InputStreamReader(System.in));
		//out = new PrintWriter(new OutputStreamWriter(System.out));
		in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		N = Integer.parseInt(in.readLine());
		
		dfs(2, 1, "1");
		out.close();
	}
	
	static void dfs(int n, int v, String s) {
		if(n == N + 1) {
			if(v == 0) out.println(s);		// if value == 0 print else do nothing
			return;
		}
		char[] chs = s.toCharArray();
		int i = chs.length - 1, vPre = 0, digit = 1;
		while(i > 0 && chs[i] != '+' && chs[i] != '-') {	// while not front of string or +/-
			if(chs[i] != ' ') {								// ignore space
				vPre += digit * (chs[i] - '0');				// "vPre" == "previous value"
				digit *= 10;
			}
			i--;
		}
		if(chs[i] == '1') vPre += digit;					// if while loop went to front of string append the 1
		if(chs[i] == '-') dfs(n + 1, v - vPre * 9 - n, s + " " + n);	// this before the other two dfs call because of ascii order
		else dfs(n + 1, v + vPre * 9 + n, s + " " + n);
		dfs(n + 1, v + n, s + "+" + n);
		dfs(n + 1, v - n, s + "-" + n);
	}
}