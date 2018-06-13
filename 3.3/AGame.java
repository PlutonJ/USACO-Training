/*
ID: plutonj1
LANG: JAVA
TASK: game1
*/

// dfs minimax with memoization

import java.io.*;
import java.util.*;

class game1 {
	static String name = "game1";
	static BufferedReader in;
	static PrintWriter out;
	
	// memoization
	static int[][][] minimax;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		int[] seq = new int[N];
		int i = 0;
		String cheese;
		while((cheese = in.readLine()) != null && cheese.length() > 0) {
			input = new StringTokenizer(cheese);
			while(input.hasMoreTokens()) {
				seq[i++] = Integer.parseInt(input.nextToken());
			}
		}
		
		int[] scores = new int[2];
		minimax = new int[N][N][2];
		for(int[][] buf : minimax) {
			Arrays.fill(buf, null);
		}
		i = 0;
		int start = 0, end = N;
		while(end - start > 1) {
			int[] move = minimax(seq, start, end);
			scores[(i++) % 2] += move[2];
			if(move[0] == 0) {
				start++;
			} else {
				end--;
			}
		}
		// last number left
		scores[i % 2] += seq[start];
		
		out.println(scores[0] + " " + scores[1]);
		out.close();
	}
	
	// the int[] returned contains: {0 for taking the leftmost number / 1 for taking the rightmost number, 
	//							     net gain of the move against a perfect player, 
	// 								 the number that is taken}
	static int[] minimax(int[] seq, int start, int end) {
		int[] result = null;
		// if memoized
		if(minimax[start][end - 1] != null) {
			return minimax[start][end - 1];
		} else if(end - start == 2) {	// base case: take the greater of the two
			result = seq[start] >= seq[end - 1] ? new int[] {0, seq[start], seq[start]} : new int[] {1, seq[end - 1], seq[end - 1]};
		} else {
			int a, b;
			// test which move yields more net gain
			if((a = seq[start] - minimax(seq, start + 1, end)[1]) >= (b = seq[end - 1] - minimax(seq, start, end - 1)[1])) {
				result = new int[] {0, a, seq[start]};
			} else {
				result = new int[] {1, b, seq[end - 1]};
			}
		}
		// memoize
		minimax[start][end - 1] = result;
		return result;
	}
}