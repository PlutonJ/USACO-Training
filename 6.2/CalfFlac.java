/*
ID: plutonj1
LANG: JAVA
TASK: calfflac
*/

import java.io.*;
import java.util.*;

class calfflac {
    static String name = "calfflac";
    static BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        String cheese, str = "";
        while((cheese = in.readLine()) != null) {
            str += cheese + "\n";
        }
        int ind = 0, inds[] = new int[str.length()];
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') {
                sb.append(c < 'a' ? (char)(c - 'A' + 'a') : c);
                inds[ind++] = i;
            }
        }
        String T = preProcess(sb.toString());
        int n = T.length(), P[] = new int[n], C = 0, R = 0, maxLen = 0, centerIndex = 0, a, b;
        for(int i = 1; i < n - 1; i++) {
            int iMirror = 2 * C - i;
            P[i] = (R > i) ? Math.min(R - i, P[iMirror]) : 0;
            while(T.charAt(i + 1 + P[i]) == T.charAt(i - 1 - P[i])) {
                P[i]++;
            }
            if(P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
            if(i + P[i] > R) {
                C = i;
                R = i + P[i];
            }
        }
        a = (centerIndex - 1 - maxLen) / 2;
        b = a + maxLen;
        
        out.println(maxLen);
        out.println(str.substring(inds[a], inds[b - 1] + 1));
        out.close();
    }
    
    static String preProcess(String s) {
        int n = s.length();
        if(n == 0) {
            return "^$";
        }
        StringBuilder ret = new StringBuilder("^");
        for(int i = 0; i < n; i++) {
            ret.append('#');
            ret.append(s.charAt(i));
        }
        ret.append("#$");
        return ret.toString();
    }
}