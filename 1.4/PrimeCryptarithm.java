/*
ID: plutonj1
LANG: JAVA
TASK: crypt1
*/

import java.io.*;
import java.util.*;

class crypt1 {
	static String name = "crypt1";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		int[] digits = new int[N];
		boolean[] d = new boolean[10];
		input = new StringTokenizer(in.readLine());
		for(int i = 0; i < N; i++) {
			int n = Integer.parseInt(input.nextToken());
			digits[i] = n;
			d[n] = true;
		}
		Arrays.sort(digits);
		int c = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				for(int k = 0; k < N; k++) {
					int a = digits[i] * 100 + digits[j] * 10 + digits[k];
					for(int x = 0; x < N; x++) {
						if(digits[x] * a >= 1000) break;				// if partial product is 4 digits every digit > digits[x] wouldn't work either
						for(int y = 0; y < N; y++) {
							if(digits[y] * a >= 1000) break;
							if(new Multiplication(a, digits[x] * 10 + digits[y]).works(d)) {	// checks if product is 4 digits and if all the digits of partial products and the product are valid
								//System.out.println(a + ", " + digits[x] * 10 + digits[y]);
								c++;
							}
						}
					}
				}
			}
		}
		
		out.println(c);
		out.close();
	}
}

class Multiplication {
	int a, b;
	
	Multiplication(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	boolean works(boolean[] digits) {
		int n = a * b;
		if(n >= 10000) return false;
		while(n > 0) {
			if(!digits[n % 10]) return false;
			n /= 10;
		}
		n = a * (b % 10);
		while(n > 0) {
			if(!digits[n % 10]) return false;
			n /= 10;
		}
		n = a * (b / 10);
		while(n > 0) {
			if(!digits[n % 10]) return false;
			n /= 10;
		}
		return true;
	}
}