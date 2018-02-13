/*
ID: plutonj1
LANG: JAVA
TASK: holstein
*/

import java.io.*;
import java.util.*;

class holstein {
	static String name = "holstein";
	static PrintWriter out;
	
	static int G;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int V = Integer.parseInt(in.readLine());
		int[] minimum = new int[V];
		input = new StringTokenizer(in.readLine());
		for(int i = 0; i < V; i++) {
			minimum[i] = Integer.parseInt(input.nextToken());
		}
		G = Integer.parseInt(in.readLine());
		int[][] feeds = new int[G][V];
		for(int i = 0; i < G; i++) {
			input = new StringTokenizer(in.readLine());
			for(int j = 0; j < V; j++) {
				feeds[i][j] = Integer.parseInt(input.nextToken());
			}
		}
		
		// since the number of scoops of each type of feed is either 0 or 1, 
		// just iterate through 0 to 2 ** G - 1 and use the individual bits as scoops
		int maxCombination = (int)Math.pow(2, G), count, minCount = G + 1;
		String scoops = null;
		int[] vitamins;
		for(int i = 0; i < maxCombination; i++) {
			count = 0;
			vitamins = new int[V];
			for(int j = 0; j < G; j++) {
				if(((i >> j) & 1) == 1) {
					count++;
					for(int k = 0; k < V; k++) {
						vitamins[k] += feeds[j][k];
					}
				}
			}
			boolean minimumReached = true;
			for(int j = 0; j < V; j++) {
				if(vitamins[j] < minimum[j]) {
					minimumReached = false;
					break;
				}
			}
			if(minimumReached && count < minCount) {
				minCount = count;
				scoops = new Scoops(i).toString();
			}
		}
		
		out.println(minCount + scoops);
		out.close();
	}
}

class Scoops {
	List<Integer> scoops;
	
	Scoops(int i) {
		scoops = new LinkedList<Integer>();
		for(int j = 0; j < holstein.G; j++) {
			if(((i >> j) & 1) == 1) {
				scoops.add(j + 1);
			}
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		for(Integer i : scoops) str = str + " " + i;
		return str;
	}
}