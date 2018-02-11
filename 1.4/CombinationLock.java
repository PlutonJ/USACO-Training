/*
ID: plutonj1
LANG: JAVA
TASK: combo
*/

import java.io.*;
import java.util.*;

class combo {
	static String name = "combo";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		input = new StringTokenizer(in.readLine());
		int[] johns = {Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken())};
		input = new StringTokenizer(in.readLine());
		int[] master = {Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken())};
		Set<Combo> combos = new HashSet<Combo>();				// use set to keep track of duplicates
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				for(int k = -2; k <= 2; k++) {
					combos.add(new Combo((johns[0] + i + N) % N, (johns[1] + j + N) % N, (johns[2] + k + N) % N));			// (x + N) % N to wrap around
				}
			}
		}
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				for(int k = -2; k <= 2; k++) {
					combos.add(new Combo((master[0] + i + N) % N, (master[1] + j + N) % N, (master[2] + k + N) % N));			// (x + N) % N to wrap around
				}
			}
		}
		
		out.println(combos.size());
		out.close();
	}
}

class Combo {
	int a, b, c;
	
	Combo(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Override
	public int hashCode() {
		return a * 10000 + b * 100 + c;			// for hash set, multiply by 100's because max a, b, or c is 100
	}
	
	@Override
	public boolean equals(Object o) {
		Combo combo = (Combo)o;
		return a == combo.a && b == combo.b && c == combo.c;		// for hash set
	}
}