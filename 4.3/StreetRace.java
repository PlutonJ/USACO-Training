/*
ID: plutonj1
LANG: JAVA
TASK: race3
*/

import java.io.*;
import java.util.*;

class race3 {
	static String name = "race3";
	static BufferedReader in;
	static PrintWriter out;
    
    static boolean[][] adj;
    static int[] minPath;
    static int ind;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        adj = new boolean[50][50];
        ind = 0;
        String cheese;
        while(!(cheese = in.readLine()).equals("-1")) {
            input = new StringTokenizer(cheese);
            String token;
            while(!(token = input.nextToken()).equals("-2")) {
                int p = Integer.parseInt(token);
                adj[ind][p] = true;
            }
            ind++;
        }
        
        minPath = new int[ind];
        Arrays.fill(minPath, 100);
        minPath[0] = 0;
        List<Integer> unavoidable = new ArrayList<>();
        // if cannot reach end without a node, then that node is unavoidable
        for(int i = 1; i < ind - 1; i++) {
            if(dfs(0, i, new HashSet<>())) {
                continue;
            }
            unavoidable.add(i);
        }
        List<Integer> splitting = new ArrayList<>();
        // if cannot reach a node earlier than this unavoidable node, then it is a splitting point
        for(int i : unavoidable) {
            if(dfsCycle(i, i, minPath[i], new HashSet<>())) {
                continue;
            }
            splitting.add(i);
        }
        
        out.print(unavoidable.size() + (unavoidable.size() > 0 ? " " : ""));
        Iterator<Integer> iter = unavoidable.iterator();
        while(iter.hasNext()) {
            out.print(iter.next());
            if(iter.hasNext()) {
                out.print(" ");
            }
        }
        out.println("");
        out.print(splitting.size() + (splitting.size() > 0 ? " " : ""));
        iter = splitting.iterator();
        while(iter.hasNext()) {
            out.print(iter.next());
            if(iter.hasNext()) {
                out.print(" ");
            }
        }
        out.println("");
		out.close();
	}
    
    static boolean dfs(int current, int exclude, Set<Integer> visited) {
        if(current == ind - 1) {
            return true;
        }
        visited.add(current);
        for(int i = 0; i < ind; i++) {
            if(adj[current][i] && minPath[current] + 1 < minPath[i]) {
                minPath[i] = minPath[current] + 1;
            }
            if(adj[current][i] && !visited.contains(i) && i != exclude && dfs(i, exclude, visited)) {
                //System.out.println(visited);
                //System.out.println(current);
                return true;
            }
        }
        visited.remove(current);
        return false;
    }
    
    static boolean dfsCycle(int current, int dest, int min, Set<Integer> visited) {
        visited.add(current);
        for(int i = 0; i < ind; i++) {
            // has to connect to a node before the unavoidable node, i.e., min(minPath[i for each node on path]) < minPath[dest]
            if(i == dest && adj[current][i] && min < minPath[i]) {
                return true;
            }
            if(adj[current][i] && !visited.contains(i) && dfsCycle(i, dest, Math.min(minPath[i], min), visited)) {
                return true;
            }
        }
        visited.remove(current);
        return false;
    }
}