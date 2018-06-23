/*
ID: plutonj1
LANG: JAVA
TASK: fc
*/

// implementation of the USACO convex-hull algorithm

import java.io.*;
import java.util.*;

class fc {
	static String name = "fc";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
        double[] x = new double[N], y = new double[N];
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            x[i] = Double.parseDouble(input.nextToken());
            y[i] = Double.parseDouble(input.nextToken());
        }
        double midX = 0, midY = 0;
        for(int i = 0; i < N; i++) {
            midX += x[i] / N;
            midY += y[i] / N;
        }
        Vector[] perm = new Vector[N];
        for(int i = 0; i < N; i++) {
            perm[i] = new Vector();
            perm[i].x = x[i];
            perm[i].y = y[i];
            perm[i].angle = Math.atan2(y[i] - midY, x[i] - midX);
        }
        
        Arrays.sort(perm, Comparator.comparingDouble(Vector::a));
        Vector[] hull = new Vector[N];
        hull[0] = perm[0];
        hull[1] = perm[1];
        int hullPos = 2;
        for(int i = 2; i < N - 1; i++) {
            Vector p = perm[i];
            while(hullPos > 1 && zCross(hull[hullPos - 2].sub(hull[hullPos - 1]), hull[hullPos - 1].sub(p)) < 0) {
                hullPos--;
            }
            hull[hullPos++] = p;
        }
        
        Vector p = perm[N - 1];
        while(hullPos > 1 && zCross(hull[hullPos - 2].sub(hull[hullPos - 1]), hull[hullPos - 1].sub(p)) < 0) {
            hullPos--;
        }
        
        int hullStart = 0;
        boolean flag;
        do {
            flag = false;
            if(hullPos - hullStart >= 2 && zCross(p.sub(hull[hullPos - 1]), hull[hullStart].sub(p)) < 0) {
                p = hull[--hullPos];
                flag = true;
            }
            if(hullPos - hullStart >= 2 && zCross(hull[hullStart].sub(p), hull[hullStart + 1].sub(hull[hullStart])) < 0) {
                hullStart++;
                flag = true;
            }
        } while(flag);
        hull[hullPos++] = p;
        
        double len = 0;
        for(int i = hullStart; i < hullPos - 1; i++) {
            len += dist(hull[i], hull[i + 1]);
        }
        len += dist(hull[hullPos - 1], hull[hullStart]);
		
        out.println(String.format("%.2f", len));
		out.close();
	}
    
    static double zCross(Vector v0, Vector v1) {
        return v0.x * v1.y - v1.x * v0.y;
    }
    
    static double dist(Vector v0, Vector v1) {
        return Math.sqrt((v1.x - v0.x) * (v1.x - v0.x) + (v1.y - v0.y) * (v1.y - v0.y));
    }
}

class Vector {
    double x, y, angle;
    
    double a() {
        return angle;
    }
    
    Vector sub(Vector v) {
        Vector res = new Vector();
        res.x = x - v.x;
        res.y = y - v.y;
        return res;
    }
}