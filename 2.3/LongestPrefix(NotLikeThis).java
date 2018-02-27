/*
ID: plutonj1
LANG: JAVA
TASK: prefix
*/

import java.io.*;
import java.util.*;

// dfs

class prefix {
	static String name = "prefix";
	static PrintWriter out;
	
	static List<char[]> P = new LinkedList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		String line;
		while(!(line = in.readLine()).equals(".")) {
			input = new StringTokenizer(line);
			while(input.hasMoreElements()) {
				P.add(input.nextToken().toCharArray());
			}
		}
		for(int i = 0; i < P.size(); i++) {
			char[] p = P.get(i);
			if(longestPrefix(p, 0, p) == p.length) {	// if primitive can be formed by combining other primitives
				P.remove(i--);						// remove primitive to reduce computation
			}
		}
		StringBuilder sBuilder = new StringBuilder();
		while((line = in.readLine()) != null && !line.equals("")) {
			sBuilder.append(line);
		}
		
		out.println(longestPrefix(sBuilder.toString().toCharArray(), 0, null));
		out.close();
	}
	
	static int longestPrefix(char[] s, int start, char[] exclude) {	// parameters: string, starting index, excluding primitive
		int max = 0;
		for(char[] primitive : P) {
			if(primitive == exclude) continue;
			if(fits(s, start, primitive)) {
				max = Math.max(max, primitive.length + longestPrefix(s, start + primitive.length, exclude));
			}
		}
		return max;
	}
	
	static boolean fits(char[] s, int start, char[] primitive) {
		int i = 0;
		for(char c : primitive) {
			if(start + i >= s.length || s[start + i++] != c) return false;	// return false if too long or doesnt match
		}
		return true;
	}
}