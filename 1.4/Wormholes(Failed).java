/*
ID: plutonj1
LANG: JAVA
TASK: wormhole
*/

import java.io.*;
import java.util.*;

class wormhole {
	static String name = "wormhole";
	
	static List<int[]> configs = new LinkedList<int[]>();				// to prevent same pairing with different tokens(e.g. 1, 2, 1, 2 & 2, 1, 2, 1)
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		P[] wormholes = new P[N];
		for(int i = 0; i < N; i++) {
			input = new StringTokenizer(in.readLine());
			wormholes[i] = new P(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
		}
		int count = 0;
		for(int i = 0; i < N - 1; i++) {
			for(int j = i + 1; j < N; j++) {
				int[] taken = new int[N];				// used to be a boolean array thus the name(later figured out tokens needed to distinguish pairs)
				taken[i] = 1;						// set pair token(same token = paired together), 0 = unpaired
				taken[j] = 1;
				count += pair(wormholes, taken, 2);
			
			}
		}
		
		out.println(count);
		out.close();
	}
	
	static int pair(P[] p, int[] taken, int t) {
		int n = taken.length;
		for(int i = 0; i < taken.length; i++) if(taken[i] > 0) n--;		// since 0 = unpaired, if all paired
		if(n == 0) return loop(p, taken) ? 1 : 0;						// return 1 if pairing forms loop
		n = 0;															// variable reuse ;)
		for(int i = 0; i < taken.length - 1; i++) {
			if(taken[i] > 0) continue;									// if paired skip
			for(int j = i + 1; j < taken.length; j++) {
				if(taken[j] > 0) continue;
				taken[i] = t;					// set pair token
				taken[j] = t;
				n += pair(p, taken, t + 1);		// recursion, increment token to distinguish
				taken[i] = 0;					// reset for next pair
				taken[j] = 0;
			}
		}
		return n;
	}
	
