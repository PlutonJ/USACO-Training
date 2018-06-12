/*
ID: plutonj1
LANG: JAVA
TASK: shopping
*/

// 5-dimensional dp

import java.io.*;
import java.util.*;

class shopping {
	static String name = "shopping";
	static BufferedReader in;
	static PrintWriter out;
	
	static int[] map;
	static int[] wanted;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int s = Integer.parseInt(in.readLine());
		int[][] offersRaw = new int[s][];
		for(int i = 0; i < s; i++) {
			input = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(input.nextToken());
			offersRaw[i] = new int[2 * n + 1];
			for(int j = 0; j < n; j++) {
				offersRaw[i][2 * j] = Integer.parseInt(input.nextToken());
				offersRaw[i][2 * j + 1] = Integer.parseInt(input.nextToken());
			}
			offersRaw[i][2 * n] = Integer.parseInt(input.nextToken());
		}
		map = new int[999];
		Arrays.fill(map, -1);
		wanted = new int[5];
		int[] regPrice = new int[5];
		List<int[]> offers = new LinkedList<>();
		int b = Integer.parseInt(in.readLine());
		for(int i = 0; i < b; i++) {
			input = new StringTokenizer(in.readLine());
			int c = Integer.parseInt(input.nextToken()) - 1, k = Integer.parseInt(input.nextToken()), p = Integer.parseInt(input.nextToken());
			// map item id's from [1, 999] to [0, 4]
			map[c] = i;
			wanted[map[c]] = k;
			regPrice[map[c]] = p;
		}
		// convert raw offers to using the mapped id's and convert the reduced price to amount saved using the offer
		next: for(int i = 0; i < s; i++) {
			int[] offer = new int[6];
			for(int j = 0; j < offersRaw[i].length / 2; j++) {
				int k;
				// if id not mapped, the offer is unusable
				if((k = map[offersRaw[i][2 * j] - 1]) == -1) {
					continue next;
				}
				offer[k] = offersRaw[i][2 * j + 1];
			}
			offer[5] = offer[0] * regPrice[0] + offer[1] * regPrice[1] + offer[2] * regPrice[2] + offer[3] * regPrice[3] + offer[4] * regPrice[4] - offersRaw[i][offersRaw[i].length - 1];
			//System.out.println(offer[0] + ", " + offer[1] + ", " + offer[2] + ", " + offer[3] + ", " + offer[4] + ", " + offer[5]);
			offers.add(offer);
		}
		
		int[][][][][] dp = new int[wanted[0] + 1][wanted[1] + 1][wanted[2] + 1][wanted[3] + 1][wanted[4] + 1];
		for(int[][][][] i : dp) {
			for(int[][][] j : i) {
				for(int[][] k : j) {
					for(int[] l : k) {
						Arrays.fill(l, Integer.MAX_VALUE);
					}
				}
			}
		}
		int min = dp[0][0][0][0][0] = wanted[0] * regPrice[0] + wanted[1] * regPrice[1] + wanted[2] * regPrice[2] + wanted[3] * regPrice[3] + wanted[4] * regPrice[4];
		for(int[] offer : offers) {
			for(int i = 0; i <= wanted[0]; i++) {
				for(int j = 0; j <= wanted[1]; j++) {
					for(int k = 0; k <= wanted[2]; k++) {
						for(int l = 0; l <= wanted[3]; l++) {
							for(int m = 0; m <= wanted[4]; m++) {
								if(dp[i][j][k][l][m] != Integer.MAX_VALUE && i + offer[0] <= wanted[0] && j + offer[1] <= wanted[1] && k + offer[2] <= wanted[2] && l + offer[3] <= wanted[3] && m + offer[4] <= wanted[4]) {
									int p = dp[i + offer[0]][j + offer[1]][k + offer[2]][l + offer[3]][m + offer[4]] = Math.min(dp[i + offer[0]][j + offer[1]][k + offer[2]][l + offer[3]][m + offer[4]], dp[i][j][k][l][m] - offer[5]);
									if(p < min){
										min = p;
									}
								}
							}
						}
					}
				}
			}
		}
		
		out.println(min);
		out.close();
	}
}