/*
ID: plutonj1
LANG: JAVA
TASK: heritage
*/

// construct the tree and print post-order traversal

import java.io.*;
import java.util.*;

class heritage {
	static String name = "heritage";
	static BufferedReader in;
	static PrintWriter out;
    
    static int r = 0;
    static char[] pre;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
        char[] tree = in.readLine().toCharArray();
        pre = in.readLine().toCharArray();
        Node t = Node.construct(tree);
        
        out.println(t.postOrderTraversal());
		out.close();
	}
    
    static char nextRoot() {
        return pre[r++];
    }
}

class Node {
    char c;
    Node left, right;
    
    Node(char c) {
        this.c = c;
    }
    
    StringBuilder postOrderTraversal() {
        StringBuilder sb = new StringBuilder();
        if(left != null) {
            sb.append(left.postOrderTraversal());
        }
        if(right != null) {
            sb.append(right.postOrderTraversal());
        }
        return sb.append(c);
    }
    
    static Node construct(char[] tree) {
        if(tree.length == 0) {
            return null;
        }
        Node root = new Node(heritage.nextRoot());
        if(tree.length == 1) {
            return root;
        }
        int ind = indexOf(tree, root.c);
        root.left = construct(Arrays.copyOfRange(tree, 0, ind));
        root.right = construct(Arrays.copyOfRange(tree, ind + 1, tree.length));
        return root;
    }
    
    static int indexOf(char[] ch, char c) {
        for(int i = 0; i < ch.length; i++) {
            if(ch[i] == c) {
                return i;
            }
        }
        return -1;
    }
}