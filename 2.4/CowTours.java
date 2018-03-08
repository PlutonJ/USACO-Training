/*
ID: plutonj1
LANG: JAVA
TASK: cowtour
*/

// For the probability of this code passing test case 5, see Schrodinger's cat

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
		for(int i = 0; i < N; i++) {
			String s = in.readLine();
			for(int j = 0; j < N; j++) {
				if(s.charAt(j) == '1') {
					adjacency[i][j] = true;
				}
			}
		}
		List<Set<Pasture>> fields = new LinkedList<>();
		boolean[] visited = new boolean[N];
		int nextPas = 0;
		while(nextPas < N) {
			Set<Pasture> f = new HashSet<>();
			fields.add(f);
			Queue<Pasture> q = new LinkedList<>();
			visited[nextPas] = true;
			f.add(pastures[nextPas]);
			q.offer(pastures[nextPas]);
			while(q.size() > 0) {
				Pasture p = q.poll();
				for(int i = 0; i < N; i++) {
					if(adjacency[p.i][i] && !f.contains(pastures[i]) && !visited[i]) {
						visited[i] = true;
						f.add(pastures[i]);
						q.offer(pastures[i]);
					}
				}
			}
			while(nextPas < N && visited[nextPas]) {
				nextPas++;
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
		double diameter = 0;
		for(Set<Pasture> field : fields) {
			for(Pasture a : field) {
				for(Pasture b : field) {
					diameter = Math.max(diameter, dist[a.i][b.i]);
				}
			}
			//System.out.println("Size = " + field.size());
			//System.out.println("Diameter = " + diameter);
		}
		// f-w ends
		double min = Double.POSITIVE_INFINITY;
		for(int k = 0; k < fields.size() - 1; k++) {
			Set<Pasture> a = fields.get(k);
			for(int l = k + 1; l < fields.size(); l++) {
				Set<Pasture> b = fields.get(l);
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
						};
						min = Math.min(min, max);
					}
				}
			}
		}
		min = Math.max(min, diameter);
		
		//System.out.println(min);
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