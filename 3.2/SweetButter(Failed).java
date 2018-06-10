/*
ID: plutonj1
LANG: JAVA
TASK: butter
*/

// Floyd-Warshall
// Fastest running time for Test 9: 1.008s

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
		int[] cow = new int[P];
		int[][] dist = new int[P][P];
		int oneThousandFourHundredFiftyTimesTwoHundredTwentyFivePlusOne = 1450 * 225 + 1;
		for(int i = 0; i < P; i++) {
			Arrays.fill(dist[i], oneThousandFourHundredFiftyTimesTwoHundredTwentyFivePlusOne);
		}
		for(int i = 0; i < N; i++) {
			cow[Integer.parseInt(in.readLine()) - 1]++;
		}
		for(int i = 0; i < C; i++) {
			input = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(input.nextToken()) - 1, y = Integer.parseInt(input.nextToken()) - 1, w = Integer.parseInt(input.nextToken());
			dist[x][y] = dist[y][x] = w;
		}
		for(int k = 0; k < P; k++) {
			for(int i = 0; i < P - 1; i++) {
				for(int j = i + 1; j < P; j++) {
					int d = dist[i][k] + dist[k][j];
					if(d < dist[i][j]) {
						dist[i][j] = dist[j][i] = d;
					}
				}
			}
		}
		//for(int i = 0; i < P; i++) {
		//	for(int j = 0; j < P; j++) {
		//		System.out.print(dist[i][j] + "\t\t");
		//	}
		//	System.out.println("");
		//}
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < P; i++) {
			int sum = 0;
			for(int j = 0; j < P; j++) {
				for(int k = 0; i != j && k < cow[j]; k++) {
					sum += dist[i][j];
				}
			}
			if(sum < min) {
				min = sum;
			}
		}
		
		out.println(min);
		out.close();
	}
}