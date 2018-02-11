/*
ID: plutonj1
LANG: JAVA
TASK: barn1
*/

import java.io.*;
import java.util.*;

class barn1 {
	static String name = "barn1";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int M = Integer.parseInt(input.nextToken()), S = Integer.parseInt(input.nextToken()), C = Integer.parseInt(input.nextToken());
		int[] stalls = new int[C];
		for(int i = 0; i < C; i++) {
			stalls[i] = Integer.parseInt(in.readLine());
		}
		Arrays.sort(stalls);	// sorted stalls with cows
		Interval[] intervals = new Interval[C - 1];
		int last = stalls[0], first = last;
		for(int i = 0; i < C - 1; i++) {
			intervals[i] = new Interval(last, last = stalls[i + 1]);		// intervals of stalls with no cows
		}
		Arrays.sort(intervals, (a, b) -> (b.b - b.a) - (a.b - a.a));		// sort intervals in non-ascending order
		int s = last - first;
		for(int i = 1; i < M && i < C; i++) {
			s -= intervals[i - 1].b - intervals[i - 1].a;					// subtract largest intervals
		}
		
		out.println(s + Math.min(M, C));									// apparently the subtracted intervals included ones needed
		out.close();
	}
}

class Interval {
	int a, b;
	
	Interval(int a, int b) {
		this.a = a;
		this.b = b;
	}
}