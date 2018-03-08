/*
ID: plutonj1
LANG: JAVA
TASK: cowtour
*/

// according to description of the problem, there should be 2 fields(apparently it doesn't work this way and it's dumb as f)
// thought process: 
// 		use floyd-warshall to find all distances
//		then try all pairs of vertices in the two fields a and b
//		find Da = diameter(a), Db = diameter(b)
//		for pair (u in a, v in b), d(i in a, j in b) = dist[i][u] + dist(u, v) + dist[j][v]
//		(since (u -> v) is the only path that connects a and b)
//		and diameter(a -> (u -> v) -> b) = max(Da, Db, max(d(i in a, j in b)))
//		max(d(i in a, j in b)) can be obtained by storing the max in a variable in the for pair (u, v) loop

import java.io.*;
import java.util.*;

class cowtour {
	static String name = "cowtour";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		Pasture[] pastures = new Pasture[N];
		boolean[][] adjacency = new boolean[N][N];
		double[][] dist = new double[N][N];
		for(int i = 0; i < N; i++) {
			input = new StringTokenizer(in.readLine());
			pastures[i] = new Pasture(i, Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
		}
		Set<Pasture> a = new HashSet<>(), b = new HashSet<>();
		for(int i = 0; i < N; i++) {
			String s = in.readLine();
			for(int j = 0; j < N; j++) {
				if(s.charAt(j) == '1') {
					adjacency[i][j] = true;
				}
			}
		}
		Queue<Pasture> overcomplication = new LinkedList<>();
		a.add(pastures[0]);
		overcomplication.offer(pastures[0]);
		while(overcomplication.size() > 0) {
			Pasture p = overcomplication.poll();
			for(int i = 0; i < N; i++) {
				if(adjacency[p.i][i] && !a.contains(pastures[i])) {
					a.add(pastures[i]);
					overcomplication.offer(pastures[i]);
				}
			}
		}
		for(Pasture p : pastures) {
			if(!a.contains(p)) {
				b.add(p);
			}
		}
		// f-w starts
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(adjacency[i][j]) {
					int dx = pastures[j].x - pastures[i].x, dy = pastures[j].y - pastures[i].y;
					dist[i][j] = Math.sqrt(dx * dx + dy * dy);
				} else if(i != j) {
					dist[i][j] = Double.POSITIVE_INFINITY;
				}
			}
		}
		for(int k = 0; k < N; k++) {
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}
		// f-w ends
		double min = Double.POSITIVE_INFINITY;
		for(Pasture u : a) {
			for(Pasture v : b) {
				double duv = Math.sqrt((u.x - v.x) * (u.x - v.x) + (u.y - v.y) * (u.y - v.y));
				double max = 0;
				for(Pasture i : a) {
					for(Pasture j : b) {
						max = Math.max(max, dist[i.i][u.i] + duv + dist[v.i][j.i]);
						//System.out.println("i -> u: " + dist[i.i][u.i]);
						//System.out.println("v -> j: " + dist[v.i][j.i]);
					}
				}
				min = Math.min(min, max);
			}
		}
		
		System.out.println(min);
		out.println(new java.text.DecimalFormat("#0.000000").format(min));
		out.close();
	}
}

class Pasture {
	int i, x, y;
	
	Pasture(int i, int x, int y) {
		this.i = i;
		this.x = x;
		this.y = y;
	}
}