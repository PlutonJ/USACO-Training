/*
ID: plutonj1
LANG: JAVA
TASK: comehome
*/

// dijkstra's from source to all pastures with cow

import java.io.*;
import java.util.*;

class comehome {
	static String name = "comehome";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int P = Integer.parseInt(in.readLine());
		Pasture[] pastures = new Pasture[52];
		int[] dist = new int[52];
		Arrays.fill(dist, Integer.MAX_VALUE);
		boolean[] visited = new boolean[52];
		//int[] parent = new int[26];
		//Arrays.fill(parent, -1);
		int[][] weight = new int[52][52];
		for(int i = 0; i < 52 * 52; i++) {
			weight[i / 52][i % 52] = Integer.MAX_VALUE;
		}
		for(int i = 0; i < P; i++) {
			input = new StringTokenizer(in.readLine());
			char a = input.nextToken().charAt(0);
			if(pastures[i(a)] == null) {
				pastures[i(a)] = new Pasture(a);
			}
			char b = input.nextToken().charAt(0);
			if(pastures[i(b)] == null) {
				pastures[i(b)] = new Pasture(b);
			}
			weight[i(a)][i(b)] = Math.min(weight[i(a)][i(b)], Integer.parseInt(input.nextToken()));
			weight[i(b)][i(a)] = weight[i(a)][i(b)];
		}
		int graphSize = 0, nodeVisited = 0;
		for(int i = 0; i < 52; i++) {
			if(pastures[i] != null) {
				graphSize++;
			}
		}
		dist[25] = 0;
		while(nodeVisited < graphSize) {
			int i = -1;
			for(int j = 0; j < 52; j++) {
				if(pastures[j] != null && !visited[j] && (i == -1 || dist[j] < dist[i])) {
					i = j;
				}
			}
			visited[i] = true;
			for(int j = 0; j < 52; j++) {
				if(pastures[j] != null && i != j && weight[i][j] <= 1000) {
					if(dist[i] + weight[i][j] < dist[j]) {
						dist[j] = dist[i] + weight[i][j];
						//parent[j] = i;
					}
				}
			}
			nodeVisited++;
		}
		int min = Integer.MAX_VALUE;
		char c = ' ';
		for(int i = 0; i < 25; i++) {
			if(pastures[i] != null && dist[i] < min) {
				min = dist[i];
				c = pastures[i].c;
			}
		}
		
		out.println(c + " " + min);
		out.close();
	}
	
	static int i(char c) {
		return c - 'A' - (c >= 'a' ? 6 : 0);
	}
}

class Pasture {
	char c;
	
	Pasture(char c) {
		this.c = c;
	}
}