/*
ID: plutonj1
LANG: JAVA
TASK: milk6
*/

// delete each edge from graph in the order of highest -> lowest weight then lowest -> highest index to determine minimum cut

import java.io.*;
import java.util.*;

class milk6 {
	static String name = "milk6";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(input.nextToken()), M = Integer.parseInt(input.nextToken());
        int[][] c = new int[N][N], f = new int[N][N];
        List<Edge> edges = new ArrayList<>(M);
        for(int i = 0; i < M; i++) {
            input = new StringTokenizer(in.readLine());
            int Si = Integer.parseInt(input.nextToken()) - 1, Ei = Integer.parseInt(input.nextToken()) - 1, Ci = Integer.parseInt(input.nextToken());
            c[Si][Ei] += Ci;
            f[Si][Ei] += Ci;
            edges.add(new Edge(i + 1, Si, Ei, Ci));
        }
        Collections.sort(edges, (a, b) -> b.c - a.c == 0 ? a.i - b.i : b.c - a.c);
        int src = 0, sink = N - 1;
        long totalFlow = 0;
        while(true) {
            int maxFlow, maxLoc;
            int[] prevNode = new int[N], flow = new int[N];
            Arrays.fill(prevNode, -1);
            boolean[] visited = new boolean[N];
            flow[src] = 200000000;
            while(true) {
                maxFlow = 0;
                maxLoc = -1;
                for(int i = 0; i < N; i++) {
                    if(flow[i] > maxFlow && !visited[i]) {
                        maxFlow = flow[i];
                        maxLoc = i;
                    }
                }
                if(maxLoc == -1 || maxLoc == sink) {
                    break;
                }
                visited[maxLoc] = true;
                for(int i = 0; i < N; i++) {
                    if(i != maxLoc && f[maxLoc][i] > 0) {
                        if(flow[i] < Math.min(maxFlow, f[maxLoc][i])) {
                            prevNode[i] = maxLoc;
                            flow[i] = Math.min(maxFlow, f[maxLoc][i]);
                        }
                    }
                }
            }
            if(maxLoc == -1) {
                break;
            }
            int pathCapacity = flow[sink];
            totalFlow += pathCapacity;
            
            int curNode = sink;
            while(curNode != src) {
                int nextNode = prevNode[curNode];
                f[nextNode][curNode] -= pathCapacity;
                f[curNode][nextNode] += pathCapacity;
                curNode = nextNode;
            }
        }
        long bufFlow = totalFlow;
        List<Edge> ans = new ArrayList<>();
        for(Edge edge : edges) {
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    f[i][j] = c[i][j];
                }
            }
            long fl = 0;
            f[edge.s][edge.e] -= edge.c;
            while(true) {
                int maxFlow, maxLoc;
                int[] prevNode = new int[N], flow = new int[N];
                Arrays.fill(prevNode, -1);
                boolean[] visited = new boolean[N];
                flow[src] = 200000000;
                while(true) {
                    maxFlow = 0;
                    maxLoc = -1;
                    for(int i = 0; i < N; i++) {
                        if(flow[i] > maxFlow && !visited[i]) {
                            maxFlow = flow[i];
                            maxLoc = i;
                        }
                    }
                    if(maxLoc == -1 || maxLoc == sink) {
                        break;
                    }
                    visited[maxLoc] = true;
                    for(int i = 0; i < N; i++) {
                        if(i != maxLoc && f[maxLoc][i] > 0) {
                            if(flow[i] < Math.min(maxFlow, f[maxLoc][i])) {
                                prevNode[i] = maxLoc;
                                flow[i] = Math.min(maxFlow, f[maxLoc][i]);
                            }
                        }
                    }
                }
                if(maxLoc == -1) {
                    break;
                }
                int pathCapacity = flow[sink];
                fl += pathCapacity;
                
                int curNode = sink;
                while(curNode != src) {
                    int nextNode = prevNode[curNode];
                    f[nextNode][curNode] -= pathCapacity;
                    f[curNode][nextNode] += pathCapacity;
                    curNode = nextNode;
                }
            }
            if(bufFlow - fl == edge.c) {
                bufFlow = fl;
                c[edge.s][edge.e] -= edge.c;
                ans.add(edge);
                if(fl == 0) {
                    break;
                }
            }
        }
        Collections.sort(ans, (a, b) -> a.i - b.i);
		
        out.println(totalFlow + " " + ans.size());
        for(Edge edge : ans) {
            out.println(edge.i);
        }
		out.close();
	}
}

class Edge {
    int i, s, e, c;
    
    Edge(int i, int s, int e, int c) {
        this.i = i;
        this.s = s;
        this.e = e;
        this.c = c;
    }
}