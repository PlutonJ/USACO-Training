/*
ID: plutonj1
LANG: JAVA
TASK: milk4
*/

// USACO solution

import java.io.*;
import java.util.*;

class milk4 {
	static String name = "milk4";
	static BufferedReader in;
	static PrintWriter out;
    
    static int P;
    static int[] minPails, lastPail, nLast;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int Q = Integer.parseInt(in.readLine());
        P = Integer.parseInt(in.readLine());
        int[] p = new int[P];
        for(int i = 0; i < P; i++) {
            p[i] = Integer.parseInt(in.readLine());
            if(p[i] == 1) {
                out.println("1 1");
                out.close();
                return;
            }
        }
        Arrays.sort(p);
        for(int i = 0; i < P / 2; i++) {
            int swap = p[i];
            p[i] = p[P - i - 1];
            p[P - i - 1] = swap;
        }
        
        lastPail = new int[Q + 1];
        minPails = new int[Q + 1];
        nLast = new int[Q + 1];
        Arrays.fill(lastPail, -1);
        Arrays.fill(minPails, 101);
        Arrays.fill(nLast, -1);
        minPails[0] = 0;
        for(int i = 0; i < P; i++) {
            int[] tempMP = new int[Q + 1], tempLP = new int[Q + 1], tempNL = new int[Q + 1];
            for(int j = 0; j <= Q; j++) {
                tempMP[j] = minPails[j];
                tempLP[j] = lastPail[j];
                tempNL[j] = nLast[j];
            }
            for(int j = p[i]; j <= Q; j++) {
                int prev = j - p[i];
                if(tempMP[prev] <= 100) {
                    if(tempLP[prev] == p[i]) {
                        tempLP[j] = p[i];
                        tempMP[j] = tempMP[prev];
                        tempNL[j] = tempNL[prev] + 1;
                    }
                    if(tempLP[prev] != p[i]) {
                        tempLP[j] = p[i];
                        tempMP[j] = tempMP[prev] + 1;
                        tempNL[j] = 1;
                    }
                    if(minPails[prev] <= 100 && (minPails[prev] + 1 < tempMP[j] || minPails[prev] + 1 == tempMP[j] && better(prev, j - tempNL[j] * p[i]))) {
                        tempLP[j] = p[i];
                        tempMP[j] = minPails[prev] + 1;
                        tempNL[j] = 1;
                    }
                }
            }
            for(int j = p[i]; j <= Q; j++) {
                if(tempMP[j] <= minPails[j]) {
                    minPails[j] = tempMP[j];
                    lastPail[j] = tempLP[j];
                    nLast[j] = tempNL[j];
                }
            }
        }
        int cur = Q, nFinal = minPails[Q];
        int[] finalSet = new int[nFinal];
        for(int j = 0; j < nFinal; j++) {
            finalSet[j] = lastPail[cur];
            cur -= lastPail[cur] * nLast[cur];
        }
        
        out.print(nFinal);
        for(int i = 0; i < nFinal; i++) {
            out.print(" " + finalSet[i]);
        }
        out.println("");
		out.close();
	}
    
    static boolean better(int a, int b) {
        while(a > 0 && b > 0) {
            if(lastPail[a] < lastPail[b]) {
                return true;
            }
            if(lastPail[a] > lastPail[b]) {
                return false;
            }
            a -= nLast[a] * lastPail[a];
            b -= nLast[b] * lastPail[b];
        }
        
        if(a > 0) {
            return false;
        }
        return true;
    }
}