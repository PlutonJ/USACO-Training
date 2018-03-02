/*
ID: plutonj1
LANG: JAVA
TASK: money
*/

import java.io.*;
import java.util.*;

class money {
	static String name = "money";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		//BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		//out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int V = Integer.parseInt(input.nextToken()), N = Integer.parseInt(input.nextToken());
		int[] v = new int[V];
		input = new StringTokenizer(in.readLine());
		for(int i = 0; i < V; i++) {
			v[i] = Integer.parseInt(input.nextToken());
		}
		Map<Integer, Integer> dp = new HashMap<>();
		List<int[]> changes;
		dp.put(0, 1);
		for(int i = 0; i < V; i++) {
			int n = N / v[i];
			for(int j = 1; j <= n; j++) {
				changes = new LinkedList<>();
				for(Map.Entry<Integer, Integer> p : dp.entrySet()) {
					if(p.getKey() < N) {
						int key = p.getKey();
						Integer get = dp.get(key + v[i]);
						changes.add(new int[] {key + v[i], get == null ? 0 : get + p.getValue()});
					}
				}
				for(int[] change : changes) {
					dp.put(change[0], change[1]);
					System.out.println(change[0] + ", " + change[1]);
				}
			}
		}
		
		if(dp.get(N) == null) out.println(0);
		else out.println(dp.get(N));
		out.close();
	}
}