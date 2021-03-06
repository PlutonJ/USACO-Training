/*
ID: plutonj1
LANG: JAVA
TASK: 
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

class TrainingTemplate {
    static String name = "";
    static BufferedReader $in;
    static PrintWriter $out;
    static StringTokenizer input;
    
    public static void main(String[] args) throws IOException {
        $in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        $out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        
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