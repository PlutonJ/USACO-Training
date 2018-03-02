/*
ID: plutonj1
LANG: JAVA
TASK: ttwo
*/

import java.io.*;
import java.util.*;

class ttwo {
	static String name = "ttwo";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		//in = new BufferedReader(new InputStreamReader(System.in));
		//out = new PrintWriter(new OutputStreamWriter(System.out));
		in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		final int[][] move = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
		boolean[][] forest = new boolean[10][10];	// true == obstacle
		int fx = -1, fy = -1, fd = 0, cx = -1, cy = -1, cd = 0;	// farmer x(vertical), y(horizontal), direction(0 == north, clockwise), cow x, y, direction
		int ifx = -1, ify = -1, icx = -1, icy = -1;		// initial positions
		for(int i = 0; i < 10; i++) {
			char[] line = in.readLine().toCharArray();
			for(int j = 0; j < 10; j++) {
				switch(line[j]) {
					case 'F':
						fx = ifx = i;
						fy = ify = j;
					break;
					case 'C':
						cx = icx = i;
						cy = icy = j;
					break;
					case '*':
						forest[i][j] = true;
					break;
				}
			}
		}
		
		int count = 0;
		while(!(fx == cx && fy == cy)) {
			if(fx + move[fd][0] < 0 || fx + move[fd][0] >= 10 ||
			   fy + move[fd][1] < 0 || fy + move[fd][1] >= 10 ||
			   forest[fx + move[fd][0]][fy + move[fd][1]]) {
				fd = (fd + 1) % 4;
			} else {
				fx += move[fd][0];
				fy += move[fd][1];
			}
			if(cx + move[cd][0] < 0 || cx + move[cd][0] >= 10 ||
			   cy + move[cd][1] < 0 || cy + move[cd][1] >= 10 ||
			   forest[cx + move[cd][0]][cy + move[cd][1]]) {
				cd = (cd + 1) % 4;
			} else {
				cx += move[cd][0];
				cy += move[cd][1];
			}
			if(fx == ifx && fy == ify && fd == 0 && cx == icx && cy == icy && cd == 0 || count > 200) {
				out.println(0);
				out.close();
				return;
			}
			count++;
		}
		
		out.println(count);
		out.close();
	}
}