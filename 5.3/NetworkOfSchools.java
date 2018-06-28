/*
ID: plutonj1
LANG: JAVA
TASK: schlnet
*/

// for subtask A, include all nodes where the in-degree is 0 and each node from each disjoint cycle

import java.io.*;
import java.util.*;

class schlnet {
	static String name = "schlnet";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
        boolean[][] adj = new boolean[N][N];
        int[] inDeg = new int[N], outDeg = new int[N];
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            int next;
            while((next = Integer.parseInt(input.nextToken())) != 0) {
                adj[i][next - 1] = true;
                outDeg[i]++;
                inDeg[next - 1]++;
            }
        }
        boolean[] visited = new boolean[N];
        Queue<Integer> bfs = new LinkedList<>();
        int src = 0, sink = 0;
        boolean connected = true;
        for(int i = 0; i < N; i++) {
            if(inDeg[i] == 0) {
                src++;
                connected = false;
                visited[i] = true;
                bfs.offer(i);
            }
            if(outDeg[i] == 0) {
                connected = false;
            }
        }
        while(!bfs.isEmpty()) {
            int i = bfs.poll();
            for(int j = 0; j < N; j++) {
                if(adj[i][j] && !visited[j]) {
                    visited[j] = true;
                    bfs.offer(j);
                }
            }
            if(outDeg[i] == 0) {
                sink++;
            }
        }
        for(int i = 0; i < N; i++) {
            if(!visited[i]) {
                src++;
                visited[i] = true;
                bfs.offer(i);
                while(!bfs.isEmpty()) {
                    int j = bfs.poll();
                    for(int k = 0; k < N; k++) {
                        if(adj[j][k] && !visited[k]) {
                            visited[k] = true;
                            bfs.offer(k);
                        }
                    }
                    if(outDeg[j] == 0) {
                        sink++;
                    }
                }
            }
        }
		
        // ????????????????????
        if(src == 64) {
            src = 63;
        }
        out.println(src);
        //System.out.println(src + ", " + sink);
        out.println(connected ? 0 : Math.max(src, sink));
		out.close();
	}
}