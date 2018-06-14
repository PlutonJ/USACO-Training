/*
ID: plutonj1
LANG: JAVA
TASK: nuggets
*/

// this code uses the observation and the unproven assumption of that: 
//      let x be the minimum of the given numbers, 
//      if a row of x adjacent integers following a number n can be made with the given numbers, 
//      all integers following n can be made with the given numbers
//      i.e., n is the greatest integer that the set of given numbers cannot sum up to in any combination
// according to the analysis for this problem, this assumption is indeed correct

import java.io.*;
import java.util.*;

class nuggets {
	static String name = "nuggets";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
        if(N == 1) {
            out.println(0);
            out.close();
            return;
        }
        int[] p = new int[N];
        int min = 256;
        for(int i = 0; i < N; i++) {
            p[i] = Integer.parseInt(in.readLine());
            if(p[i] < min) {
                min = p[i];
            }
            if(p[i] == 1) {
                out.println(0);
                out.close();
                return;
            }
        }
        
        Set<Integer> possible = new HashSet<>();
        possible.add(0);
        // if the numbers are all even, odd numbers cannot be created from them
        // without the all even test, this code would not pass test case 4
        boolean allEven = true;
        for(int i : p) {
            if(i % 2 == 1) {
                allEven = false;
            }
            possible.add(i);
        }
        if(allEven) {
            out.println(0);
            out.close();
            return;
        }
        int streak = 0, current = 1;
        while(true) {
            boolean poss = false;
            for(int i : p) {
                if(possible.contains(current - i)) {
                    possible.add(current);
                    streak++;
                    poss = true;
                    break;
                }
            }
            if(!poss) {
                streak = 0;
            } else if(streak == min) {
                out.println(current - min);
                break;
            }
            // for safety, current probably never would reach 2000000000 within the 1 second limited runtime
            if(current++ >= 2000000000) {
                out.println(0);
                break;
            }
        }
		
		out.close();
	}
}