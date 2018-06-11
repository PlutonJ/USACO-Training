/*
ID: plutonj1
LANG: JAVA
TASK: butter
*/

// Optimized Floyd-Warshall

import java.io.*;
import java.util.*;

class butter {
	static String name = "butter";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(input.nextToken()), P = Integer.parseInt(input.nextToken()), C = Integer.parseInt(input.nextToken());
		int[] cow = new int[N];
		int[][] adj = new int[P][P];
		for(int[] row : adj) {
			Arrays.fill(row, 1451);
		}
		for(int i = 0; i < N; i++) {
			cow[i] = Integer.parseInt(in.readLine()) - 1;
		}
		for(int i = 0; i < C; i++) {
			input = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(input.nextToken()) - 1, b = Integer.parseInt(input.nextToken()) - 1;
			adj[a][b] = adj[b][a] = Integer.parseInt(input.nextToken());
		}
		for(int k = 0; k < P; k++) {
			for(int i = 0; i < P - 1; i++) {
				if(adj[i][k] <= 1450) {
					for(int j = i + 1; j < P; j++) {
						int d = adj[i][k] + adj[k][j];
						if(d < adj[i][j]) {
							adj[i][j] = adj[j][i] = d;
						}
					}
				}
			}
		}
		int minSum = Integer.MAX_VALUE, sum;
		for(int i = 0; i < P; i++) {
			sum = 0;
			for(int j = 0; j < N; j++) {
				if(i != cow[j]) {
					sum += adj[cow[j]][i];
				}
			}
			if(sum < minSum) {
				minSum = sum;
			}
		}
		
		out.println(minSum);
		out.close();
	}
}