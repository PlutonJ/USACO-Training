/*
ID: plutonj1
LANG: JAVA
TASK: heritage
*/

// janky algorithm that fails test case 3 because of the 
// assumption of that for a left child must be present for a right child to exist

import java.io.*;
import java.util.*;

class heritage {
	static String name = "heritage";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
        String cheese = in.readLine();
        char[] pre = in.readLine().toCharArray();
        int[] depths = new int[pre.length];
        // sort by depth, descending
        Queue<int[]> bounds = new PriorityQueue<>((a, b) -> b[2] - a[2] == 0 ? a[0] - b[0] : b[2] - a[2]);
        bounds.offer(new int[] {0, pre.length, 1});
        for(char c : pre) {
            int[] bound = bounds.poll();
            while(bound[1] - bound[0] == 0) {
                bound = bounds.poll();
            }
            int ind = cheese.indexOf(c);
            depths[ind] = bound[2];
            bounds.offer(new int[] {bound[0], ind, bound[2] + 1});
            bounds.offer(new int[] {ind + 1, bound[1], bound[2] + 1});
        }
        char[] post = new char[pre.length];
        post[0] = cheese.charAt(0);
        int currentDepth = depths[0];
        Stack<Integer> parser = new Stack<>();
        int i = 1, cursor = 1;
        while(i < post.length) {
            if(cursor >= post.length) {
                int ind = parser.pop();
                post[i++] = cheese.charAt(ind);
                currentDepth = depths[ind];
            } else if(depths[cursor] >= currentDepth) {
                post[i++] = cheese.charAt(cursor);
                currentDepth = depths[cursor];
            } else if(parser.empty()) {
                parser.push(cursor);
            } else if(depths[cursor] > depths[parser.peek()]) {
                parser.push(cursor);
            } else if(depths[cursor] < depths[parser.peek()]) {
                int ind = parser.pop();
                post[i++] = cheese.charAt(ind);
                currentDepth = depths[ind];
                cursor--;
            }
            cursor++;
        }
        
        out.println(new String(post));
		out.close();
	}
}