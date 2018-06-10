/*
ID: plutonj1
LANG: JAVA
TASK: butter
*/

import java.io.*;
import java.util.*;

class butter {
	static String name = "butter";
	static BufferedReader in;
	static PrintWriter out;
	
	static int N, P, C;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		N = Integer.parseInt(input.nextToken());
		P = Integer.parseInt(input.nextToken());
		C = Integer.parseInt(input.nextToken());
		int[] cow = new int[P];
		Pasture[] p = new Pasture[P];
		for(int i = 0; i < N; i++) {
			cow[Integer.parseInt(in.readLine()) - 1]++;
		}
		for(int i = 0; i < P; i++) {
			p[i] = new Pasture(i);
		}
		for(int i = 0; i < C; i++) {
			input = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(input.nextToken()) - 1, y = Integer.parseInt(input.nextToken()) - 1, w = Integer.parseInt(input.nextToken());
			p[x].weight[y] = p[y].weight[x] = w;
			p[x].neighbors.add(p[y]);
			p[y].neighbors.add(p[x]);
		}
		
		for(int i = 0; i < P; i++) {
			Pasture pi = p[i];
			pi.dist[i] = 0;
			Queue<Pasture> heap = new PriorityQueue<>((a, b) -> pi.dist[a.i] - pi.dist[b.i]);
			heap.offer(p[i]);
			int n = P;
			while(n --> 0) {
				Pasture p1 = heap.poll();
				for(Pasture p2 : p1.neighbors) {
					if(pi.dist[p1.i] + p1.weight[p2.i] < pi.dist[p2.i]) {
						pi.dist[p2.i] = pi.dist[p1.i] + p1.weight[p2.i];
						heap.remove(p2);
						heap.offer(p2);
					}
				}
			}
		}
		
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < P; i++) {
			int sum = 0;
			for(int j = 0; j < P; j++) {
				for(int k = 0; i != j && k < cow[j]; k++) {
					sum += p[i].dist[j];
				}
			}
			if(sum < min) {
				min = sum;
			}
		}
		
		out.println(min);
		out.close();
	}
}

class Pasture {
	static final int maxDist = 1450 * 225 + 1;
	
	int i;
	int[] weight, dist;
	List<Pasture> neighbors;
	
	Pasture(int i) {
		this.i = i;
		weight = new int[butter.P];
		dist = new int[butter.P];
		Arrays.fill(weight, maxDist);
		Arrays.fill(dist, maxDist);
		neighbors = new ArrayList<>();
	}
}