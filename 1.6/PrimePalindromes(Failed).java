/*
ID: plutonj1
LANG: JAVA
TASK: pprime
*/

import java.io.*;
import java.util.*;

// timed out
class pprime {
	static String name = "pprime";
	
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int a = Integer.parseInt(input.nextToken()), b = Integer.parseInt(input.nextToken());
		boolean[] prime = new boolean[b + 1];														// probably not a good idea
																									// since filling  array b will potentially take a lot of time, 
																									// use false as true
		int maxDivisor = (int)Math.sqrt(b), index = 2;
		while(index <= maxDivisor) {
			while(prime[index]) index++;															// find next prime as divisor
			for(int t = index + 1; t <= b; t++) {
				if(t % index == 0) prime[t] = true;													// if divisible by a prime other than self, t != prime
			}
		}
		for(int i = a; i <= b; i++) {
			if(!prime[i]) {																			// if is prime
				StringBuffer buf = new StringBuffer(i);
				if(buf.toString().equals(buf.reverse().toString())) out.println(i);					// if readers the same reversed, i == prime
			}
		}
		
		out.close();
	}
}