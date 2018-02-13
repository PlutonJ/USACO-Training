/*
ID: plutonj1
LANG: JAVA
TASK: hamming
*/

import java.io.*;
import java.util.*;

class hamming {
	static String name = "hamming";
	static PrintWriter out;
	
	static int N, B, D;
	static List<Integer> check = new LinkedList<Integer>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		N = Integer.parseInt(input.nextToken());
		B = Integer.parseInt(input.nextToken());
		D = Integer.parseInt(input.nextToken());
		
		check.add(0);
		List<Integer> ans = new LinkedList<Integer>();
		ans.add(0);
		ans.addAll(solve(0, N));
		for(int i = 0; i < N; i++) {
			out.print(ans.get(i));
			if(i == ans.size() - 1) {
				out.print("\n");
				break;
			} else if((i + 1) % 10 == 0) {
				out.print("\n");
			} else {
				out.print(" ");
			}
		}
		out.close();
	}
	
	
	static List<Integer> solve(int seed, int n) {		// greedy dfs
		if(--n == 0) return new LinkedList<Integer>();
		List<Integer> solution = null;
		int x = seed;
		boolean notFirstIteration = false;
		do {
			if(notFirstIteration) {
				check.remove(check.size() - 1);
			} else {
				notFirstIteration = true;
			}
			solution = new LinkedList<Integer>();
			boolean valid = false;				// for the next smallest value that has hamming distance >= D with all other values
			while(!valid) {
				x++;
				valid = true;
				for(Integer i : check) {
					if(dist(x, i) < D) {
						valid = false;
						continue;
					}
				}
			}
			solution.add(x);
			check.add(x);
			solution.addAll(solve(x, n));				// solve with that seed
		} while(solution.size() < n);					// if does not yield enough values, try the next smallest value
		return solution;
	}
	
	static int dist(int a, int b) {
		int diff = a ^ b, dist = 0;
		for(int i = 0; i < B; i++) {
			if((diff >> i & 1) == 1) dist++;
		}
		return dist;
	}
}