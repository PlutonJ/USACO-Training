/*
ID: plutonj1
LANG: JAVA
TASK: fence6
*/

// make edges nodes that can be connected on either side with another node
// then perform dijkstra's on all of the neighbors of any edge and find the shortest path
// to get back to the original edge
// while doing dijkstra's, it is restricted to search the nodes that are on the opposite side
// of where the path visits the current node from

import java.io.*;
import java.util.*;

class fence6 {
	static String name = "fence6";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
        Edge[] edges = new Edge[N];
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            int id = Integer.parseInt(input.nextToken()) - 1;
            int len = Integer.parseInt(input.nextToken());
            int N1s = Integer.parseInt(input.nextToken()), N2s = Integer.parseInt(input.nextToken());
            int[] a = new int[N1s];
            input = new StringTokenizer(in.readLine());
            for(int j = 0; j < N1s; j++) {
                a[j] = Integer.parseInt(input.nextToken()) - 1;
            }
            int[] b = new int[N2s];
            input = new StringTokenizer(in.readLine());
            for(int j = 0; j < N2s; j++) {
                b[j] = Integer.parseInt(input.nextToken()) - 1;
            }
            edges[id] = new Edge(id, len, a, b);
        }
        int min = 25500;
        for(Edge edge : edges) {
            for(int e : edge.a) {
                int d;
                if((d = edges[e].dist(edge.id, edge.id)) < min) {
                    //System.out.println(edge.id + ", " + e + ", " + d);
                    min = d;
                }
            }
            for(int e : edge.b) {
                int d;
                if((d = edges[e].dist(edge.id, edge.id)) < min) {
                    //System.out.println(edge.id + ", " + e + ", " + d);
                    min = d;
                }
            }
        }
		
        out.println(min);
		out.close();
	}
}

class Edge {
    static Edge[] edges = new Edge[100];
    static int count = 0;
    
    int id, len;
    int[] a, b;
    
    Edge(int id, int len, int[] a, int[] b) {
        this.id = id;
        this.len = len;
        this.a = a;
        this.b = b;
        edges[id] = this;
        count++;
    }
    
    // dijkstra's
    int dist(int to, int from) {
        int[] dist = new int[count];
        Arrays.fill(dist, 25500);
        boolean[] visited = new boolean[count];
        int[] parent = new int[count];
        Arrays.fill(parent, -1);
        int nodesVisited = 0;
        
        dist[id] = len;
        parent[id] = from;
        while(nodesVisited < count) {
            int n = 0;
            while(visited[n]) {
                n++;
            }
            for(int i = 0; i < count; i++) {
                if(!visited[i] && dist[i] < dist[n]) {
                    n = i;
                }
            }
            visited[n] = true;
            nodesVisited++;
            // only traverse through the nodes on the opposite side
            for(int e : edges[n].getOpposite(parent[n])) {
                if(dist[n] + edges[e].len < dist[e]) {
                    dist[e] = dist[n] + edges[e].len;
                    parent[e] = n;
                }
            }
        }
        /*System.out.println((from + 1) + " - " + (id + 1) + ": ");
        for(int d : dist) {
            System.out.print(d + ", ");
        }
        System.out.println("");*/
        return dist[to];
    }
    
    // return the side without the node that is passed in as the argument
    int[] getOpposite(int from) {
        for(int e : a) {
            if(e == from) {
                return b;
            }
        }
        return a;
    }
}