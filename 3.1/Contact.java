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
		while((next = in.readLine()) != null && next.length() > 0) {
			buf.append(next);
		}
		String seq = buf.toString();
		int length = seq.length();
		// sort by frequency descending then length ascending then binary value ascending
		TreeSet<Pattern> ps = new TreeSet<>((a, b) -> Pattern.freqs.get(a.p) == Pattern.freqs.get(b.p) ? a.p.length() == b.p.length() ? Integer.parseInt(a.p, 2) - Integer.parseInt(b.p, 2) : a.p.length() - b.p.length() : Pattern.freqs.get(b.p) - Pattern.freqs.get(a.p));
		for(int len = A; len <= B; len++) {
			for(int i = 0; i <= length - len; i++) {
				Pattern p = new Pattern(seq.substring(i, i + len));
				ps.remove(p);	// shit happens here
				ps.add(p);
			}
		}
		
		while(ps.size() > 0 && N --> 0) {
			int freq = Pattern.freqs.get(ps.first().p);
			out.println(freq);
			f: while(Pattern.freqs.get(ps.first().p) == freq) {
				for(int i = 0; i < 5; i++) {
					out.print(ps.pollFirst().p);
					if(ps.size() == 0 || Pattern.freqs.get(ps.first().p) != freq) {
						out.println("");
						break f;
					}
					out.print(" ");
				}
				if(ps.size() > 0 && Pattern.freqs.get(ps.first().p) == freq) {
					out.println(ps.pollFirst().p);
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
	static Map<String, Integer> freqs = new HashMap<>();
	
	String p;
	
	Pattern(String p) {
		this.p = p;
		if(freqs.get(p) != null) {
			freqs.put(p, freqs.get(p) + 1);
		} else {
			freqs.put(p, 1);
		}
	}
	
	@Override
	public int hashCode() {
		return p.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Pattern)) {
			return false;
		}
		return p.equals(((Pattern)o).p);
	}
}