/*
ID: plutonj1
LANG: JAVA
TASK: milk2
*/

import java.io.*;
import java.util.*;

class milk2 {
	static String name = "milk2";
	
	public static void main(String[] args) throws IOException {
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		
		int N = Integer.parseInt(in.readLine());
		Interval[] intervals = new Interval[N];
		for(int i = 0; i < N; i++) {
			StringTokenizer input = new StringTokenizer(in.readLine());
			intervals[i] = new Interval(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
		}
		Arrays.sort(intervals, (a, b) -> a.a - b.a);
		int max = 0, noMax = 0, a = 1000000, b = -1, i = 0;
		for(Interval interval : intervals) {
			if(i++ > 0) {
				//System.out.println(b);
				noMax = Math.max(noMax, interval.a - b);
			}
			if(interval.a <= b) {
				b = Math.max(b, interval.b);
			} else {
				a = interval.a;
				b = interval.b;
			}
			max = Math.max(max, b - a);
		}
		
		out.println(max + " " + noMax);
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