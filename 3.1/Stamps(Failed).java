/*
ID: plutonj1
LANG: JAVA
TASK: stamps
*/

// Times out at test case 10

import java.io.*;
import java.util.*;

class stamps {
	static String name = "stamps";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int K = Integer.parseInt(input.nextToken()), N = Integer.parseInt(input.nextToken());
		int[] v = new int[N];
		input = new StringTokenizer(in.readLine());
		for(int i = 0; i < N; i++) {
			v[i] = Integer.parseInt(input.nextToken());
		}
		
		Map<Integer, Integer> dp = new TreeMap<>();
		dp.put(0, 0);
		for(int z = 0; z < K; z++) {
			for(int i = 0; i < N; i++) {
				List<int[]> changes = new LinkedList<>();
				for(Map.Entry<Integer, Integer> e : dp.entrySet()) {
					if(e.getValue() < K) {
						// supposedly dp.put(change[0], change[1]) but can't do so due to ConcurrentModificationException
						changes.add(new int[] {e.getKey() + v[i], dp.get(e.getKey() + v[i]) == null ? e.getValue() + 1 : Math.min(e.getValue() + 1, dp.get(e.getKey() + v[i]))});
					}
				}
				for(int[] change : changes) {
					dp.put(change[0], change[1]);
				}
			}
		}
		
		int max = 0, current = 0, lastN = 0;
		for(Map.Entry<Integer, Integer> e : dp.entrySet()) {
			//System.out.println(e.getKey() + " : " + e.getValue());
			if(e.getKey() == lastN + 1) {
				current++;
				//System.out.println(current);
				lastN = e.getKey();
			} else if(e.getKey() != 0) {
				max = Math.max(max, current);
				current = 1;
				lastN = e.getKey();
			}
		}
		max = Math.max(max, current);
		
		out.println(max);
		out.close();
	}
}