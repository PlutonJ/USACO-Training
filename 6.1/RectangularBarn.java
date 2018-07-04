/*
ID: plutonj1
LANG: JAVA
TASK: rectbarn
*/

// http://www.cnblogs.com/lichen782/p/leetcode_maximal_rectangle.html

import java.io.*;
import java.util.*;

class rectbarn {
	static String name = "rectbarn";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        long start = System.currentTimeMillis();
        
        input = new StringTokenizer(in.readLine());
        int R = Integer.parseInt(input.nextToken()), C = Integer.parseInt(input.nextToken()), P = Integer.parseInt(input.nextToken());
        int[][] d = new int[P][2];
        for(int i = 0; i < P; i++) {
            input = new StringTokenizer(in.readLine());
            d[i] = new int[] {Integer.parseInt(input.nextToken()) - 1, Integer.parseInt(input.nextToken()) - 1};
        }   
        Arrays.sort(d, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int max = 0, ind = 0, hist[] = new int[C + 1];
		for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++) {
                hist[c]++;
            }
            while(ind < P && d[ind][0] <= r) {
                hist[d[ind++][1]] = 0;
            }
            int[] stack = new int[C];
            int i = 0, p = 0;
            while(i < hist.length) {
                if(p == 0 || hist[stack[p - 1]] < hist[i]) {
                    stack[p++] = i++;
                } else {
                    int t = stack[--p], a = hist[t] * (p == 0 ? i : i - stack[p - 1] - 1);
                    if(a > max) {
                        max = a;
                    }
                }
            }
        }
        
        out.println(max);
		out.close();
	}
}