/*
ID: plutonj1
LANG: JAVA
TASK: fence3
*/

// initially a simulated annealing
// by changing the acceptance probability function from exp((e - e') / t) to e - e', 
// this algorithm has essentially turned into a hill climbing algorithm yet it still passed all the cases

import java.io.*;
import java.util.*;

class fence3 {
    static String name = "fence3";
    static BufferedReader in;
    static PrintWriter out;
    
    static int x = 0, y = 1, x0 = 0, y0 = 1, x1 = 2, y1 = 3;
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        int F = Integer.parseInt(in.readLine());
        int[][] f = new int[F][4];
        int xSum = 0, ySum = 0;
        for(int i = 0; i < F; i++) {
            input = new StringTokenizer(in.readLine());
            xSum += f[i][x0] = Integer.parseInt(input.nextToken()) * 10;
            ySum += f[i][y0] = Integer.parseInt(input.nextToken()) * 10;
            xSum += f[i][x1] = Integer.parseInt(input.nextToken()) * 10;
            ySum += f[i][y1] = Integer.parseInt(input.nextToken()) * 10;
        }
        int loop = 100;
        double t = 500, a = 0.99, minT = 0.01, sx = (double)xSum / F / 2, sy = (double)ySum / F / 2, e = 0, e_;
        //System.out.println(sx + ", " + sy);
        for(int[] ff : f) {
            e += dist(sx, sy, ff);
        }
        while(t > minT) {
            for(int i = 0; i < loop; i++) {
                double nx = sx + (Math.random() - 0.5) * t;
                double ny = sy + (Math.random() - 0.5) * t;
                e_ = 0;
                for(int[] ff : f) {
                    e_ += dist(nx, ny, ff);
                }
                if(p(e, e_, t) > Math.random()) {
                    sx = nx;
                    sy = ny;
                    e = e_;
                }
            }
            t *= a;
        }
        
        //System.out.println(dist(sx, sy, f[0]) + " " + dist(sx, sy, f[1]) + " " + dist(sx, sy, f[2]));
        out.println(((double)Math.round(sx) / 10) + " " + ((double)Math.round(sy) / 10) + " " + ((double)Math.round(e) / 10));
        out.close();
    }
    
    static double p(double e, double e_, double t) {
        return e - e_;
        //return Math.exp((e - e_) / t);
    }
    
    static double dist(double px, double py, int[] f) {
        double[] vf = {f[x1] - f[x0], f[y1] - f[y0]};
        if(vf[x] == 0) {
            if(in(py, f[y0], f[y1])) {
                return Math.abs(px - f[x0]);
            } else {
                return Math.min(mag(px - f[x0], py - f[y0]), mag(px - f[x1], py - f[y1]));
            }
        } else {
            if(in(px, f[x0], f[x1])) {
                return Math.abs(py - f[y0]);
            } else {
                return Math.min(mag(px - f[x0], py - f[y0]), mag(px - f[x1], py - f[y1]));
            }
        }
    }
    
    static boolean in(double r, double a, double b) {
        return a > b ? in(r, b, a) : a <= r && r <= b;
    }
    
    static double mag(double vx, double vy) {
        return Math.sqrt(vx * vx + vy * vy);
    }
}