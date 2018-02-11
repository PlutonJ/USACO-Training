/*
ID: plutonj1
LANG: JAVA
TASK: wormhole
*/

import java.io.*;
import java.util.*;

class wormhole {
	static String name = "wormhole";
	
	static int N;		// for easy reference
	static Wormhole[] wormholes;
	static int[] pair;
	static List<int[]> configs = new LinkedList<int[]>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		N = Integer.parseInt(in.readLine());
		wormholes = new Wormhole[N];
		for(int i = 0; i < N; i++) {
			input = new StringTokenizer(in.readLine());
			wormholes[i] = new Wormhole(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
		}
		
		pair = new int[N];			// 0 = unpaired, same num = paired together(1 to N / 2 + 1)
		int count = 0;
		for(int i = 0; i < N - 1; i++) {
			pair[i] = 1;
			for(int j = i + 1; j < N; j++) {
				pair[j] = 1;
				count += count(2);
				pair[j] = 0;		// reset
			}
			pair[i] = 0;		// reset
		}
		
		out.println(count);
		out.close();
	}
	
	static int count(int t) {				// t = next pairing token
		for(int i = 0; i < N; i++) {
			if(pair[i] == 0) break;
			if(i == N - 1) return loop() ? 1 : 0;		// if no unpaired wormhole 
		}
		int count = 0;
		for(int i = 0; i < N - 1; i++) {
			if(pair[i] > 0) continue;			// skip if paired
			pair[i] = t;
			for(int j = i + 1; j < N; j++) {
				if(pair[j] > 0) continue;		// skip if paired
				pair[j] = t;
				count += count(t + 1);
				pair[j] = 0;					// reset
			}
			pair[i] = 0;						// reset
		}
		return count;
	}
	
	static boolean loop() {
		int i;
		boolean[] checked = new boolean[N];
		out: while(true) {
			i = 0;									// reset i to search for unchecked wormhole
			while(i < N && checked[i]) i++;			// go to next unchecked wormhole
			if(i == N) return false;				// if all checked wthout returnng true, return false
			int orgI = i;
			while(true) {
				checked[i] = true;
				int x = wormholes[i].x, y = wormholes[i].y, minX = Integer.MAX_VALUE;
				Wormhole next = null;
				for(Wormhole wormhole : wormholes) {
					if(wormhole.y == y && wormhole.x > x && wormhole.x < minX) {		// cow goes into nearest wormhole on the right with same y
						minX = wormhole.x;
						next = wormhole;
					}
				}
				if(next == null) continue out;		// if right is empty check next unchecked wormhole
				i = 0;
				while(wormholes[i] != next) i++;		// set i to wormhole the cow is walking into
				for(int j = 0; j < N; j++) {
					if(pair[j] == pair[i] && j != i) {
						i = j;						// go to paired wormhole
						break;
					}
				}
				if(i == orgI) {
					if(!checkedConfig()) {
						configs.add(Arrays.copyOfRange(pair, 0, N));
						return true;			// if loops return true
					} else {
						continue out;
					}
				}
			}
		}
	}
	
	static boolean checkedConfig() {
		out: for(int[] config : configs) {
			int[] map = new int[N / 2 + 1];		// map current pairing tokens to checked configs
			for(int i = 0; i < N; i++) {
				if(map[pair[i]] == 0) map[pair[i]] = config[i];	// if unmapped, map
				if(map[pair[i]] != config[i]) continue out;		// if mapped and don't match, check next config
			}
			return true;
		}
		return false;							// if none correspond then unchecked
	}
	
	static void printConfig() {
		for(int i = 0; i < N; i++) {
			System.out.print(pair[i] + " ");
		}
		System.out.print("\n");
	}
}

class Wormhole {
	int x, y;
	
	Wormhole(int x, int y) {
		this.x = x;
		this.y = y;
	}
}