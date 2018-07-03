/*
ID: plutonj1
LANG: JAVA
TASK: charrec
*/

// screw this problem

import java.io.*;
import java.util.*;

class charrec {
	static String name = "charrec";
	static BufferedReader in, font;
	static PrintWriter out;
    
    static final char[] ch = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    
    static int[][] ideal = new int[27][20];
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		font = new BufferedReader(new FileReader("font.in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        font.readLine();
        String cheese;
        int ind = 0;
        while((cheese = font.readLine()) != null) {
            ideal[ind / 20][ind++ % 20] = Integer.parseInt(cheese, 2);
        }
        int N = Integer.parseInt(in.readLine());
        int[] raw = new int[N];
        for(int i = 0; i < N; i++) {
            raw[i] = Integer.parseInt(in.readLine(), 2);
        }
        ind = 0;
        StringBuilder sb = new StringBuilder();
        while(ind < N) {
            int max = -1, minScore = 400, skipN = 0;
            for(int i = 0; i < 27; i++) {
                int minS = 400, score = 0, n = 19;
                for(int j = 0; j < 20; j++) {
                    score = 0;
                    for(int k = 0; k < 19; k++) {
                        if(k < j) {
                            score += diff(raw[ind + k], ideal[i][k]);
                        } else {
                            score += diff(raw[ind + k], ideal[i][k + 1]);
                        }
                    }
                    if(score < minS) {
                        minS = score;
                    }
                }
                score = 0;
                if(N - ind >= 20) {
                    for(int j = 0; j < 20; j++) {
                        score += diff(raw[ind + j], ideal[i][j]);
                    }
                    if(score < minS) {
                        n = 20;
                        minS = score;
                    }
                    if(N - ind >= 21) {
                        for(int j = 0; j < 20; j++) {
                            score = 0;
                            for(int k = 0; k < 20; k++) {
                                if(k < j) {
                                    score += diff(raw[ind + k], ideal[i][k]);
                                } else if(k == j) {
                                    score += Math.min(diff(raw[ind + k], ideal[i][k]), diff(raw[ind + k + 1], ideal[i][k]));
                                } else {
                                    score += diff(raw[ind + k + 1], ideal[i][k]);
                                }
                            }
                            if(score < minS) {
                                n = 21;
                                minS = score;
                            }
                        }
                    }
                }
                System.out.println(sb.length() + "[" + ch[i] + "]: " + minS + ", " + n);
                if(minS < minScore) {
                    max = i;
                    minScore = minS;
                    skipN = n;
                }
            }
            sb.append(max == -1 || minScore >= 120 ? '?' : ch[max]);
            ind += skipN;
        }
		
        out.println(sb);
		out.close();
	}
    
    static int diff(int a, int b) {
        int c = a ^ b, count = 0;
        while(c > 0) {
            if(c % 2 == 1) {
                count++;
            }
            c /= 2;
        }
        return count;
    }
}