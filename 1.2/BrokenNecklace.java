/*
ID: plutonj1
LANG: JAVA
TASK: beads
*/

import java.io.*;
import java.util.*;

class beads {
	static String name = "beads";
	
	public static void main(String[] args) throws IOException {
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		
		int N = Integer.parseInt(in.readLine());
		char[] beads = in.readLine().toCharArray();
		char s = beads[0];
		for(int i = 0; i < N; i++) {
			if(beads[i] != s) break;
			if(i == N - 1) {
				out.println(N);
				out.close();
				//System.out.println(N);
				return;
			}
		}
		int[] full = new int[N], back = new int[N];
		int index = N - 1, whiteCount = 0, lastSegment = 0;
		char precedingWhite = 'w', startColor = 'w', color = 'w';
		while(beads[index] == precedingWhite) {
			index--;
		}
		precedingWhite = beads[index];
		index = 0;
		while(beads[index] == startColor) {
			index++;
		}
		startColor = beads[index];
		index = -1;
		for(int i = 0; i < N; i++) {
			if(beads[i] == 'w' && color == 'w') {
				beads[i] = precedingWhite;
				lastSegment++;
			} else {
				if(beads[i] == 'w') {
					whiteCount++;
					beads[i] = color;
				} else if(beads[i] != color) {
					full[index + 1] += whiteCount;
					whiteCount = 0;
					index++;
				} else {
					whiteCount = 0;
				}
				full[index]++;
				back[index]++;
				color = beads[i];
			}
		}
		if(color == startColor) {
			full[0] += lastSegment + full[index];
			back[0] += lastSegment + back[index];
			full[index] = 0;
			back[index] = 0;
		} else {
			full[0] += lastSegment;
			full[index] += lastSegment;
			back[index] += lastSegment;
		}
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < index; i++) {
			if(full[i] + back[(i + 1) % index] > max) {
				max = full[i] + back[(i + 1) % index];
			}
		}
		
		/*for(int i = 0; i < index; i++) {
			System.out.print(full[i] + ", ");
		}
		System.out.print("\n");
		for(int i = 0; i < index; i++) {
			System.out.print(back[i] + ", ");
		}*/
		
		out.println(Math.min(N, max));
		out.close();
	}
}