	// completely unoptimized brute force ;)
	static boolean loop(P[] p, int[] taken) {
//		for(int j = 0; j < taken.length; j++) {
//			System.out.print(taken[j] + " ");
//		}
//		System.out.print("\n");
//		boolean[] checked = new boolean[taken.length];
//		int i = 0;
//		while(true) {
//			while(i < taken.length && checked[i]) i++;					// set indicator to next unchecked wormhole
//			if(i == taken.length) return false;							// if all checked without returning true
//			int y = p[i].y, minX = Integer.MAX_VALUE, newI = i, orgI = i;
//			do {
//				i = newI;
//				newI = -1;
//				for(int j = 0; j < taken.length; j++) {
//					checked[i] = true;
//					for(int k = 0; k < taken.length; k++) {							// go to connected wormhole via brute force ;)
//						if(taken[k] == taken[i] && k != i) {
//							i = k;
//						}
//					}
//					if(p[j].y == p[i].y && p[j].x > p[i].x && p[j].x < minX) {		// cow goes into nearest wormhole on the right with same y
//						minX = p[j].x;
//						newI = j;
//					}
//				}
//				if(newI == orgI) {
//					/*for(int j = 0; j < taken.length; j++) {
//						System.out.print(taken[j] + " ");
//					}
//					System.out.print("\n");*/
//					if(!checkedConfig(taken)) {
//						/*for(int j = 0; j < taken.length; j++) {
//							System.out.print(taken[j] + " ");
//						}
//						System.out.print("\n");*/
//						configs.add(Arrays.copyOfRange(taken, 0, taken.length));
//						return true;										// if ever goes back to starting point return true
//					}
//				}
//			} while(newI != -1);
//			i = 0;									// reset indicator to search for unchecked wormhole in next iteration
//		}
//		^
//		|
//		|
//		NO
//		JUST CHECK EVERY I
//		LONG LIVE BRUTE FORCING
//
//		for(int i = 0; i < taken.length; i++) {
//			int y = p[i].y, minX = Integer.MAX_VALUE, newI = i, orgI = i;
//			do {
//				i = newI;
//				newI = -1;
//				for(int k = 0; k < taken.length; k++) {			// this should be here instead of being in the j loop
//					if(taken[k] == taken[i] && k != i) {
//						i = k;
//					}
//				}
//				for(int j = 0; j < taken.length; j++) {
//					if(p[j].y == p[i].y && p[j].x > p[i].x && p[j].x < minX) {
//						minX = p[j].x;
//						newI = j;
//					}
//				}
//				if(newI == orgI) {
//					if(!checkedConfig(taken)) {
//						configs.add(Arrays.copyOfRange(taken, 0, taken.length));
//						return true;
//					}
//				}
//			} while(newI != -1);
//		}
//		return false;
//		^
//		|
//		|
//		NVM
//		JUST SPEND 2 MINUTES AND THINK ABOUT HOW TO ACTUALLY DO THIS 
//		INSTEAD OF BEING A LAZY FXCK

//		Literally just cut + pasted the k loop to outside of j loop
		boolean[] checked = new boolean[taken.length];
		int i = 0;
		out: while(true) {
			while(i < taken.length && checked[i]) i++;					// set indicator to next unchecked wormhole
			if(i == taken.length) return false;							// if all checked without returning true
			int y = p[i].y, minX = Integer.MAX_VALUE, newI = i, orgI = i;
			do {
				i = newI;
				newI = -1;
//				checked[i] = true;												// fxck forgot to move this to here and this is probably why it timed out like 7 times in a row
																				// yup exactly fxck
//				for(int k = 0; k < taken.length; k++) {							// go to connected wormhole via brute force ;)
//					if(taken[k] == taken[i] && k != i) {
//						i = k;
//					}
//				}
				for(int j = 0; j < taken.length; j++) {
					if(p[j].y == p[i].y && p[j].x > p[i].x && p[j].x < minX) {		// cow goes into nearest wormhole on the right with same y
						minX = p[j].x;
						newI = j;
					}
				}
				if(newI == -1) break;
				checked[newI] = true;												// nvm this line should be here
				if(newI == orgI) {
					/*for(int j = 0; j < taken.length; j++) {
						System.out.print(taken[j] + " ");
					}
					System.out.print("\n");*/
					if(!checkedConfig(taken)) {
						for(int j = 0; j < taken.length; j++) {
							System.out.print(taken[j] + " ");
						}
						System.out.print("\n");
						configs.add(Arrays.copyOfRange(taken, 0, taken.length));
						return true;										// if ever goes back to starting point return true
					}
				}
				for(int k = 0; k < taken.length; k++) {							// fxck this should be here i think
					if(taken[k] == taken[newI] && k != newI) {
						newI = k;
						break;
					}
				}
				if(checked[newI]) continue out;										// if next stop already checked continue next i
			} while(newI != -1);
			i = 0;									// reset indicator to search for unchecked wormhole in next iteration
		}
	}
	
	// crappy brute forcing ;)
	static boolean checkedConfig(int[] taken) {
		out: for(int[] config : configs) {
			int[] map = new int[taken.length / 2 + 1];						// don't ask
			for(int i = 0; i < taken.length; i++) {
				if(map[taken[i]] == 0) map[taken[i]] = config[i];			// if map not set at taken[i], map it to config[i]
				if(map[taken[i]] != config[i]) continue out;				// if taken[i] mapped and config[i] doesn't correspond, the two configs are different, thus check next config
				if(i == taken.length - 1) {
					/*for(int j = 0; j < taken.length; j++) {
						System.out.print(taken[j] + " ");
					}
					System.out.print("\n");
					for(int j = 0; j < taken.length; j++) {
						System.out.print(config[j] + " ");
					}
					System.out.print("\n");*/
					return true;			// if all mapping correspond, the two configs are the same
				}
			}
		}
		return false;
	}
}

class P {
	int x, y;
	
	P(int x, int y) {
		this.x = x;
		this.y = y;
	}
}