/*
ID: plutonj1
LANG: JAVA
TASK: prefix
*/

import java.io.*;
import java.util.*;

class prefix {
	static String name = "prefix";
	static PrintWriter out;
	
	static char[] S;
	static char[][]P = new char[200][0];
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		String line;
		int i = 0;
		while(!(line = in.readLine()).equals(".")) {
			input = new StringTokenizer(line);
			while(input.hasMoreElements()) {
				P[i++] = input.nextToken().toCharArray();
			}
		}
		reducePrimitives();
		for(i = 0; i < 200 && P[i].length > 0; i++) {
			System.out.println(new String(P[i]));
		}
		StringBuilder sBuilder = new StringBuilder();
		while((line = in.readLine()) != null && !line.equals("")) {
			sBuilder.append(line);
		}
		S = sBuilder.toString().toCharArray();
		
		int[][] dp = new int[2][S.length + 1];				// (i, j) = (iteration index % 2, index), dp[i][j] = end index + 1(0 = not reached)
		boolean[] checked = new boolean[S.length + 1];
		i = 0;
		dp[0][S.length] = S.length;
		out: while(true) {
			dp[(i + 1) % 2] = new int[S.length + 1];
			boolean anyReachable = false;
			for(int j = 0; j <= S.length; j++) {
				if(dp[i % 2][j] > 0) {					// if reached
					checked[j] = true;
					for(char[] primitive : P) {
						int index;
						if((index = getNextIndex(j, primitive)) != -1) {
//							System.out.println(index);
							anyReachable = true;
							dp[(i + 1) % 2][index] = Math.max(dp[i % 2][index], dp[i % 2][j]);		// Math.max needed? or just dp[i % 2][j]
							if(index == 0) {
								i++;
								break out;
							}
						}
					}
				}
			}
			if(!anyReachable) {
				int index = S.length - 1;
				while(checked[index]) index--;
				if(index > 0) {
					dp[(i + 1) % 2][index] = index;
				} else {
					i++;
					break;
				}
			}
//			System.out.print("dp: ");
//			for(int j = 0; j <= S.length; j++) {
//				System.out.print(dp[(i + 1) % 2][j] + " ");
//			}
//			System.out.println("");
//			System.out.print("ch: ");
//			for(int j = 0; j <= S.length; j++) {
//				System.out.print((checked[j] ? 1 : 0) + " ");
//			}
//			System.out.println("");
			i++;
		}
		
		out.println(dp[i % 2][0]);
		out.close();
	}
	
	static void reducePrimitives() {
		int i = 0, iLen, jLen;
		while(i < 200 && (iLen = P[i].length) > 0) {		// while not the end of primitives(length 0 means no more primitives)
			int j = i + 1;
			while(j < 200 && (jLen = P[j].length) > 0) {	// while not the end of primitives
				if(iLen % jLen == 0) {			// if length of P[i] is a multiple of length of P[j]
					boolean reduce = true;
					for(int k = 0; k < iLen; k++) {
						if(P[i][k] != P[j][k % jLen]) {	// if doesnt match then not P[i] is not a repetition of P[j]
							reduce = false;
							break;
						}
					}
					if(reduce) {				// if P[i] is a repetition of P[j], delete P[i]
						int k = i + 1;
						while(k < 200 && P[k].length > 0) {		// while not the end of primitives
							P[k - 1] = P[k++];	// overwrite previous primitive starting from index i + 1
						}
					}
				} else if(jLen % iLen == 0) {
					boolean reduce = true;
					for(int k = 0; k < jLen; k++) {
						if(P[j][k] != P[i][k % iLen]) {	// if doesnt match then not P[j] is not a repetition of P[i]
							reduce = false;
							break;
						}
					}
					if(reduce) {				// if P[j] is a repetition of P[i], delete P[j]
						int k = j + 1;
						while(k < 200 && P[k].length > 0) {		// while not the end of primitives
							P[k - 1] = P[k++];	// overwrite previous primitive starting from index j + 1
						}
					}
				}
				j++;
			}
			i++;
		}
	}
	
	static int getNextIndex(int i, char[] primitive) {
		int len = primitive.length;
		if(len > 0 && i - len >= 0) {
			for(int j = 1; j <= len; j++) {
				if(S[i - j] != primitive[len - j]) return -1;
			}
			return i - len;
		}
		return -1;
	}
}