/*
ID: plutonj1
LANG: JAVA
TASK: fracdec
*/

import java.io.*;
import java.util.*;

class fracdec {
	static String name = "fracdec";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(input.nextToken()), D = Integer.parseInt(input.nextToken());
		StringBuilder buf = new StringBuilder("" + N / D);
		buf.append('.');
		int fPartIndex = buf.length();
		N = N % D;
		Map<Integer, Integer> remainders = new HashMap<>();
		int index = 0;
		while(N != 0) {
			int r = N;
			N *= 10;
			buf.append(N / D);
			N %= D;
			if(remainders.get(r) != null) {			// if the same remainder appears again
				buf.insert(fPartIndex + remainders.get(r), '(');
				buf.setCharAt(buf.length() - 1, ')');
				break;
			}
			remainders.put(r, index++);				// record the index for this remainder
		}
		if(buf.charAt(buf.length() - 1) == '.') {
			buf.append(0);
		}
		
		int i = 0;
		String output = buf.toString();
		while(i < output.length()) {
			out.println(output.substring(i, Math.min(output.length(), i += 76)));
		}
		out.close();
	}
}