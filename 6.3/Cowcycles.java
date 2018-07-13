/*
ID: plutonj1
LANG: JAVA
TASK: cowcycle
*/

// I thought this would be hard???
// brute force british museum + prune situations where the max ratio < 3 * the min ratio

import java.io.*;
import java.util.*;

class cowcycle {
    static String name = "cowcycle";
    static BufferedReader in;
    static PrintWriter out;
    
    static int F, R, F1, F2, R1, R2;
    static int[] f, r, ff, rr;
    static double min = Double.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        F = Integer.parseInt(input.nextToken());
        R = Integer.parseInt(input.nextToken());
        input = new StringTokenizer(in.readLine());
        F1 = Integer.parseInt(input.nextToken());
        F2 = Integer.parseInt(input.nextToken());
        R1 = Integer.parseInt(input.nextToken());
        R2 = Integer.parseInt(input.nextToken());
        f = new int[F];
        r = new int[R];
        ff = new int[F];
        rr = new int[R];
        search(0);
        
        out.print(ff[0]);
        for(int i = 1; i < F; i++) {
            out.print(" " + ff[i]);
        }
        out.println("");
        out.print(rr[0]);
        for(int i = 1; i < R; i++) {
            out.print(" " + rr[i]);
        }
        out.println("");
        out.close();
    }
    
    static void search(int depth) {
        if(depth == F + R) {
            double var = calc();
            if(var < min) {
                min = var;
                ff = Arrays.copyOfRange(f, 0, F);
                rr = Arrays.copyOfRange(r, 0, R);
            }
        } else if(depth == 0) {
            for(f[0] = F1; f[0] <= F2 - F + 1; f[0]++) {
                search(depth + 1);
            }
        } else if(depth < F) {
            for(f[depth] = f[depth - 1] + 1; f[depth] <= F2 - (F - depth) + 1; f[depth]++) {
                search(depth + 1);
            }
        } else if(depth == F) {
            for(r[0] = R1; r[0] <= R2 - R + 1; r[0]++) {
                search(depth + 1);
            }
        } else {
            int dep = depth - F;
            for(r[dep] = r[dep - 1] + 1; r[dep] <= R2 - (R - dep) + 1; r[dep]++) {
                search(depth + 1);
            }
        }
    }
    
    static double calc() {
        if(f[F - 1] * r[R - 1] < 3 * f[0] * r[0]) {
            return Double.MAX_VALUE;
        }
        int c = 0;
        double[] ratio = new double[F * R];
        for(int i = 0; i < F; i++) {
            for(int j = 0; j < R; j++) {
                ratio[c++] = (double)f[i] / r[j];
            }
        }
        Arrays.sort(ratio);
        double[] diff = new double[F * R - 1];
        double mean = 0;
        for(int i = 0; i < diff.length; i++) {
            diff[i] = ratio[i + 1] - ratio[i];
            mean += diff[i];
        }
        mean /= diff.length;
        double var = 0;
        for(int i = 0; i < diff.length; i++) {
            double d = diff[i] - mean;
            var += d * d;
        }
        var /= diff.length;
        return var;
    }
}