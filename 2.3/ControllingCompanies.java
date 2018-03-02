/*
ID: plutonj1
LANG: JAVA
TASK: concom
*/

import java.io.*;
import java.util.*;

// brute force

class concom {
	static String name = "concom";
	static BufferedReader in;
	static PrintWriter out;
	
	static int n;
	static Map<Integer, List<Integer>> controlledBy;
	static int[][] own;
	
	public static void main(String[] args) throws IOException {
		//in = new BufferedReader(new InputStreamReader(System.in));
		//out = new PrintWriter(new OutputStreamWriter(System.out));
		in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		n = Integer.parseInt(in.readLine());
		controlledBy = new HashMap<>();
		own = new int[100][100];
		for(int z = 0; z < n; z++) {
			input = new StringTokenizer(in.readLine());
			int i = Integer.parseInt(input.nextToken()) - 1, 
				j = Integer.parseInt(input.nextToken()) - 1, 
				p = Integer.parseInt(input.nextToken());
			own[i][j] += p;
			if(own[i][j] > 50 && i != j) {
				if(controlledBy.get(j) == null) {
					controlledBy.put(j, new LinkedList<Integer>());
				}
				controlledBy.get(j).add(i);
				dfs(i, j);
			}
			if(controlledBy.get(i) != null) {
				for(int k : controlledBy.get(i)) {
					own[k][j] += p;
					if(own[k][j] > 50 && j != k) {
						if(controlledBy.get(j) == null) {
							controlledBy.put(j, new LinkedList<Integer>());
						}
						controlledBy.get(j).add(k);
						dfs(k, j);
					}
				}
			}
		}
		
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				if(own[i][j] > 50 && i != j) {
					out.println((i + 1) + " " + (j + 1));
				}
			}
		}
		out.close();
	}
	
	static void dfs(int i, int j) {
		for(int z = 0; z < 100; z++) {
			own[i][z] += own[j][z];
			if(own[i][z] > 50 && i != z && !controlledBy.get(z).contains(i)) {
				if(controlledBy.get(z) == null) {
					controlledBy.put(z, new LinkedList<Integer>());
				}
				controlledBy.get(z).add(i);
				dfs(i, z);
			}
		}
	}
}