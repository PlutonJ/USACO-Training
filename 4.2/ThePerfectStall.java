/*
ID: plutonj1
LANG: JAVA
TASK: stall4
*/

// maximum matching

import java.io.*;
import java.util.*;

class stall4 {
	static String name = "stall4";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(input.nextToken()), M = Integer.parseInt(input.nextToken());
        int[][] c = new int[N + M + 2][N + M + 2];
        int src = 0, sink = N + M + 1;
        for(int i = 1; i <= N; i++) {
            c[src][i] = 1;
        }
        for(int i = 1; i <= M; i++) {
            c[i + N][sink] = 1;
        }
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            int Si = Integer.parseInt(input.nextToken());
            for(int j = 0; j < Si; j++) {
                c[i + 1][Integer.parseInt(input.nextToken()) + N] = 1;
            }
        }
        // the code below is copied from DrainDitches.java exactly with minimal changes
        int totalFlow = 0;
        while(true) {
            int[] prevNode = new int[N + M + 2];
            int[] flow = new int[N + M + 2];
            boolean[] visited = new boolean[N + M + 2];
            int maxLoc = -1;
            Arrays.fill(prevNode, -1);
            flow[src] = N;
            while(true) {
                int maxFlow = 0;
                maxLoc = -1;
                for(int i = 0; i < N + M + 2; i++) {
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
                for(int i = 0; i < N + M + 2; i++) {
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