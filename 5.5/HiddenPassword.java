/*
ID: plutonj1
LANG: JAVA
TASK: hidden
*/

import java.io.*;
import java.util.*;

class hidden {
	static String name = "hidden";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        
        int L = Integer.parseInt(in.readLine());
        String cheese;
        String S = in.readLine();
        while((cheese = in.readLine()) != null && cheese.length() > 0) {
            S += cheese;
        }
        // brute force times out on test case 8
        /*int ind = 0;
        cheese = S;
        S += S;
        for(int i = 1; i < L; i++) {
            String sub = S.substring(i, i + L);
            if(sub.compareTo(cheese) < 0) {
                cheese = sub;
                ind = i;
            }
        }
        
        out.println(ind);*/
        
        // times out on test case 11
        /*
        S += S;
        char minChar = 'z', ref[] = S.toCharArray();
		List<Integer> candidates = new ArrayList<>();
        for(int i = 0; i < L; i++) {
            if(ref[i] == minChar) {
                candidates.add(i);
            } else if(ref[i] < minChar) {
                minChar = ref[i];
                candidates.clear();
                candidates.add(i);
            }
        }
        for(int i = 1; i < L; i++) {
            if(candidates.size() == 1) {
                break;
            }
            minChar = 'z';
            List<Integer> next = new ArrayList<>();
            for(int j : candidates) {
                if(ref[j + i] == minChar) {
                    next.add(j);
                } else if(ref[j + i] < minChar) {
                    minChar = ref[j + i];
                    next.clear();
                    next.add(j);
                }
            }
            candidates.clear();
            candidates.addAll(next);
        }
        
        out.println(candidates.get(0));
        */
        
        S += S;
        char[] ref = S.toCharArray();
        int i = 0, j = 1, c;
        next: while(j < L) {
            for(c = 0; c < L; c++) {
                if(ref[i + c] > ref[j + c]) {
                    i = j++;
                    continue next;
                } else if(ref[i + c] < ref[j + c]) {
                    j += c + 1;
                    continue next;
                }
            }
            j++;
        }
        
        out.println(i);
		out.close();
	}
}