/*
ID: plutonj1
LANG: JAVA
TASK: buylow
*/

// official solution too good for me

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
        int[] p = new int[N];
        int ind = 0;
        String cheese;
        while((cheese = in.readLine()) != null && cheese.length() > 0) {
            input = new StringTokenizer(cheese);
            while(input.hasMoreTokens()) {
                p[ind++] = Integer.parseInt(input.nextToken());
            }
        }
        
        int[] dp = new int[N];
        int maxLen = 1;
        dp[0] = 1;
        for(int i = 1; i < N; i++) {
            int max = 0;
            for(int j = i - 1; j >= 0; j--) {
                if(p[i] < p[j] && dp[j] > max) {
                    max = dp[j];
                }
            }
            dp[i] = max + 1;
            if(dp[i] > maxLen) {
                maxLen = dp[i];
            }
        }
        Int[] count = new Int[N];
        for(int i = 0; i < N; i++) {
            if(dp[i] == 1) {
                count[i] = new Int(1);
            } else {
                count[i] = new Int(0);
                int last = -1;
                int len = dp[i] - 1;
                for(int j = i - 1; j >= 0; j--) {
                    if(dp[j] == len && p[j] > p[i] && p[j] != last) {
                        count[i].add(count[j]);
                        last = p[j];
                    }
                }
            }
        }
        Int c = new Int(0);
        int last = -1;
        for(int i = N - 1; i >= 0; i--) {
            if(dp[i] == maxLen && p[i] != last) {
                c.add(count[i]);
                last = p[i];
            }
        }
        
        out.println(maxLen + " " + c.toString());
		out.close();
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