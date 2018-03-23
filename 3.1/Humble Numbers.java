/*
ID: plutonj1
LANG: JAVA
TASK: humble
*/

// Thought process: 
// 		let s be a sorted set of integers that contains the next integer in the set of humble numbers
// 		initialize s by inserting the K prime numbers
// 		the next integer in the set of humble numbers is the smallest element in set s
// 		every time an integer h in set s is determined as the next humble number, 
// 		remove h from s and add the products of h and all K primes to the set
// 		continue generating the next humber numbers and output the Nth one

import java.io.*;
import java.util.*;

class humble {
	static String name = "humble";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int K = Integer.parseInt(input.nextToken()), N = Integer.parseInt(input.nextToken());
		int[] S = new int[K];
		TreeSet<Integer> s = new TreeSet<>();
		input = new StringTokenizer(in.readLine());
		for(int i = 0; i < K; i++) {
			S[i] = Integer.parseInt(input.nextToken());
			s.add(S[i]);
		}
		Arrays.sort(S);
		int i = N, h = -1;
		while(i --> 0) {
			h = s.first();
			s.remove(h);
			for(int j = 0; j < K; j++) {
				if((long)h * S[j] <= Integer.MAX_VALUE) {	// since the h can be storedin32-bit signed integer
					if(s.size() >= N && h * S[j] > s.last()) {
						break;
					}
					s.add(h * S[j]);
				}
			}
		}
		
		out.println(h);
		out.close();
	}
}