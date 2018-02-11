/*
ID: plutonj1
LANG: JAVA
TASK: dualpal
*/

import java.io.*;
import java.util.*;

class dualpal {
	static String name = "dualpal";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input = new StringTokenizer(in.readLine());
		
		int N = Integer.parseInt(input.nextToken()), S = Integer.parseInt(input.nextToken());
		int count = 0, i = S + 1;
		while(count < N) {
			int palindromeC = 0;
			StringBuilder d = new StringBuilder("" + i);
			if(d.toString().equals(d.reverse().toString())) palindromeC++;
			for(int B = 2; B < 10; B++) {
				int n = i;
				StringBuilder buf = new StringBuilder();
				while(n > 0) {
					buf.append(n % B);
					n /= B;
				}
				if(buf.toString().equals(buf.reverse().toString())) 
					System.out.println(buf.toString() + ", " + B);
				if(buf.toString().equals(buf.reverse().toString()) && ++palindromeC == 2) {
					out.println(i);
					count++;
					break;
				} 
			}
			i++;
		}
		
		out.close();
	}
}