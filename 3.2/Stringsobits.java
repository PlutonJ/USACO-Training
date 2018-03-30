/*
ID: plutonj1
LANG: JAVA
TASK: kimbits
*/

import java.io.*;
import java.util.*;

class kimbits {
	static String name = "kimbits";
	static BufferedReader in;
	static PrintWriter out;
	
	static int[][] size;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(input.nextToken()), L = Integer.parseInt(input.nextToken());
		long I = Long.parseLong(input.nextToken());
		size = new int[32][32];
		for(int i = 0; i < 32; i++) {
			size[0][i] = 1;
			size[i][0] = 1;
		}
		for(int i = 1; i < 32; i++) {
			for(int j = 1; j < 32; j++) {
				size[i][j] = size[i - 1][j - 1] + size[i - 1][j];
			}
		}
		
		print(N, L, I - 1);
		out.println("");
		out.close();
	}
	
	static void print(int n, int l, long i) {
		if(n == 0) {
			return;
		}
		int s = size[n - 1][l];
		if(s <= i) {
			out.print("1");
			print(n - 1, l - 1, i - s);
		} else {
			out.print("0");
			print(n - 1, l, i);
		}
	}
}