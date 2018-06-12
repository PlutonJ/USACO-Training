/*
ID: plutonj1
LANG: JAVA
TASK: shopping
*/

// times out on test case 10

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
		
		Queue<int[]> pq = new PriorityQueue<>((i, j) -> i[5] - j[5]);
		pq.offer(new int[] {wanted[0], wanted[1], wanted[2], wanted[3], wanted[4], wanted[0] * regPrice[0] + wanted[1] * regPrice[1] + wanted[2] * regPrice[2] + wanted[3] * regPrice[3] + wanted[4] * regPrice[4]});
		for(int[] offer : offers) {
			List<int[]> add = new LinkedList<>();
			for(int[] state : pq) {
				while(state[0] >= offer[0] && state[1] >= offer[1] && state[2] >= offer[2] && state[3] >= offer[3] && state[4] >= offer[4]) {
					state = new int[] {state[0] - offer[0], state[1] - offer[1], state[2] - offer[2], state[3] - offer[3], state[4] - offer[4], state[5] - offer[5]};
					//System.out.println(state[5]);
					add.add(state);
				}
			}
			for(int[] state : add) {
				pq.offer(state);
			}
		}
		
		out.println(pq.poll()[5]);
		out.close();
	}
}