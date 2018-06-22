/*
ID: plutonj1
LANG: JAVA
TASK: frameup
*/

import java.io.*;
import java.util.*;

class frameup {
	static String name = "frameup";
	static BufferedReader in;
	static PrintWriter out;
    
    static boolean[][] after;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int H = Integer.parseInt(input.nextToken()), W = Integer.parseInt(input.nextToken());
        int[][] pic = new int[H][W];
        boolean[] used = new boolean[26];
        for(int i = 0; i < H; i++) {
            String cheese = in.readLine();
            for(int j = 0; j < W; j++) {
                char c = cheese.charAt(j);
                if(c == '.') {
                    pic[i][j] = -1;
                } else {
                    pic[i][j] = c - 'A';
                    used[pic[i][j]] = true;
                }
            }
        }
        after = new boolean[26][26];
        int[] top = new int[26], right = new int[26], bottom = new int[26], left = new int[26];
        Arrays.fill(top, H);
        Arrays.fill(left, W);
        for(int i = 0; i < H; i++) {
            for(int j = 0; j < W; j++) {
                if(pic[i][j] >= 0) {
                    if(i < top[pic[i][j]]) {
                        top[pic[i][j]] = i;
                    }
                    if(i > bottom[pic[i][j]]) {
                        bottom[pic[i][j]] = i;
                    }
                    if(j < left[pic[i][j]]) {
                        left[pic[i][j]] = j;
                    }
                    if(j > right[pic[i][j]]) {
                        right[pic[i][j]] = j;
                    }
                }
            }
        }
        // determine which frames are above which frames
        for(int c = 0; c < 26; c++) {
            if(used[c]) {
                for(int i = top[c]; i <= bottom[c]; i++) {
                    if(pic[i][left[c]] != c) {
                        after[pic[i][left[c]]][c] = true;
                    }
                    if(pic[i][right[c]] != c) {
                        after[pic[i][right[c]]][c] = true;
                    }
                }
                for(int i = left[c] + 1; i < right[c]; i++) {
                    if(pic[top[c]][i] != c) {
                        after[pic[top[c]][i]][c] = true;
                    }
                    if(pic[bottom[c]][i] != c) {
                        after[pic[bottom[c]][i]][c] = true;
                    }
                }
            }
        }
        // if c is after i and i is after j then c is after j
        for(int c = 0; c < 26; c++) {
            if(used[c]) {
                for(int i = 0; i < 26; i++) {
                    if(after[c][i]) {
                        for(int j = 0; j < 26; j++) {
                            after[c][j] = after[c][j] || after[i][j];
                        }
                    }
                }
            }
        }
        List<Integer> pool = new ArrayList<>(), use = new ArrayList<>();
        for(int c = 0; c < 26; c++) {
            if(used[c]) {
                pool.add(c);
            }
        }
        dfs(use, pool, "");
		
		out.close();
	}
    
    static void dfs(List<Integer> used, List<Integer> pool, String pre) {
        if(pool.size() == 0) {
            out.println(pre);
        } else {
            next: for(int i = 0; i < pool.size(); i++) {
                int c = pool.get(i);
                // only allow a frame to be put on when all of the frames that come before it are on
                for(int j = 0; j < 26; j++) {
                    if(after[c][j] && !used.contains(j)) {
                        continue next;
                    }
                }
                used.add(c);
                pool.remove(i);
                dfs(used, pool, pre + (char)('A' + c));
                used.remove(used.size() - 1);
                pool.add(i, c);
            }
        }
    }
}