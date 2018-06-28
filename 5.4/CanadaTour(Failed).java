/*
ID: plutonj1
LANG: JAVA
TASK: tour
*/

// find all west-east paths and take maximum combined number of cities of any two disjoint paths
// times out on test case 8

import java.io.*;
import java.util.*;

class tour {
	static String name = "tour";
	static BufferedReader in;
	static PrintWriter out;
    
    static int N;
    static Map<String, Integer> map = new HashMap<>();
    static boolean[][] adj;
    static Set<Integer> path = new HashSet<>();
    static Set<Path> paths = new HashSet<>();
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        N = Integer.parseInt(input.nextToken());
        int V = Integer.parseInt(input.nextToken());
        adj = new boolean[N][N];
        for(int i = 0; i < N; i++) {
            map.put(in.readLine(), i);
        }
        for(int i = 0; i < V; i++) {
            input = new StringTokenizer(in.readLine());
            int a = map.get(input.nextToken()), b = map.get(input.nextToken());
            adj[a][b] = adj[b][a] = true;
        }
        for(int i = 1; i < N; i++) {
            if(adj[0][i]) {
                dfs(i);
            }
        }
        int max = -1;
        System.out.println(paths.size());
        for(Path w2e : paths) {
            for(Path e2w : paths) {
                if(w2e != e2w && w2e.disjoint(e2w) && w2e.count + e2w.count > max) {
                    max = w2e.count + e2w.count;
                }
            }
        }
		
        out.println(max + 2);
		out.close();
	}
    
    static void dfs(int cur) {
        path.add(cur);
        // start from cur + 1 since the restraint on direction
        for(int i = cur + 1; i < N - 1; i++) {
            if(adj[cur][i]) {
                dfs(i);
            }
        }
        if(adj[cur][N - 1]) {
            paths.add(new Path(path));
        }
        path.remove(cur);
    }
}

class Path {
    int[] path = new int[5];
    int count = 0;
    
    Path(Set<Integer> p) {
        for(int i : p) {
            path[i / 20] += 1 << (i % 20);
            count++;
        }
    }
    
    boolean disjoint(Path p) {
        return ((path[0] | p.path[0]) == path[0] + p.path[0]) && 
               ((path[1] | p.path[1]) == path[1] + p.path[1]) && 
               ((path[2] | p.path[2]) == path[2] + p.path[2]) && 
               ((path[3] | p.path[3]) == path[3] + p.path[3]) && 
               ((path[4] | p.path[4]) == path[4] + p.path[4]);
    }
}