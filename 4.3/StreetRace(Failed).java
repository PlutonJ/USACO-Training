/*
ID: plutonj1
LANG: JAVA
TASK: race3
*/

// an unavoidable point is a node for which all of the nodes(before the next unavoidable point) that its out-arcs point to only have one in-arc which comes from this node

import java.io.*;
import java.util.*;

class race3 {
	static String name = "race3";
	static BufferedReader inR;
	static PrintWriter outP;
	
	public static void main(String[] args) throws IOException {
		inR = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		outP = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int[][] in = new int[50][50];
        int[] inInd = new int[50];
        int ind = 0;
        String cheese;
        while(!(cheese = inR.readLine()).equals("-1")) {
            input = new StringTokenizer(cheese);
            String token;
            while(!(token = input.nextToken()).equals("-2")) {
                int p = Integer.parseInt(token);
                if(p != ind) {
                    in[p][inInd[p]++] = ind;
                }
            }
            ind++;
        }
        // after reading input, ind = number of nodes, inInd[i]is the number of in-arcs of node i
        // traverse through in-arcs starting from the finish, enqueuing all nodes on the opposite side of the in-arcs
        // stop until the node only has 1 in-arc from any unvisited node and enqueue the node for later
        // if all nodes get stuck on 1 node then that node is an unavoidable point
        // repeat the processing from each unavoidable point until finished
        Queue<Integer> bfs = new LinkedList<>();
        Set<Integer> section = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> unavoidable = new TreeSet<>();
        List<Integer> splitting = new ArrayList<>();
        int[] lastUnavoidable = new int[ind];
        bfs.offer(ind - 1);
        while(!bfs.isEmpty()) {
            System.out.println(bfs);
            int node = bfs.poll();
            visited.add(node);
            if(section.contains(node)) {
                int point = in[node][0];
                if(point == 0) {
                    break;
                }
                for(int p : visited) {
                    if(lastUnavoidable[p] == 0) {
                        lastUnavoidable[p] = point;
                    }
                }
                lastUnavoidable[point] = point;
                section.clear();
                bfs.clear();
                unavoidable.add(point);
                splitting.add(point);
                bfs.offer(point);
            } else {
                if(inInd[node] == 1) {
                    bfs.offer(node);
                } else {
                    int count = 0;
                    for(int i = 0; i < inInd[node]; i++) {
                        if(visited.contains(in[node][i])) {
                            while(splitting.contains(lastUnavoidable[in[node][i]])) {
                                splitting.remove(splitting.size() - 1);
                            }
                            if(unavoidable.contains(node)) {
                                splitting.add(node);
                            }
                        } else {
                            count++;
                        }
                    }
                    if(count <= 1) {
                        bfs.offer(node);
                    } else {
                        for(int i = 0; i < inInd[node]; i++) {
                            if(!visited.contains(in[node][i])) {
                                bfs.offer(in[node][i]);
                            }
                        }
                    }
                }
                section.add(node);
            }
        }
        Collections.sort(splitting);
		
        outP.print(unavoidable.size() + (unavoidable.size() > 0 ? " " : ""));
        Iterator<Integer> iter = unavoidable.iterator();
        while(iter.hasNext()) {
            outP.print(iter.next());
            if(iter.hasNext()) {
                outP.print(" ");
            }
        }
        outP.println("");
        outP.print(splitting.size() + (splitting.size() > 0 ? " " : ""));
        iter = splitting.iterator();
        while(iter.hasNext()) {
            outP.print(iter.next());
            if(iter.hasNext()) {
                outP.print(" ");
            }
        }
        outP.println("");
		outP.close();
	}
}