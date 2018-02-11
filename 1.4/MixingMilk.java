/*
ID: plutonj1
LANG: JAVA
TASK: milk
*/

import java.io.*;
import java.util.*;

class milk {
	static String name = "milk";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(input.nextToken()), M = Integer.parseInt(input.nextToken());
		Farmer[] P = new Farmer[M];
		for(int i = 0; i < M; i++) {
			input = new StringTokenizer(in.readLine());
			P[i] = new Farmer(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
		}
		Arrays.sort(P, (a, b) -> a.price - b.price);			// sort farmers into non-descending price order
		int c = 0, i = 0;
		while(N != 0) {
			if(N >= P[i].amount) {								// exhaust farmers with lowest prices
				N -= P[i].amount;
				c += P[i].price * P[i++].amount;
			} else {
				c += P[i].price * N;
				N = 0;
			}
		}
		
		out.println(c);
		out.close();
	}
}

class Farmer {
	int price, amount;
	
	Farmer(int price, int amount) {
		this.price = price;
		this.amount = amount;
	}
}