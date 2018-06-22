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
    
    static Level[] level;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int H = Integer.parseInt(input.nextToken()), W = Integer.parseInt(input.nextToken());
        char[][] pic = new char[H][W];
        boolean[] used = new boolean[26];
        for(int i = 0; i < H; i++) {
            pic[i] = in.readLine().toCharArray();
            for(int j = 0; j < W; j++) {
                if(pic[i][j] != '.') {
                    used[pic[i][j] - 'A'] = true;
                }
            }
        }
        level = new Level[26];
        boolean[][] after = new boolean[26][26], before = new boolean[26][26];
        int[] top = new int[26], right = new int[26], bottom = new int[26], left = new int[26];
        Arrays.fill(top, H);
        Arrays.fill(left, W);
        for(int i = 0; i < H; i++) {
            for(int j = 0; j < W; j++) {
                if(pic[i][j] != '.') {
                    int c = pic[i][j] - 'A';
                    if(i < top[c]) {
                        top[c] = i;
                    }
                    if(i > bottom[c]) {
                        bottom[c] = i;
                    }
                    if(j < left[c]) {
                        left[c] = j;
                    }
                    if(j > right[c]) {
                        right[c] = j;
                    }
                }
            }
        }
        // determine which frames are above and below which frames
        for(int c = 0; c < 26; c++) {
            if(used[c]) {
                for(int i = top[c]; i <= bottom[c]; i++) {
                    if(pic[i][left[c]] != 'A' + c) {
                        before[c][pic[i][left[c]] - 'A'] = true;
                        after[pic[i][left[c]] - 'A'][c] = true;
                    }
                    if(pic[i][right[c]] != 'A' + c) {
                        before[c][pic[i][right[c]] - 'A'] = true;
                        after[pic[i][right[c]] - 'A'][c] = true;
                    }
                }
                for(int i = left[c] + 1; i < right[c]; i++) {
                    if(pic[top[c]][i] != 'A' + c) {
                        before[c][pic[top[c]][i] - 'A'] = true;
                        after[pic[top[c]][i] - 'A'][c] = true;
                    }
                    if(pic[bottom[c]][i] != 'A' + c) {
                        before[c][pic[bottom[c]][i] - 'A'] = true;
                        after[pic[bottom[c]][i] - 'A'][c] = true;
                    }
                }
            }
        }
        // if c is before i and i is before j then c is before j
        // if c is after i and i is after j then c is after j
        for(int c = 0; c < 26; c++) {
            if(used[c]) {
                for(int i = 0; i < 26; i++) {
                    if(before[c][i]) {
                        for(int j = 0; j < 26; j++) {
                            before[c][j] = before[c][j] || before[i][j];
                        }
                    } else if(after[c][i]) {
                        for(int j = 0; j < 26; j++) {
                            after[c][j] = after[c][j] || after[i][j];
                        }
                    }
                }
            }
        }
        List<Char> list = new ArrayList<>(26);
        for(int c = 0; c < 26; c++) {
            if(used[c]) {
                Char ch = new Char(c);
                for(int i = 0; i < 26; i++) {
                    if(before[c][i]) {
                        ch.count++;
                    }
                }
                list.add(ch);
            }
        }
        Collections.sort(list, (a, b) -> b.count - a.count);
        /*for(int c = 0; c < 26; c++) {
            if(used[c]) {
                System.out.println((char)(c + 'A') + ": ");
                for(int i = 0; i < 26; i++) {
                    if(before[c][i]) {
                        System.out.print((char)(i + 'A') + " ");
                    }
                }
                System.out.println("");
                for(int i = 0; i < 26; i++) {
                    if(after[c][i]) {
                        System.out.print((char)(i + 'A') + " ");
                    }
                }
                System.out.println("");
            }
        }*/
        Level l = new Level();
        for(int c = 0; c < 26; c++) {
            if(used[c]) {
                l.frames[c] = true;
                level[c] = l;
            }
        }
        for(Char ch : list) {
            int c = ch.c;
            for(int i = 0; i < 26; i++) {
                // if i is in the same level as c right now
                if(level[c].frames[i]) {
                    // if c should be before i
                    if(before[c][i]) {
                        if(level[c].top == null) {
                            level[c].top = new Level();
                            level[c].top.bottom = level[c];
                        }
                        // add frame to top layer and delete frame from this layer
                        level[c].top.frames[i] = true;
                        level[i] = level[c].top;
                        level[c].frames[i] = false;
                    // if c should be after i
                    } else if(after[c][i]) {
                        if(level[c].bottom == null) {
                            level[c].bottom = new Level();
                            level[c].bottom.top = level[c];
                        }
                        // add frame to bottom layer and delete frame from this layer
                        level[c].bottom.frames[i] = true;
                        level[i] = level[c].bottom;
                        level[c].frames[i] = false;
                    }
                }
            }
        }
        while(l.bottom != null) {
            l = l.bottom;
        }
        l.print("");
		
		out.close();
	}
}

class Char {
    int c, count = 0;
    
    Char(int c) {
        this.c = c;
    }
}

class Level {
    boolean[] frames = new boolean[26];
    Level top, bottom;
    
    void print(String pre) {
        List<Character> chars = new ArrayList<>(26);
        for(int i = 0; i < 26; i++) {
            if(frames[i]) {
                chars.add((char)(i + 'A'));
            }
        }
        print(chars, pre);
        for(int i = 0; i < 26; i++) {
            if(frames[i]) {
                System.out.print((char)('A' + i) + " ");
            }
        }
        System.out.println(" ");
    }
    
    void print(List<Character> chars, String pre) {
        if(chars.size() == 0) {
            if(top == null) {
                frameup.out.println(pre);
            } else {
                top.print(pre);
            }
        }
        for(int i = 0; i < chars.size(); i++) {
            char c = chars.remove(i);
            print(chars, pre + c);
            chars.add(i, c);
        }
    }
}