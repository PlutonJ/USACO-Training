/*
ID: plutonj1
LANG: JAVA
TASK: frac1
*/

import java.io.*;
import java.util.*;

class frac1 {
	static String name = "frac1";
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		Set<Fraction> fractions = new TreeSet<Fraction>((a, b) -> a.n * b.d - b.n * a.d);	// comparator uses the numeratorof a - b with denominator a.d * b.d
		fractions.add(Fraction.zero());
		fractions.add(Fraction.one());
		for(int d = 2; d <= N; d++) {
			for(int n = 1; n < d; n++) {
				Fraction f;
				if((f = Fraction.get(n, d)) != null) fractions.add(f);
			}
		}
		for(Fraction f : fractions) out.println(f);
		
		out.close();
	}
}

class Fraction {
	int n, d;
	
	Fraction(int n, int d) {
		this.n = n;
		this.d = d;
	}
	
	@Override
	public String toString() {
		return n + "/" + d;
	}
	
	static Fraction zero() {
		return new Fraction(0, 1);
	}
	
	static Fraction one() {
		return new Fraction(1, 1);
	}
	
	static Fraction get(int n, int d) {			// return null if reducible
		if(n != 1 && d % n == 0) return null;
		return new Fraction(n, d);
	}
}