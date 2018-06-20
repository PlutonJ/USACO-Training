/*
ID: plutonj1
LANG: JAVA
TASK: buylow
*/

// O(n ** 2) solution, times out on test case 10

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
        Node[] nodesBuf = new Node[N + 1], nodes = new Node[N + 1];
        int ind = 0;
        String cheese;
        while((cheese = in.readLine()) != null && cheese.length() > 0) {
            input = new StringTokenizer(cheese);
            while(input.hasMoreTokens()) {
                Node node = new Node(Integer.parseInt(input.nextToken()));
                nodesBuf[ind++] = node;
            }
        }
        nodesBuf[N] = new Node(0);
        ind = 0;
        for(int i = N; i >= 0; i--) {
            for(int j = 0; j < ind; j++) {
                nodes[j].add(nodesBuf[i]);
            }
            nodes[ind++] = nodesBuf[i];
        }
        Int count = new Int(0);
        for(Node node : nodes) {
            //System.out.println(node.value + " : " + node.depth + " : " + node.count.toString());
            if(node.depth == Node.maxDepth) {
                count.add(node.count);
            }
        }
        
        out.println((Node.maxDepth - 1) + " " + count.toString());
        out.close();
	}
}

class Node {
    static int maxDepth = 1;
    int value, depth = 1;
    Int count = new Int(1);
    Set<Integer> used = new HashSet<>();
    
    Node(int value) {
        this.value = value;
    }
    
    void add(Node node) {
        if(node.value > value && !used.contains(node.value)) {
            if(depth + 1 > node.depth) {
                node.depth = depth + 1;
                node.count = new Int(count);
                used.add(node.value);
            } else if(depth + 1 == node.depth) {
                node.count.add(count);
                used.add(node.value);
            }
            maxDepth = Math.max(maxDepth, node.depth);
        }
    }
}

class Int {
    static final int b = 1000000000;
    
    int[] d = new int[32];
    int size;
    
    Int(int v) {
        d[0] = v;
        size = 1;
    }
    
    Int(Int i) {
        for(int pos = 0; pos < i.size; pos++) {
            d[pos] = i.d[pos];
        }
        size = i.size;
    }
    
    void add(Int i) {
        int carry = 0;
        int s = Math.max(size, i.size);
        for(int pos = 0; pos < s; pos++) {
            d[pos] += i.d[pos] + carry;
            carry = d[pos] / b;
            d[pos] %= b;
        }
        if(carry != 0) {
            // assume size < 32
            d[s] = carry;
            size = s + 1;
        } else {
            size = s;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int pos = size - 1; pos >= 0; pos--) {
            sb.append(("000000000" + d[pos]).substring(("" + d[pos]).length()));
        }
        return sb.toString().replaceFirst("^0+(?!$)", "");
    }
}