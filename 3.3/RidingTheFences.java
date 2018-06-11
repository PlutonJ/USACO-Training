/*
ID: plutonj1
LANG: JAVA
TASK: fence
*/

// eulerian tour

import java.io.*;
import java.util.*;

class fence {
	static String name = "fence";
	static BufferedReader in;
	static PrintWriter out;
	
	static int[][] adj;
	// use stack since the eulerian cycle algorithm is in reverse
	static Stack<Integer> path;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		adj = new int[500][500];
		int[] degree = new int[500];
		int F = Integer.parseInt(in.readLine());
		// start: first node with odd degree
		int start = -1;
		while(F --> 0) {
			input = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(input.nextToken()), b = Integer.parseInt(input.nextToken());
			adj[a - 1][b - 1]++;
			adj[b - 1][a - 1]++;
			degree[a - 1]++;
			degree[b - 1]++;
		}
		// find odd degree
		while(degree[++start] % 2 == 0) {
			// if no odd degree start at lowest number
			if(start == 499){
				start = 0;
				break;
			}
		}
		
		path = new Stack<>();
		find(start);
		
		while(!path.isEmpty()) {
			out.println(path.pop());
		}
		out.close();
	}
	
	static void find(int node) {
		// traverse in order to get smallest magnitude in base 500 number
		for(int i = 0; i < 500; i++) {
			if(i != node && adj[node][i] > 0) {
				adj[node][i]--;
				adj[i][node]--;
				find(i);
			}
		}
		path.push(node + 1);
	}
}