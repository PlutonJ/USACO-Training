/*
ID: plutonj1
LANG: JAVA
TASK: tour
*/

// USACO solution

import java.io.*;
import java.util.*;

class tour {
	static String name = "tour";
	static BufferedReader in;
	static PrintWriter out;
	
    static Map<String, Integer> map = new HashMap<>();
    
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
        input = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(input.nextToken()), V = Integer.parseInt(input.nextToken());
        boolean[][] adj = new boolean[N][N];
        for(int i = 0; i < N; i++) {
            map.put(in.readLine(), i);
        }
        for(int i = 0; i < V; i++) {
            input = new StringTokenizer(in.readLine());
            int a = map.get(input.nextToken()), b = map.get(input.nextToken());
            adj[a][b] = adj[b][a] = true;
        }
        
        int[][] dp = new int[N][N];
        for(int[] i : dp) {
            Arrays.fill(i, -1000000);
        }
        dp[0][0] = 1;
        adj[0][0] = true;
        for(int i = 1; i < N; i++) {
            if(adj[i][0]) {
                dp[i][0] = dp[0][i] = 2;
                for(int j = 1; j < i; j++) {
                    if(adj[j][0]) {
                        dp[i][j] = dp[j][i] = 3;
                    }
                }
            }
        }
        for(int i = 0; i < N; i++) {
            for(int j = 1; j < N; j++) {
                if(i != j && dp[i][j] > 0) {
                    for(int k = Math.min(Math.max(i, j) + 1, N - 1); k < N; k++) {
                        if(adj[i][k] && dp[k][j] < dp[i][j] + 1) {
                            dp[k][j] = dp[i][j] + 1;
                        }
                        if(adj[k][j] && dp[i][k] < dp[i][j] + 1) {
                            dp[i][k] = dp[i][j] + 1;
                        }
                    }
                }
            }
        }
        
        if(dp[N - 1][N - 1] > 0) {
            // - 1 because N - 1 is counted twice
            out.println(dp[N - 1][N - 1] - 1);
        } else {
            out.println(1);
        }
		out.close();
	}
}