/*
ID: plutonj1
LANG: JAVA
TASK: contact
*/

// Times out on test 5.
// Absolute stupidness for using kmp algorithm
// Just count the number of times the same string is passed into the constructor of Pattern.

import java.io.*;
import java.util.*;

class contact {
	static String name = "contact";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int A = Integer.parseInt(input.nextToken()), B = Integer.parseInt(input.nextToken()), N = Integer.parseInt(input.nextToken());
		StringBuilder buf = new StringBuilder();
		String next;
		while((next = in.readLine()) != null) {
			buf.append(next);
		}
		String seq = buf.toString();
		int length = seq.length();
		// sort by frequency then length then binary value
		Comparator<Pattern> c = (a, b) -> a.freq == b.freq ? a.pattern.length() == b.pattern.length() ? Integer.parseInt(a.pattern, 2) - Integer.parseInt(b.pattern, 2) : a.pattern.length() - b.pattern.length() : b.freq - a.freq;
		TreeSet<Pattern> patterns = new TreeSet<>(c), ps = new TreeSet<>(c);
		for(int len = A; len <= B; len++) {
			for(int i = 0; i <= length - len; i++) {
				patterns.add(new Pattern(seq.substring(i, i + len)));
			}
		}
		while(patterns.size() > 0) {
			Pattern p = patterns.first();
			patterns.remove(p);
			int i = 0, j = 0;
			while(i < length) {
				if(p.pattern.charAt(j) == seq.charAt(i)) {
					i++;
					j++;
				}
				if(j == p.pattern.length()) {
					p.freq++;
					j = p.lps[j - 1];
				} else if(i < length && p.pattern.charAt(j) != seq.charAt(i)) {
					if(j != 0) {
						j = p.lps[j - 1];
					} else {
						i++;
					}
				}
			}
			ps.add(p);	// to reorder
		}
		
		while(ps.size() > 0 && N --> 0) {
			int freq = ps.first().freq;
			out.println(freq);
			f: while(ps.first().freq == freq) {
				for(int i = 0; i < 5; i++) {
					out.print(ps.first().pattern);
					ps.remove(ps.first());
					if(ps.size() == 0 || ps.first().freq != freq) {
						out.println("");
						break f;
					}
					out.print(" ");
				}
				if(ps.size() > 0 && ps.first().freq == freq) {
					out.println(ps.first().pattern);
					ps.remove(ps.first());
				} else {
					out.println("");
					break;
				}
			}
		}
		out.close();
	}
}

class Pattern {
	static Map<String, int[]> p2lps = new HashMap<>();
	
	String pattern;
	int freq = 0;
	int[] lps;
	
	Pattern(String pattern) {
		this.pattern = pattern;
		if(p2lps.get(pattern) != null) {
			lps = p2lps.get(pattern);
		} else {
			lps = new int[pattern.length()];
			int i = 1, len = 0;
			while(i < lps.length) {
				if(pattern.charAt(i) == pattern.charAt(len)) {
					len++;
					lps[i] = len;
					i++;
				} else {
					if(len != 0) {
						len = lps[len - 1];
					} else {
						lps[i] = len;
						i++;
					}
				}
			}
			p2lps.put(pattern, lps);
		}
	}
	
	@Override
	public int hashCode() {
		return pattern.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Pattern)) {
			return false;
		}
		return pattern.equals(((Pattern)o).pattern);
	}
}