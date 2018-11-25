/*
ID: plutonj1
LANG: JAVA
TASK: jpol
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

class jpol {
    static String name = "jpol";
    static BufferedReader $in;
    static PrintWriter $out;
    static StringTokenizer input;
    
    public static void main(String[] args) throws IOException {
        $in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        $out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        
        int K = readInt(), c[][] = new int[3 * K][2], ind = 0, c1 = 0, c2 = 0, c2s[] = new int[K], c2i = 0;
        for(int i = 0; i < 3 * K; i++) {
            c[i] = new int[] {i + 1, readInt()};
        }
        sort(c, (a, b) -> a[1] == b[1] ? a[0] - b[1] : b[1] - a[1]);
        while(ind < 2 * K) {
            if(c1 <= c2) {
                println(c[ind][0]);
                c1 += c[ind++][1];
            } else {
                c2s[c2i++] = c[ind][0];
                c2 += c[ind++][1];
            }
        }
        for(int c2c : c2s) {
            println(c2c);
        }
        while(ind < 3 * K) {
            println(c[ind++][0]);
        }
        
        close();
    }
    
    static final int IMAX = 2147483647;
    static final long LMAX = 9223372036854775807L;
    static void read() throws IOException {input = new StringTokenizer($in.readLine());}
    static String readLine() throws IOException {return $in.readLine();}
    static int nextInt() {return Integer.parseInt(input.nextToken());}
    static int readInt() throws IOException {return Integer.parseInt($in.readLine());}
    static void print(Object o) {$out.print(o);}
    static void println(Object o) {$out.println(o);}
    static void close() {$out.close();}
}