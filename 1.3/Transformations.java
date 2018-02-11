/*
ID: plutonj1
LANG: JAVA
TASK: transform
*/

import java.io.*;
import java.util.*;

class transform {
	static String name = "transform";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		String[] original = new String[N];
		String[] destination = new String[N];
		for(int i = 0; i < N; i++) {
			original[i] = in.readLine();
		}
		for(int i = 0; i < N; i++) {
			destination[i] = in.readLine();
		}
		Pattern org = new Pattern(original), dest = new Pattern(destination);
		
		int res = 7;
		if(equal(org.rotate(1), dest.pattern)) res = 1;
		else if(equal(org.rotate(2), dest.pattern)) res = 2;
		else if(equal(org.rotate(3), dest.pattern)) res = 3;
		else if(equal(org.reflect(), dest.pattern)) res = 4;
		else if(equal(new Pattern(org.reflect()).rotate(1), dest.pattern) || 
		        equal(new Pattern(org.reflect()).rotate(2), dest.pattern) || 
				equal(new Pattern(org.reflect()).rotate(3), dest.pattern)) res = 5;
		else if(equal(org.pattern, dest.pattern)) res = 6;
		
		out.println(res);
		out.close();
	}
	
	static boolean equal(boolean[] a, boolean[] b) {
		for(int i = 0; i < a.length; i++) {
			if(a[i] != b[i]) return false;
		}
		return true;
	}
}

class Pattern {
	int n;
	boolean[] pattern;
	
	Pattern(String... data) {
		n = data.length;
		pattern = new boolean[n * n];
		for(int i = 0; i < pattern.length; i++) {
			pattern[i] = data[i / n].charAt(i % n) == '@';
		}
	}
	
	Pattern(boolean[] pattern) {
		n = (int)Math.round(Math.sqrt(pattern.length));
		this.pattern = Arrays.copyOfRange(pattern, 0, pattern.length);
	}
	
	boolean[] rotate(int times) {
		boolean[] buf = Arrays.copyOfRange(pattern, 0, pattern.length);
		while(times --> 0) {
			boolean[] bufbuf = Arrays.copyOfRange(buf, 0, buf.length);
			for(int i = 0; i < pattern.length; i++) {
				buf[i] = bufbuf[n * (n - i % n - 1) + i / n];
			}
		}
		return buf;
	}
	
	boolean[] reflect() {
		boolean[] buf = new boolean[pattern.length];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				buf[i * n + j] = pattern[(i + 1) * n - j - 1];
			}
		}
		return buf;
	}
}