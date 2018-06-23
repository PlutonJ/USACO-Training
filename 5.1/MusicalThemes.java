/*
ID: plutonj1
LANG: JAVA
TASK: theme
*/

// a theme is a repeating sequence of differences between adjacent notes
// theme a theme has to be disjoint with at least one of its appearances, at least 2 of the sequences of differences have to be separated by at least 1 other interval

import java.io.*;
import java.util.*;

class theme {
	static String name = "theme";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine()), ind = 0;
        int[] melody = new int[N], intervals = new int[N - 1];
		String cheese;
        while((cheese = in.readLine()) != null && cheese.length() > 0) {
            input = new StringTokenizer(cheese);
            while(input.hasMoreTokens()) {
                melody[ind++] = Integer.parseInt(input.nextToken());
            }
        }
        if(N < 10) {
            out.println(0);
            out.close();
            return;
        }
        for(int i = 0; i < N - 1; i++) {
            intervals[i] = melody[i + 1] - melody[i];
            //System.out.print(i + ": " + intervals[i] + "   \t");
        }
        
        // 4998 * 5000 short array causes OutOfMemoryError on test case 11
        /*
        // maxLen[i][j] = length of the longest equal sequences starting at i and j where i < j
        // also, maxLen[i][j] < j - i and maxLen[i][j] < N - j
        short[][] maxLen = new short[N - 2][N];
        int max = 0;
        for(int j = N - 2; j >= 2; j--) {
            for(int i = 0; i <= j - 2; i++) {
                if(intervals[i] == intervals[j]) {
                    maxLen[i][j] = (short)Math.min(maxLen[i + 1][j + 1] + 1, j - i - 1);
                } else {
                    maxLen[i][j] = 0;
                }
                //maxLen[i][j] = Math.min(maxLen[i + 1][j + 1] + (intervals[i] == intervals[j] ? 1 : 0), Math.min(j - i - 1, N - j - 1));
                if(maxLen[i][j] > max) {
                    //System.out.println(i + ", " + j + ": " + maxLen[i][j]);
                    max = maxLen[i][j];
                }
            }
        }*/
        short[][] maxLen = new short[N - 2][2];
        int max = 0;
        for(int j = N - 2; j >= 2; j--) {
            for(int i = 0; i <= j - 2; i++) {
                if(intervals[i] == intervals[j]) {
                    maxLen[i][j % 2] = (short)Math.min(maxLen[i + 1][(j + 1) % 2] + 1, j - i - 1);
                } else {
                    maxLen[i][j % 2] = 0;
                }
                if(maxLen[i][j % 2] > max) {
                    max = maxLen[i][j % 2];
                }
            }
        }
        
        out.println(max >= 4 ? max + 1 : 0);
		out.close();
	}
}