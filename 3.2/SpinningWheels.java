/*
ID: plutonj1
LANG: JAVA
TASK: spin
*/

import java.io.*;
import java.util.*;

class spin {
	static String name = "spin";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int[] rot = new int[5], start[] = new int[5][], extent[] = new int[5][];
		for(int i = 0; i < 5; i++) {
			input = new StringTokenizer(in.readLine());
			rot[i] = Integer.parseInt(input.nextToken());
			int W = Integer.parseInt(input.nextToken());
			start[i] = new int[W];
			extent[i] = new int[W];
			for(int j = 0; j < W; j++) {
				start[i][j] = Integer.parseInt(input.nextToken());
				extent[i][j] = Integer.parseInt(input.nextToken());
			}
		}
		
		int time = 0;
		long[] angle = new long[5];
		
		out: for(; time < 360; time++) {
			byte[] light = new byte[360];
			for(int i = 0; i < 5; i++) {
				for(int w = 0; w < start[i].length; w++) {
					int ang = (int)(angle[i] % 360L);
					for(int a = ang + start[i][w]; a <= ang + start[i][w] + extent[i][w]; a++) {
						light[a % 360] |= 1 << i;
					}
				}
			}
			for(byte b : light) {
				if(b == 31) {
					break out;
				}
			}
			for(int i = 0; i < 5; i++) {
				angle[i] += rot[i];
			}
		}
		
		out.println(time == 360 ? "none" : time);
		out.close();
	}
}