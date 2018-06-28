/*
ID: plutonj1
LANG: JAVA
TASK: milk4
*/

// times out and gives wrong answer for test case 7

import java.io.*;
import java.util.*;

class milk4 {
	static String name = "milk4";
	static BufferedReader in;
	static PrintWriter out;
    
    static int P;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int Q = Integer.parseInt(in.readLine());
        P = Integer.parseInt(in.readLine());
        short[] p = new short[P];
        for(int i = 0; i < P; i++) {
            p[i] = Short.parseShort(in.readLine());
            if(p[i] == 1) {
                out.println("1 1");
                out.close();
                return;
            }
        }
        Arrays.sort(p);
        
        // executing the following commented code, n[Q] should(?) == the minimum number of pails required
        /*
        short[] n = new short[Q + 1], last = new short[Q + 1];
        for(int i = 0; i < P; i++) {
            n[p[i]] = 1;
            last[p[i]] = (short)i;
        }
        for(int i = 0; i < Q - 1; i++) {
            if(n[i] > 0) {
                if(i + p[last[i]] <= Q) {
                    if(n[i + p[last[i]]] == 0 || n[i] < n[i + p[last[i]]]) {
                        n[i + p[last[i]]] = n[i];
                    }
                }
                for(int j = last[i] + 1; j < P; j++) {
                    if(i + p[j] <= Q) {
                        if(n[i + p[j]] == 0 || n[i] + 1 < n[i + p[j]]) {
                            n[i + p[j]] = (short)(n[i] + 1);
                            last[i + p[j]] = (short)j;
                        }
                    }
                }
            }
        }
        */
        
        int[] dp = new int[Q + 1];
        List<TreeSet<Config>> configs = new ArrayList<>(Q + 1);
        for(int i = 0; i <= Q; i++) {
            configs.add(new TreeSet<>());
        }
        configs.get(0).add(new Config());
        for(int i = 0; i < Q - 1; i++) {
            for(Config c : configs.get(i)) {
                for(int j = 0; j < P; j++) {
                    if(i + p[j] <= Q) {
                        if(c.used[j] > 0) {
                            if(dp[i + p[j]] == 0 || dp[i] == dp[i + p[j]]) {
                                dp[i + p[j]] = dp[i];
                                Config conf = c.copy();
                                conf.used[j]++;
                                configs.get(i + p[j]).add(conf);
                            } else if(dp[i] < dp[i + p[j]]) {
                                configs.get(i + p[j]).clear();
                                dp[i + p[j]] = dp[i];
                                Config conf = c.copy();
                                conf.used[j]++;
                                configs.get(i + p[j]).add(conf);
                            }
                        } else {
                            if(dp[i + p[j]] == 0 || dp[i] + 1 == dp[i + p[j]]) {
                                dp[i + p[j]] = dp[i] + 1;
                                Config conf = c.copy();
                                conf.used[j]++;
                                configs.get(i + p[j]).add(conf);
                            } else if(dp[i + 1] < dp[i + p[j]]) {
                                configs.get(i + p[j]).clear();
                                dp[i + p[j]] = dp[i] + 1;
                                Config conf = c.copy();
                                conf.used[j]++;
                                configs.get(i + p[j]).add(conf);
                            }
                        }
                    }
                }
            }
        }
        /*for(int i = 0; i <= Q; i++) {
            System.out.println(i + ": " + configs.get(i));
        }*/
        
        out.print(dp[Q] + " ");
        short[] used = configs.get(Q).first().used;
        int i = -1, count = dp[Q] - 1;
        while(count --> 0) {
            while(used[++i] == 0) {}
            out.print(p[i] + " ");
        }
        while(used[++i] == 0) {}
        out.println(p[i]);
		out.close();
	}
}

class Config implements Comparable<Config> {
    short[] used = new short[milk4.P];
    
    Config copy() {
        Config c = new Config();
        for(int i = 0; i < milk4.P; i++) {
            c.used[i] = used[i];
        }
        return c;
    }
    
    // precondition: (Config)this and (Config)c has the same # of nonzeros in (short[])used
    // thus if one has a nonzero appearing before the other one, the one that has the nonzero has more priority
    @Override
    public int compareTo(Config c) {
        for(int i = 0; i < milk4.P; i++) {
            if(used[i] > 0 && c.used[i] == 0) {
                return -1;
            } else if(used[i] == 0 && c.used[i] > 0) {
                return 1;
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("<");
        for(int i = 0; i < milk4.P - 1; i++) {
            s.append(used[i] + " ");
        }
        s.append(used[milk4.P - 1]);
        s.append(">");
        return s.toString();
    }
}