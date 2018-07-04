/*
ID: plutonj1
LANG: JAVA
TASK: vans
*/

// ???????????????????

import java.io.*;
import java.util.*;
import java.math.*;

class vans {
	static String name = "vans";
	static BufferedReader in;
	static PrintWriter out;
    
    static int[] delSide = new int[998];
    
    static {
        Arrays.fill(delSide, -1);
        delSide[0] = delSide[1] = 1;
    }
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
		BigInteger[] f = new BigInteger[1001], g = new BigInteger[1001];
        g[1] = BigInteger.valueOf(2);
        g[2] = BigInteger.valueOf(2);
        g[3] = BigInteger.valueOf(8);
        f[1] = BigInteger.valueOf(0);
        f[2] = BigInteger.valueOf(2);
        f[3] = BigInteger.valueOf(4);
        for(int i = 4; i <= N; i++) {
            g[i] = f[i - 1].multiply(BigInteger.valueOf(2)).add(g[i - 1]).add(g[i - 2]).subtract(g[i - 3]);
            f[i] = f[i - 1].add(g[i - 1]);
        }
        
        out.println(f[N]);
		out.close();
	}
}