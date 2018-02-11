/*
ID: plutonj1
LANG: JAVA
TASK: skidesign
*/

import java.io.*;
import java.util.*;

class skidesign {
	static String name = "skidesign";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		int[] hills = new int[N];
		for(int i = 0; i < N; i++) {
			hills[i] = Integer.parseInt(in.readLine());
		}
		Arrays.sort(hills);
		
		int i = 0, j = 17, min = Integer.MAX_VALUE;	// left and right bounds, minimum cost
		while(j <= 100) {					// loop until right bound reaches end
			int cost = 0;
			for(int k = 0; k < N; k++) {			// caculate current cost
				if(hills[k] < i) cost += (hills[k] - i) * (hills[k] - i);
				else if(hills[k] > j) cost += (hills[k] - j) * (hills[k] - j);
			}
			if(cost < min) min = cost;				// replace minimum if current cost is less
			i++;									// push left bound
			j++;									// push right bound
		}
		
		out.println(min);
		out.close();
	}
}