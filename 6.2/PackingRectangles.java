/*
ID: plutonj1
LANG: JAVA
TASK: packrec
*/

import java.io.*;
import java.util.*;

class packrec {
    static String name = "packrec";
    static BufferedReader in;
    static PrintWriter out;
    
    static Set<int[]> dimensions = new TreeSet<>((a, b) -> a[0] - b[0]);
    static int ind[] = new int[4], ws[] = new int[4], hs[] = new int[4], max = 10000;

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        for(int i = 0; i < 4; i++) {
            input = new StringTokenizer(in.readLine());
            ws[i] = Integer.parseInt(input.nextToken());
            hs[i] = Integer.parseInt(input.nextToken());
        }
        solve(0);
        
        out.println(max);
        for(int[] d : dimensions) {
            out.println(d[0] + " " + d[1]);
        }
        out.close();
    }
    
    static void solve(int d) {
        if(d == 4) {
            get();
            return;
        }
        next: for(int i = 0; i < 4; i++) {
            // check if i is used
            for(int j = 0; j < d; j++) {
                if(ind[j] == i) {
                    continue next;
                }
            }
            ind[d] = i;
            solve(d + 1);
        }
    } 
    
    static void get() {
        for(int i = 0; i < 16; i++) {
            int aw = (i & 8) > 0 ? hs[ind[0]] : ws[ind[0]], ah = (i & 8) > 0 ? ws[ind[0]] : hs[ind[0]];
            int bw = (i & 4) > 0 ? hs[ind[1]] : ws[ind[1]], bh = (i & 4) > 0 ? ws[ind[1]] : hs[ind[1]];
            int cw = (i & 2) > 0 ? hs[ind[2]] : ws[ind[2]], ch = (i & 2) > 0 ? ws[ind[2]] : hs[ind[2]];
            int dw = (i & 1) > 0 ? hs[ind[3]] : ws[ind[3]], dh = (i & 1) > 0 ? ws[ind[3]] : hs[ind[3]];
            int w, h;
            // ??????? garbage case work
            // in the given 6 possible layouts, 4 and 5 are equivalent on the aspect of the dimensions of the packing rectangle
            w = aw + bw + cw + dw;
            h = max(ah, bh, ch, dh);
            check(w, h);
            if(dh > bw + cw) {
                w = max(aw + bw + cw, dh);
                h = max(ah, bh, ch) + dw;
                check(w, h);
            }
            if(dh > bw) {
                w = max(aw + bw, dh) + cw;
                h = max(max(ah, bh) + dw, ch);
                check(w, h);
            }
            w = aw + max(bw, dw) + cw;
            h = max(ah, bh + dh, ch);
            check(w, h);
            if(ch >= dh) {
				if(ah + dh > ch && aw <= dw) {
					w = max(aw + bh, cw + dw);
				}
			} else if(bw + ch <= dh) {
				w = max(max(bh, cw) + dw, aw);
			} else {
				w = max(aw, dw) + max(bh, cw);
			}
			h = max(ah + dh, bw + ch);
            check(w, h);
        }
    }
    
    static void check(int w, int h) {
        int a = w * h;
        if(a <= max) {
            if(a < max) {
                dimensions.clear();
                max = a;
            }
            dimensions.add(new int[] {Math.min(w, h), Math.max(w, h)});
        }
    }
    
    static int max(int... n) {
        return max(n, 0);
    }
    
    static int max(int[] n, int i) {
        if(i == n.length - 1) {
            return n[i];
        } else if(i == n.length - 2) {
            return Math.max(n[i], n[i + 1]);
        } else {
            return Math.max(n[i], max(n, i + 1));
        }
    }
}