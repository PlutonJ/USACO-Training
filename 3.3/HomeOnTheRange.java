/*
ID: plutonj1
LANG: JAVA
TASK: range
*/

import java.io.*;
import java.util.*;

class range {
	static String name = "range";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		boolean[][] range = new boolean[N][N];
		for(int i = 0; i < N; i++) {
			String row = in.readLine();
			for(int j = 0; j < N; j++) {
				range[i][j] = row.charAt(j) == '1';
			}
		}
		
		// brute force, fails test case 7
		/* for(int i = 2; i <= N; i++) {
			int count = 0;
			for(int j = 0; j < N - i + 1; j++) {
				next: for(int k = 0; k < N - i + 1; k++) {
					for(int l = 0; l < i; l++) {
						for(int m = 0; m < i; m++) {
							if(!range[j + l][k + m]) {
								continue next;
							}
						}
					}
					count++;
				}
			}
			if(count > 0) {
				out.println(i + " " + count);
			}
		}*/ 
		
		// reduce the range's sidelength by 1 each iteration by setting each square to if there
		// exists a 2 by 2 square with the same top left corner
		// after reduction, if the squares still form a 2 by 2 square, that means they form a 3 by 3
		// square before reduction
		int[] n = new int[N - 1];
		for(int i = 0; i < N - 1; i++) {
			boolean[][] newRange = new boolean[N - i - 1][N - i - 1];
			for(int j = 0; j < newRange.length; j++) {
				for(int k = 0; k < newRange.length; k++) {
					if(range[j][k] && range[j + 1][k] && range[j][k + 1] && range[j + 1][k + 1]) {
						newRange[j][k] = true;
						n[i]++;
					}
				}
			}
			range = newRange;
		}
		
		for(int i = 0; i < N - 1; i++) {
			if(n[i] != 0){
				out.println(i + 2 + " " + n[i]);
			}
		}
		out.close();
	}
}