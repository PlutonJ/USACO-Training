/*
ID: plutonj1
LANG: JAVA
TASK: camelot
*/

// first brute force bfs knight's path distances between any two nodes
// then brute force through all nodes as the gathering point
// the king either goes to the destination himself, gets carried by a knight, or goes to a nearest knight
// potential failing case: king has to go to a spot where a knight is while the knight is on his way to the destination

import java.io.*;
import java.util.*;

class camelot {
	static String name = "camelot";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int R = Integer.parseInt(input.nextToken()), C = Integer.parseInt(input.nextToken());
		String cheese;
		int[] king = null;
		List<int[]> knights = new ArrayList<>();
		while((cheese = in.readLine()) != null) {
			input = new StringTokenizer(cheese);
			if(king == null) {
				king = new int[] {input.nextToken().charAt(0) - 'A', Integer.parseInt(input.nextToken()) - 1};
			}
			while(king != null && input.hasMoreTokens()) {
				knights.add(new int[] {input.nextToken().charAt(0) - 'A', Integer.parseInt(input.nextToken()) - 1});
			}
		}
		
		// bfs all knight's path distances
		int[] dx = {-2, -2, -1, -1, 1, 1, 2, 2};
		int[] dy = {-1, 1, -2, 2, -2, 2, -1, 1};
		int[][][][] dist = new int[R][C][R][C];
		for(int[][][] i : dist) {
			for(int[][] j : i) {
				for(int[] k : j) {
					Arrays.fill(k, R * C);
				}
			}
		}
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				dist[i][j][i][j] = 0;
			}
		}
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				Queue<int[]> bfs = new LinkedList<>();
				bfs.offer(new int[] {i, j});
				while(bfs.peek() != null) {
					int[] pos = bfs.poll();
					for(int k = 0; k < 8; k++) {
						int x = pos[0] + dx[k], y = pos[1] + dy[k];
						if(x >= 0 && x < R && y >= 0 && y < C && dist[i][j][pos[0]][pos[1]] + 1 < dist[i][j][x][y]) {
							dist[i][j][x][y] = dist[x][y][i][j] = dist[i][j][pos[0]][pos[1]] + 1;
							bfs.offer(new int[] {x, y});
						}
					}
				}
			}
		}
		
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < R; i++) {
			next: for(int j = 0; j < C; j++) {
				int sum = 0, kingDist = Math.max(Math.abs(king[1] - i), Math.abs(king[0] - j));
				int minBringKing = R * C;
				for(int[] knight : knights) {
					sum += dist[knight[1]][knight[0]][i][j];
					int extra;
					// take minimum of current minimum steps to bring king and the steps for the king to get to this knight
					minBringKing = Math.min(minBringKing, Math.max(Math.abs(king[1] - knight[1]), Math.abs(king[0] - knight[0])));
					// take minimum steps to get king if it is not 0 already
					if(minBringKing > 0 && (extra = dist[knight[1]][knight[0]][king[1]][king[0]] + dist[king[1]][king[0]][i][j] - dist[knight[1]][knight[0]][i][j]) < minBringKing) {
						minBringKing = extra;
					}
				}
				// add the minimum of the minimum steps for a knight to bring the king and the steps for the king to get to the destination himself
				sum += Math.min(minBringKing, kingDist);
				if(sum < min) {
					min = sum;
				}
			}
		}
		
		out.println(min);
		out.close();
	}
}