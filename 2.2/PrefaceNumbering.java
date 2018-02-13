/*
ID: plutonj1
LANG: JAVA
TASK: preface
*/

import java.io.*;
import java.util.*;

class preface {
	static String name = "preface";
	static PrintWriter out;
	
	static int I = 0, V = 0, X = 0, L = 0, C = 0, D = 0, M = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		for(int i = 1; i <= N; i++) {
			count(i);
		}
		
		if(I > 0) out.println("I " + I);
		if(V > 0) out.println("V " + V);
		if(X > 0) out.println("X " + X);
		if(L > 0) out.println("L " + L);
		if(C > 0) out.println("C " + C);
		if(D > 0) out.println("D " + D);
		if(M > 0) out.println("M " + M);
		out.close();
	}
	
	static void count(int i) {
		int ones = i % 10, tens = (i /= 10) % 10, hundreds = (i /= 10) % 10, thousands = (i /= 10) % 10;
		switch(ones) {
			case 1:
			case 2:
			case 3:
				I += ones;
			break;
			case 4:
				I++;
				V++;
			break;
			case 8:
				I++;
			case 7:
				I++;
			case 6:
				I++;
			case 5:
				V++;
			break;
			case 9:
				I++;
				X++;
			break;
			default:
			break;
		}
		switch(tens) {
			case 1:
			case 2:
			case 3:
				X += tens;
			break;
			case 4:
				X++;
				L++;
			break;
			case 8:
				X++;
			case 7:
				X++;
			case 6:
				X++;
			case 5:
				L++;
			break;
			case 9:
				X++;
				C++;
			break;
			default:
			break;
		}
		switch(hundreds) {
			case 1:
			case 2:
			case 3:
				C += hundreds;
			break;
			case 4:
				C++;
				D++;
			break;
			case 8:
				C++;
			case 7:
				C++;
			case 6:
				C++;
			case 5:
				D++;
			break;
			case 9:
				C++;
				M++;
			break;
			default:
			break;
		}
		M += thousands;
	}
}