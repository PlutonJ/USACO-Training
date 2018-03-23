/*
ID: plutonj1
LANG: JAVA
TASK: agrinet
*/

import java.io.*;
import java.util.*;

class agrinet {
	static String name = "agrinet";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		int[][] weight = new int[N][N];
		int wi = 0, wj = 0;
		String next;
		while((next = in.readLine()) != null) {
			input = new StringTokenizer(next);
			while(input.hasMoreTokens()) {
				weight[wi][wj++] = Integer.parseInt(input.nextToken());
				wi += wj / N;
				wj %= N;
			}
		}
		
		// Prim's
		int[] dist = new int[N]/*, src = new int[N]*/;
		Arrays.fill(dist, Integer.MAX_VALUE);
		//Arrays.fill(src, -1);
		boolean[] inTree = new boolean[N];
		int size = 1, cost = 0;
		inTree[0] = true;
		for(int j = 1; j < N; j++) {
			dist[j] = weight[0][j];
			//src[j] = 0;
		}
		while(size < N) {
			int i = -1;
			for(int j = 1; j < N; j++) {
			 	if(!inTree[j] && (i == -1 || dist[j] < dist[i])) {
					i = j;
				}
			}
			size++;
			cost += dist[i];
			inTree[i] = true;
			for(int j = 1; j < N; j++) {
				if(!inTree[j] && dist[j] > weight[i][j]) {
					dist[j] = weight[i][j];
					//src[j] = i;
				}
			}
		}
		
		out.println(cost);
		out.close();
	}
}