/*
ID: plutonj1
LANG: JAVA
TASK: ditch
*/

// Ford-Fulkerson algorithm

import java.io.*;
import java.util.*;

class ditch {
	static String name = "ditch";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(input.nextToken()), M = Integer.parseInt(input.nextToken());
        int[][] c = new int[M][M];
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            int Si = Integer.parseInt(input.nextToken()) - 1, Ei = Integer.parseInt(input.nextToken()) - 1, Ci = Integer.parseInt(input.nextToken());
            // use += instead of = since there could be multiple arcs between the same nodes
            c[Si][Ei] += Ci;
        }
        int src = 0, sink = M - 1, totalFlow = 0;
        while(true) {
            int[] prevNode = new int[M];
            int[] flow = new int[M];
            boolean[] visited = new boolean[M];
            int maxLoc = -1;
            Arrays.fill(prevNode, -1);
            flow[src] = 2000000001;
            while(true) {
                int maxFlow = 0;
                maxLoc = -1;
                for(int i = 0; i < M; i++) {
                    if(flow[i] > maxFlow && !visited[i]) {
                        maxFlow = flow[i];
                        maxLoc = i;
                    }
                }
                if(maxLoc == -1) {
                    break;
                }
                if(maxLoc == sink) {
                    break;
                }
                visited[maxLoc] = true;
                for(int i = 0; i < M; i++) {
                    if(i != maxLoc && c[maxLoc][i] > 0) {
                        if(flow[i] < Math.min(maxFlow, c[maxLoc][i])) {
                            prevNode[i] = maxLoc;
                            flow[i] = Math.min(maxFlow, c[maxLoc][i]);
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
                c[nextNode][curNode] = c[nextNode][curNode] - pathCapacity;
                c[curNode][nextNode] = c[curNode][nextNode] + pathCapacity;
                curNode = nextNode;
            }
        }
        
        out.println(totalFlow);
		out.close();
	}
}