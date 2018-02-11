/*
ID: plutonj1
LANG: JAVA
TASK: ariprog
*/

import java.io.*;
import java.util.*;

class ariprog {
	static String name = "ariprog";
	
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine()), M = Integer.parseInt(in.readLine());
		boolean[] bisquares = new boolean[M * M * 2 + 1];
		for(int p = 0; p <= M; p++) {
			for(int q = p; q <= M; q++) {
				bisquares[p * p + q * q] = true;
			}
		}
		Set<Seq> ans = new TreeSet<Seq>((a, b) -> a.b - b.b == 0 ? a.a - b.a : a.b - b.b);
		int max = M * M * 2, maxB = max / (N - 1);
		for(int b = 1; b <= maxB; b++) {
			next: for(int a = 0; a < bisquares.length; a++) {
				if(bisquares[a]) {
					for(int i = 1; i < N; i++) {
						if(a + b * i > max || !bisquares[a + b * i]) continue next;
					}
					ans.add(new Seq(a, b));
				}
			}
		}
		
		if(ans.size() == 0) out.println("NONE");
		else for(Seq seq : ans) {
			out.println(seq);
		}
		out.close();
	}
}

class Seq {
	int a, b;
	
	Seq(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		return a + " " + b;
	}
}