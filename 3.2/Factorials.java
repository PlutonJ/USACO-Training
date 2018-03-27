/*
ID: plutonj1
LANG: JAVA
TASK: fact4
*/

import java.io.*;
import java.util.*;

class fact4 {
	static String name = "fact4";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine()), digit = 1;
		for(int i = 2; i <= N; i++) {
			digit *= i;
			while(digit % 10 == 0) digit /= 10;
			digit %= 1000;
		}
		
		out.println(digit % 10);
		out.close();
	}
}