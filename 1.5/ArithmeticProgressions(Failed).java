/*
ID: plutonj1
LANG: JAVA
TASK: ariprog
*/

import java.io.*;
import java.util.*;

class ariprog {
	static String name = "ariprog";
	
	static BufferedReader in;
	static PrintWriter out;
	
	static int N, M, i, si = 0, b = 0;			// i: index of int[] bisquares, si: index of sequence, b: common difference of sequence
	static int[] bisquares, sequence;
	static boolean found = false;
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		N = Integer.parseInt(in.readLine());
		M = Integer.parseInt(in.readLine());
		sequence = new int[N];
		Set<Integer> set = new HashSet<>();
		for(int p = 0; p <= M; p++) {						// O(M ^ 2)
			for(int q = p; q <= M; q++) {
				set.add(p * p + q * q);
			}
		}
		bisquares = new int[set.size()];
		i = 0;
		for(Integer in : set) {
			bisquares[i++] = in;
		}
		Arrays.sort(bisquares);								// O((M ^ 2)log(M ^ 2))
		for(int x : bisquares) out.println(x);
		for(int j = 0; j <= bisquares.length - N; j++) {
			sequence[0] = bisquares[j];
			for(int k = j + 1; k <= bisquares.length - N + 1; k++) {
				sequence[1] = bisquares[k];
				b = sequence[1] - sequence[0];
				si = 2;
				i = j;
				findArithmeticProgressions();
			}
		}
		
		if(!found) out.println("NONE");	
		out.close();
	}
	
	static void findArithmeticProgressions() {
		if(si == N - 1) {
			out.println(sequence[0] + " " + b);
			found = true;
			return;
		}
		int nextValue = sequence[si] + b;
		si++;
		while(bisquares[++i] < nextValue) {}				// find next bisquare until no less than the desired value
		if(bisquares[--i] == nextValue) {		// if is not overshot
			sequence[si] = nextValue;
			findArithmeticProgressions();
		}
	}
}