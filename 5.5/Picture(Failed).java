/*
ID: plutonj1
LANG: JAVA
TASK: picture
*/

// abandoned attempt on doing the problem geometrically

import java.io.*;
import java.util.*;

class picture {
	static String name = "picture";
	static BufferedReader in;
	static PrintWriter out;
    
    static List<Rect> rects;
    static List<Seg> segs;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
        rects = new ArrayList<>(N);
        segs = new ArrayList<>();
        next: for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            Rect r = new Rect(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
            for(Rect rect : rects) {
                if(rect.contains(r)) {
                    continue next;
                }
            }
            add(new Seg(r, r.x0, r.y0, r.x1, r.y0));
            add(new Seg(r, r.x1, r.y0, r.x1, r.y1));
            add(new Seg(r, r.x1, r.y1, r.x0, r.y1));
            add(new Seg(r, r.x0, r.y1, r.x0, r.y0));
        }
		
		out.close();
	}
    
    static void add(Seg s) {
        for(Seg seg : segs) {
            
        }
    }
}

class Seg {
    Rect of;
    int x0, y0, x1, y1;
    
    Seg(Rect of, int x0, int y0, int x1, int y1) {
        this.of = of;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }
    
    int len() {
        return x0 == x1 ? Math.abs(y1 - y0) : Math.abs(x1 - x0);
    }
    
    static boolean overlap(Seg a, Seg b) {
        return overlap(a.x0, a.y0, a.x1, a.y1, b.x0, b.y0, b.x1, b.y1);
    }
    
    static boolean overlap(int ax, int ay, int bx, int by, int cx, int cy, int dx, int dy) {
        return (ax == bx && bx == cx && cx == dx) ? ay <= cy && by >= cy || cy <= ay && dy >= ay : (ay == by && by == cy && cy == dy) ? ax <= cx && bx >= cx || cx <= ax && dx >= ax : false;
    }
    
    static boolean intersect(Seg a, Seg b) {
        return intersect(a.x0, a.y0, a.x1, a.y1, b.x0, b.y0, b.x1, b.y1);
    }
    
    static boolean intersect(int ax, int ay, int bx, int by, int cx, int cy, int dx, int dy) {
        return ((bx - ax) * (cy - ay) - (cx - ax) * (by - ay)) * ((bx - ax) * (dy - ay) - (dx - ax) * (by - ay)) > 0 && ((dx - cx) * (ay - cy) - (ax - cx) * (dy - cy)) * ((dx - cx) * (by - cy) - (bx - cx) * (dy - cy)) > 0;
    }
}

class Rect {
    int x0, y0, x1, y1;
    
    Rect(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }
    
    boolean contains(Rect r) {
        return r.x0 >= x0 && r.y0 >= y0 && r.x1 <= x1 && r.y1 <= y1;
    }
    
    boolean contains(Seg s) {
        int rayX0, rayY0, rayX1, rayY1;
        if(s.x0 == s.x1) {
            rayX0 = s.x0;
            rayY0 = (s.y0 + s.y1) / 2;
            rayX1 = rayX0 + 20000;
            rayY1 = rayY0;
        } else {
            rayX0 = (s.x0 + s.x1) / 2;
            rayY0 = s.y0;
            rayX1 = rayX0;
            rayY1 = rayY0 + 20000;
        }
        int count = 0;
        if(Seg.intersect(rayX0, rayY0, rayX1, rayY1, x0, y0, x1, y0)) {
            count++;
        }
        if(Seg.intersect(rayX0, rayY0, rayX1, rayY1, x1, y0, x1, y1)) {
            count++;
        }
        if(Seg.intersect(rayX0, rayY0, rayX1, rayY1, x1, y1, x0, y1)) {
            count++;
        }
        if(Seg.intersect(rayX0, rayY0, rayX1, rayY1, x0, y1, x0, y0)) {
            count++;
        }
        return count % 2 == 1;
    }
}