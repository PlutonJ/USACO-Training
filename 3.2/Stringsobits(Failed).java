/*
ID: plutonj1
LANG: JAVA
TASK: kimbits
*/

import java.io.*;
import java.util.*;

class kimbits {
	static String name = "kimbits";
	static BufferedReader in;
	static PrintWriter out;
	
	static List<Integer> ns;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(input.nextToken()), L = Integer.parseInt(input.nextToken()), I = Integer.parseInt(input.nextToken());
		ns = new ArrayList<>();
		ns.add(0);
		ns.add(1);
		for(int i = 1; i < N; i++) {
			int b = (int)Math.pow(2, i);
			for(int j = 0; j <= Math.min(i, L - 1); j++) {
				gen(j, 0, b);
			}
			if(ns.size() > I) {
				break;
			}
		}
		Collections.sort(ns);
		
		int n = ns.get(I - 1);
		String result = "00000000000000000000000000000000" + Integer.toBinaryString(n);
		out.println(result.substring(result.length() - N));
		out.close();
	}
	
	static void gen(int l, int j, int current) {
		if(j < l) {
			int buf = current;
			int d = 1;
			while(buf % 2 == 0) {
				gen(l, j + 1, current + d);
				d *= 2;
				buf /= 2;
			}
		} else {
			//System.out.println(Integer.toBinaryString(current));
			ns.add(current);
		}
	}
}