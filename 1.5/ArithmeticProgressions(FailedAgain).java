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
	
	static int N, M, i, a, b;			// i: index in int[] bisquares, a: first term of sequence, b: common difference of sequence
	static int[] bisquares;
	static boolean found = false;
	static boolean[] used;
	static Set<Seq> seqs = new TreeSet<Seq>((a, b) -> a.b - b.b == 0 ? a.a - b.a : a.b - b.b);
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		N = Integer.parseInt(in.readLine());
		M = Integer.parseInt(in.readLine());
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
		int maxB = bisquares[bisquares.length - 1] / (N - 1);
		for(b = 1; b <= maxB; b++) {
			/*for(int j = 0; j <= bisquares.length - N; j++) {
				i = j;
				findArithmeticSequence(bisquares[i], 1);
			}*/
			i = 0;
			a = bisquares[0];
			used = new boolean[bisquares.length];
			newFindArithmeticSequence(1);
		}
		
		if(!found) out.println("NONE");
		else for(Seq seq : seqs) out.println(seq);
		out.close();
	}
	
	static void newFindArithmeticSequence(int depth) {
		if(depth == 1) {
			used[i] = true;
		} else if(depth == N) {
			seqs.add(new Seq(a, b));
			found = true;
		}
		int next = bisquares[i] + b, nextI = i + 1;
		while(i < bisquares.length - 1 && bisquares[++i] < next) {}
		//if(b == 4) System.out.println("b: " + b + ", depth: " + depth + ", found bisquare: " + bisquares[i]);
		if(bisquares[i] == next) {
			newFindArithmeticSequence(depth + 1);
		}
		if(nextI <= bisquares.length - N && !used[nextI]) {
			i = nextI;
			a = bisquares[i];
			//System.out.println(a);
			newFindArithmeticSequence(1);
		}
	}
	
	static void findArithmeticSequence(int last, int depth) {
		if(depth == N) {
			out.println((last - (N - 1) * b) + " " + b);
			found = true;
			return;
		}
		while(i < bisquares.length - 1 && bisquares[++i] < last + b) {}		// go to next bisquare if less than next in sequence
		if(bisquares[i] == last + b) {			// if found value is not overshot
			findArithmeticSequence(last + b, depth + 1);
		}
	}
}

class Seq {
	int a, b;
	
	Seq(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		return a + " " + b;
	}
}