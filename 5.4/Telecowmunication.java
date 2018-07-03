/*
ID: plutonj1
LANG: JAVA
TASK: telecow
*/

// written according to the USACO analysis, but bidirectional edge between in-nodes and out-nodes are not necessary
// only one arc from each in-node to each out-node is necessary

import java.io.*;
import java.util.*;

class telecow {
	static String name = "telecow";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(input.nextToken()), M = Integer.parseInt(input.nextToken()), c1 = Integer.parseInt(input.nextToken()) - 1, c2 = Integer.parseInt(input.nextToken()) - 1;
        boolean[][] adj = new boolean[N][N];
        for(int i = 0; i < M; i++) {
            input = new StringTokenizer(in.readLine());
            int a1 = Integer.parseInt(input.nextToken()) - 1, a2 = Integer.parseInt(input.nextToken()) - 1;
            adj[a1][a2] = adj[a2][a1] = true;
        }
        int src = c1, sink = c2;
        int max = 0;
        int[][] c = new int[2 * N][2 * N];
        for(int i = 0; i < N; i++) {
            c[2 * i][2 * i + 1]  = 1;
        }
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(adj[i][j]) {
                    c[2 * i + 1][2 * j] = c[2 * j + 1][2 * i] = N;
                }
            }
        }
        while(true) {
            int[] prevNode = new int[2 * N], flow = new int[2 * N];
            boolean[] visited = new boolean[2 * N];
            Arrays.fill(prevNode, -1);
            flow[2 * src + 1] = N;
            int maxFlow, maxLoc;
            while(true) {
                maxFlow = 0;
                maxLoc = -1;
                for(int i = 0; i < 2 * N; i++) {
                    if(flow[i] > maxFlow && !visited[i]) {
                        maxFlow = flow[i];
                        maxLoc = i;
                    }
                }
                if(maxLoc == 2 * sink || maxLoc == -1) {
                    break;
                }
                visited[maxLoc] = true;
                for(int i = 0; i < 2 * N; i++) {
                    if(c[maxLoc][i] > 0 && flow[i] < Math.min(maxFlow, c[maxLoc][i])) {
                        prevNode[i] = maxLoc;
                        flow[i] = Math.min(maxFlow, c[maxLoc][i]);
                    }
                }
            }
            if(maxLoc == -1) {
                break;
            }
            int pathCapacity = flow[2 * sink];
            max += pathCapacity;
            int curNode = 2 * sink;
            while(curNode != 2 * src + 1) {
                int nextNode = prevNode[curNode];
                c[nextNode][curNode] -= pathCapacity;
                c[curNode][nextNode] += pathCapacity;
                curNode = nextNode;
            }
        }
        List<Integer> cut = new ArrayList<>();
        for(int off = 0; off < N; off++) {
            if(off != src && off != sink) {
                int totalFlow = 0;
                c = new int[2 * N][2 * N];
                for(int i = 0; i < N; i++) {
                    c[2 * i][2 * i + 1] = 1;
                }
                for(int o : cut) {
                    c[2 * o][2 * o + 1] = 0;
                }
                for(int i = 0; i < N; i++) {
                    for(int j = 0; j < N; j++) {
                        if(adj[i][j]) {
                            c[2 * i + 1][2 * j] = c[2 * j + 1][2 * i] = N;
                        }
                    }
                }
                c[2 * off][2 * off + 1] = 0;
                while(true) {
                    int[] prevNode = new int[2 * N], flow = new int[2 * N];
                    boolean[] visited = new boolean[2 * N];
                    Arrays.fill(prevNode, -1);
                    flow[2 * src + 1] = N;
                    int maxFlow, maxLoc;
                    while(true) {
                        maxFlow = 0;
                        maxLoc = -1;
                        for(int i = 0; i < 2 * N; i++) {
                            if(flow[i] > maxFlow && !visited[i]) {
                                maxFlow = flow[i];
                                maxLoc = i;
                            }
                        }
                        if(maxLoc == 2 * sink || maxLoc == -1) {
                            break;
                        }
                        visited[maxLoc] = true;
                        for(int i = 0; i < 2 * N; i++) {
                            if(c[maxLoc][i] > 0 && flow[i] < Math.min(maxFlow, c[maxLoc][i])) {
                                prevNode[i] = maxLoc;
                                flow[i] = Math.min(maxFlow, c[maxLoc][i]);
                            }
                        }
                    }
                    if(maxLoc == -1) {
                        break;
                    }
                    int pathCapacity = flow[2 * sink];
                    totalFlow += pathCapacity;
                    int curNode = 2 * sink;
                    while(curNode != 2 * src + 1) {
                        int nextNode = prevNode[curNode];
                        c[nextNode][curNode] -= pathCapacity;
                        c[curNode][nextNode] += pathCapacity;
                        curNode = nextNode;
                    }
                }
                if(totalFlow < max) {
                    cut.add(off);
                    max = totalFlow;
                    if(max == 0) {
                        break;
                    }
                }
            }
        }
        
        out.println(cut.size());
        out.print(cut.get(0) + 1);
        for(int i = 1; i < cut.size(); i++) {
            out.print(" " + (cut.get(i) + 1));
        }
        out.println("");
		out.close();
	}
}