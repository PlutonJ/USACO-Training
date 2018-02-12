/*
ID: plutonj1
LANG: JAVA
TASK: sort3
*/

// thought process:
// the final records will be in this format: 
// [a a a a a a|b b b b b b b b b|c c c c]  (a, b, c refer to 1, 2, 3)
//  \____1____/m\_______2_______/n\__3__/	(1, 2, 3 are sections; m, n are indices)
// observe that to sort the records in the least moves, 
// one swaps as many elements in place directly as possible, 
// then performs rotation on the leftover unsorted elements
//
// thus first swap as many b's in 1 with a's in 2, 
// c's in 1 with a's in 3, then rotate the leftover
// non-a's in 1(2 swaps per rotation of element), 
// finally swap c's in 2 with b's in 3
//
// sample: [2 2 1 3 3 3 2 3 1]

import java.io.*;
import java.util.*;

class sort3 {
	static String name = "sort3";
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		int[] records = new int[N];
		for(int i = 0; i < N; i++) {
			records[i] = Integer.parseInt(in.readLine());
		}
		
		int m = 0, n = 0;			// observe that m = # of a's, n = # of a's + # of b's
		for(int i : records) {
			if(i == 1) m++;
			else if(i == 2) n++;
		}
		n += m;
		int bIn1 = 0, cIn1 = 0, aIn2 = 0, cIn2 = 0, aIn3 = 0, swap12, swap13, leftoverBIn1;
		for(int i = 0; i < N; i++) {
			if(i < m) {
				if(records[i] == 2) bIn1++;
				else if(records[i] == 3) cIn1++;
			} else if(i < n) {
				if(records[i] == 1) aIn2++;
				else if(records[i] == 3) cIn2++;
			} else if(records[i] == 1) aIn3++;
		}
		
		out.println((swap12 = Math.min(bIn1, aIn2)) + 
					(swap13 = Math.min(cIn1, aIn3)) + 
					2 * ((leftoverBIn1 = bIn1 - swap12) + cIn1 - swap13) + 
					cIn2 - leftoverBIn1);	// some c's in 2 are moved to 3 when rotating leftover b's in 1
//		System.out.println(m);
//		System.out.println(n);
//		System.out.println(bIn1);
//		System.out.println(cIn1);
//		System.out.println(aIn2);
//		System.out.println(cIn2);
//		System.out.println(aIn3);
//		System.out.println(Math.min(bIn1, aIn2));
//		System.out.println(Math.min(cIn1, aIn3));
//		System.out.println(leftoverBIn1 + cIn1 - Math.min(cIn1, aIn3));
//		System.out.println(cIn2 - leftoverBIn1);
		out.close();
	}
}