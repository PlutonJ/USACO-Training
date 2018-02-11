/*
ID: plutonj1
LANG: JAVA
TASK: sprime
*/

import java.io.*;
import java.util.*;

class sprime {
	static String name = "sprime";
	
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine()), desiredLength = (int)Math.pow(10, N - 1);
		Queue<Integer> q = new LinkedList<>();												// use dfs to generate length N superprimes
		q.offer(2);
		q.offer(3);
		q.offer(5);
		q.offer(7);
		while(true) {
			if(q.peek() / desiredLength > 0) break;												// if all numbers in queue are of length N
			int x = q.poll();
			int p = x * 10 + 1;																// primes > 10 can only end in 1, 3, 7, or 9
			if(isPrime(p)) q.offer(p);
			p = x * 10 + 3;
			if(isPrime(p)) q.offer(p);
			p = x * 10 + 7;
			if(isPrime(p)) q.offer(p);
			p = x * 10 + 9;
			if(isPrime(p)) q.offer(p);
		}
		
		for(Integer i : q) out.println(i);
		out.close();
	}
	
	static boolean isPrime(int p) {
		int maxDivisor = (int)Math.sqrt(p);
		//System.out.println(p);
		for(int i = 2; i <= maxDivisor; i++) {
			if(p % i == 0) return false;
		}
		return true;
	}
}