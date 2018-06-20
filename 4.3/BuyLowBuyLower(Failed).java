/*
ID: plutonj1
LANG: JAVA
TASK: buylow
*/

// bfs, times out on test case 5

import java.io.*;
import java.util.*;

class buylow {
	static String name = "buylow";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
        List<Node> nodes = new ArrayList<>(N);
        String cheese;
        while((cheese = in.readLine()) != null) {
            input = new StringTokenizer(cheese);
            while(input.hasMoreTokens()) {
                Node node = new Node(Integer.parseInt(input.nextToken()));
                for(Node n : nodes) {
                    n.add(node);
                }
                nodes.add(node);
            }
        }
        
        int count = 0;
        Set<Integer> used = new HashSet<>();
        for(Node node : nodes) {
            if(!used.contains(node.value)) {
                used.add(node.value);
                count += node.count(1);
            }
        }
        out.println(Node.maxDepth + " " + count);
        out.close();
	}
}

class Node {
    static int maxDepth = 1;
    int value, depth = 1, count = 0;
    List<Node> children = new ArrayList<>();
    
    Node(int value) {
        this.value = value;
    }
    
    
    int count(int depth) {
        if(depth == maxDepth) {
            return 1;
        }
        int sum = 0;
        Set<Integer> used = new HashSet<>();
        for(Node node : children) {
            if(!used.contains(node.value)) {
                used.add(node.value);
                sum += node.count(depth + 1);
            }
        }
        return sum;
    }
    
    void add(Node node) {
        if(node.value < value) {
            node.depth = Math.max(node.depth, depth + 1);
            maxDepth = Math.max(maxDepth, node.depth);
            children.add(node);
        }
    }
}