/*
ID: plutonj1
LANG: JAVA
TASK: fence9
*/

// scan line by line bottom-up
// start from the widest line which is y = 1, then go up and use the slopes to determine when to subtract lattice points from each line
// good to read: Pick's theorem
// https://www.byvoid.com/zhs/blog/usaco-343-electric-fence

import java.io.*;
import java.util.*;

class fence9 {
	static String name = "fence9";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(input.nextToken()), m = Integer.parseInt(input.nextToken()), p = Integer.parseInt(input.nextToken());
        // not enough precision for test case 7
        /*double m0 = (double)n / m, m1 = (double)(p - n) / m;
        double left = m0, right = m1;*/
        
        // left and right are numerators of the (double)left and (double)right in the previous attempt
        int left = n, d = p - n, right = d;
        int count = 0;
        for(int i = 1; i < m; i++) {
            /*count += p - Math.floor(left) - Math.floor(right) - 1;
            left += m0;
            right += m1;*/
            // the minus 1 is needed for some reasons that is not fully understood but it works
            // if Math.floor was not used it gives wrong answers because of the potential negativity of right
            count += p - Math.floor((double)left / m) - Math.floor((double)right/ m) - 1;
            left += n;
            right += d;
        }
		
        out.println(count);
		out.close();
	}
}