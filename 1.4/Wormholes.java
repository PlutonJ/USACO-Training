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
	//static int[] pair;			// use Wormhole.pairIndex instead
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
		for(Wormhole a : wormholes) {
			int minX = Integer.MAX_VALUE;
			Wormhole right = null;
			for(Wormhole b : wormholes) {
				if(b.y == a.y && b.x > a.x && b.x < minX) {
					minX = b.x;
					right = b;
				}
			}
			a.right = right;
		}
		
//		int count = 0;
//		for(int i = 0; i < N - 1; i++) {
//			for(int j = i + 1; j < N; j++) {
//				wormholes[i].pairIndex = j;
//				wormholes[j].pairIndex = i;
//				count += count();
//				wormholes[j].pairIndex = -1;		// reset
//			}
//			wormholes[i].pairIndex = -1;		// reset
//		}
		
		out.println(count());
		out.close();
	}
	
	static int count() {				// t = next pairing token
		int i;
		for(i = 0; i < N; i++) {
			if(wormholes[i].pairIndex == -1) break;
			if(i == N - 1) return loop() ? 1 : 0;		// if no unpaired wormhole 
		}
		int count = 0;
		for(int j = i + 1; j < N; j++) {
			if(wormholes[j].pairIndex >= 0) continue;		// skip if paired
			wormholes[i].pairIndex = j;
			wormholes[j].pairIndex = i;
			count += count();
			wormholes[i].pairIndex = wormholes[j].pairIndex = -1;					// reset
		}
		return count;
	}
	
/*	static boolean loop() {
		int i;
		boolean[] checked = new boolean[N];
		out: while(true) {
			i = 0;									// reset i to search for unchecked wormhole
			while(i < N && checked[i]) i++;			// go to next unchecked wormhole
			if(i == N) return false;				// if all checked without returnng true, return false
			int orgI = i;
			while(true) {
				checked[i] = true;
//				int x = wormholes[i].x, y = wormholes[i].y, minX = Integer.MAX_VALUE;
//				Wormhole next = null;
//				for(Wormhole wormhole : wormholes) {
//					if(wormhole.y == y && wormhole.x > x && wormhole.x < minX) {		// cow goes into nearest wormhole on the right with same y
//						minX = wormhole.x;
//						next = wormhole;
//					}
//				}
				Wormhole next = wormholes[i].right;
				if(next == null) continue out;		// if right is empty check next unchecked wormhole
				i = 0;
				while(wormholes[i] != next) i++;		// set i to wormhole the cow is walking into
//				for(int j = 0; j < N; j++) {
//					if(pair[j] == pair[i] && j != i) {
//						i = j;						// go to paired wormhole
//						break;
//					}
//				}									// use Wormhole.pairIndex instead of brute forcing
				i = wormholes[i].pairIndex;
				if(i == orgI) {
					if(!checkedConfig()) {
						saveConfig();
						return true;			// if loops return true
					} else {
						continue out;
					}
				}
			}
		}
	}*/
	
	static boolean loop() {
		//if(checkedConfig()) return false;
		for(Wormhole w : wormholes) {
			Wormhole next = w;
			for(int i = 0; i < N; i++) {
				next = wormholes[next.pairIndex].right;
				if(next == null) break;
			}
			if(next != null) {
				//saveConfig();
				return true;
			}
		}
		return false;
	}
	
	static boolean checkedConfig() {
		out: for(int[] config : configs) {
			for(int i = 0; i < N; i++) {
				if(wormholes[i].pairIndex != config[i]) continue out;		// if pairing doesn't match, check next config
			}
			return true;
		}
		return false;							// if none matches then unchecked
	}
		
	static void saveConfig() {
		int[] config = new int[N];
		for(int i = 0; i < N; i++) {
			config[i] = wormholes[i].pairIndex;
		}
		configs.add(config);
	}
	
	static void printConfig() {
		for(int i = 0; i < N; i++) {
			System.out.print(wormholes[i].pairIndex + " ");
		}
		System.out.print("\n");
	}
}

class Wormhole {
	int x, y, pairIndex = -1;
	Wormhole right = null;
	
	Wormhole(int x, int y) {
		this.x = x;
		this.y = y;
	}
}