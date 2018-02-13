/*
ID: plutonj1
LANG: JAVA
TASK: runround
*/

import java.io.*;
import java.util.*;

class runround {
	static String name = "runround";
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		long M = Long.parseLong(in.readLine());
		while(!runround(++M)) {}
		
		out.println(M);
		out.close();
	}
	
	static boolean runround(long n) {
		String s = "" + n;
		int i = 0, l = s.length(), c = l;		// index, length, counter
		boolean[] checked = new boolean[l];		// to see if all digits are visited
		boolean[] used = new boolean[10];		// to see if digits are unique
		while(c --> 0) {
			checked[i] = true;
			int d = (s.charAt(i) - '0');		// numeric value of digit
			if(d == 0) return false;			// has to benonzero
			if(used[d]) return false;			// has to be unique
			used[d] = true;
			i = (i + d) % l;					// run around
		}
		for(boolean b : checked) if(!b) return false;
		return i == 0;							// return true if back to beginning
	}
}