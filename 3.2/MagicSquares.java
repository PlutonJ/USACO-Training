/*
ID: plutonj1
LANG: JAVA
TASK: msquare
*/

// square[] = {1, 2, 3, 4, 5, 6, 7, 8}
// A: reverse => {8, 7, 6, 5, 4, 3, 2, 1}
// B: [0 : 3] >> 1 wrap, [4 : 7] << 1 wrap => {4, 1, 2, 3, 6, 7, 8, 5}
// C: [1] -> [2] -> [6] -> [5]( -> [1]) => {1, 6, 2, 4, 5, 7, 3, 8}
// Since there are only 8! = 40320 possible permutations, brute force bfs

import java.io.*;
import java.util.*;

class msquare {
	static String name = "msquare";
	static BufferedReader in;
	static PrintWriter out;
	
	static final int[] offset = {1 << 21, 1 << 18, 1 << 15, 1 << 12, 1 << 9, 1 << 6, 1 << 3, 1};
	static final int[] mask = {7 << 21, 7 << 18, 7 << 15, 7 << 12, 7 << 9, 7 << 6, 7 << 3, 7};
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int[] square = new int[8];
		input = new StringTokenizer(in.readLine());
		for(int i = 0; i < 8; i++) {
			square[i] = Integer.parseInt(input.nextToken());
		}
		int dest = hash(square);
		Map<Integer, StringBuilder> seq = new HashMap<>();
		int start = hash(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		seq.put(start, new StringBuilder());
		Queue<Integer> bfs = new LinkedList<>();
		bfs.offer(start);
		while(bfs.size() > 0) {
			int permutation = bfs.poll();
			if(permutation == dest) {
				out.println(seq.get(permutation).length());
				out.println(seq.get(permutation).toString());
				break;
			}
			StringBuilder sequence = seq.get(permutation);
			int a = A(permutation), b = B(permutation), c = C(permutation);
			//printDehash(a);
			//printDehash(b);
			//printDehash(c);
			if(!seq.containsKey(a)) {
				seq.put(a, new StringBuilder(sequence).append('A'));
				bfs.offer(a);
			}
			if(!seq.containsKey(b)) {
				seq.put(b, new StringBuilder(sequence).append('B'));
				bfs.offer(b);
			}
			if(!seq.containsKey(c)) {
				seq.put(c, new StringBuilder(sequence).append('C'));
				bfs.offer(c);
			}
		}
		
		out.close();
	}
	
	
	static void printDehash(int hash) {
		System.out.println(Integer.toOctalString(hash));
	}
	
	static int hash(int[] square) {
		int hash = 0;
		for(int i = 0; i < 8; i++) {
			hash += (square[i] - 1) * offset[i];
		}
		return hash;
	}
	
	static int A(int hash) {
		for(int i = 0; i < 4; i++) {
			hash = swap(i, 7 - i, hash);
		}
		return hash;
	}
	
	static int B(int hash) {
		hash = swap(0, 1, hash);
		hash = swap(0, 2, hash);
		hash = swap(0, 3, hash);
		hash = swap(7, 6, hash);
		hash = swap(7, 5, hash);
		hash = swap(7, 4, hash);
		return hash;
	}
	
	static int C(int hash) {
		hash = swap(1, 2, hash);
		hash = swap(1, 5, hash);
		hash = swap(1, 6, hash);
		return hash;
	}
	
	static int swap(int i, int j, int hash) {
		int swap = hash & mask[i];
		hash -= swap;
		swap /= offset[i];
		hash += (hash & mask[j]) / offset[j] * offset[i];
		hash -= hash & mask[j];
		hash += swap * offset[j];
		return hash;
	}
}