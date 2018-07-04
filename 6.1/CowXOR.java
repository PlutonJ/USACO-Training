/*
ID: plutonj1
LANG: JAVA
TASK: cowxor
*/

// https://blog.csdn.net/jiangshibiao/article/details/21441431

import java.io.*;
import java.util.*;

class cowxor {
	static String name = "cowxor";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine()), cow[] = new int[N], prefix[] = new int[N + 1];
        Node trie = new Node();
        prefix[0] = cow[0];
        trie.insert(0, prefix[0], 20);
        for(int i = 0; i < N; i++) {
            cow[i] = Integer.parseInt(in.readLine());
            prefix[i + 1] = prefix[i] ^ cow[i];
        }
        int max = cow[0], a = 0, b = 0;
        for(int i = 0; i < N; i++) {
            // insert to trie as finding maximum match goes along to ensure that maxMatch.i <= i
            trie.insert(i + 1, prefix[i + 1], 20);
            Node maxMatch = trie.find(~prefix[i + 1], 20);
            int cur = prefix[i + 1] ^ maxMatch.v;
            if(cur > max) {
                max = cur;
                a = maxMatch.i;
                b = i;
            }
        }
		
        out.println(max + " " + (a + 1) + " " + (b + 1));
		out.close();
	}
}

class Node {
    Node[] c = new Node[2];
    int v, i = -1;
    
    void insert(int i, int n, int d) {
        if(d == -1) {
            this.i = i;
            v = n;
            return;
        }
        int bit = (n & (1 << d)) >> d;
        if(c[bit] == null) {
            c[bit] = new Node();
        }
        c[bit].insert(i, n, d - 1);
    }
    
    // greedy
    Node find(int n, int d) {
        if(d == -1) {
            return this;
        } else {
            int bit = (n & (1 << d)) >> d;
            return c[bit] == null ? c[~bit & 1].find(n, d - 1) : c[bit].find(n, d - 1);
        }
    }
}