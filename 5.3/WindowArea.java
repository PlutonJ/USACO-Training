/*
ID: plutonj1
LANG: JAVA
TASK: window
*/

import java.io.*;
import java.util.*;

class window {
	static String name = "window";
	static BufferedReader in;
	static PrintWriter out;
    
    static Map<Character, WindowC> map = new HashMap<>();
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        String cheese;
        while((cheese = in.readLine()) != null && cheese.length() > 0) {
            char op = cheese.charAt(0);
            switch(op) {
                case 'w': 
                    input = new StringTokenizer(cheese.substring(4, cheese.length() - 1), ",");
                    w(cheese.charAt(2), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
                break;
                case 't': 
                    t(cheese.charAt(2));
                break;
                case 'b': 
                    b(cheese.charAt(2));
                break;
                case 'd': 
                    d(cheese.charAt(2));
                break;
                case 's': 
                    s(cheese.charAt(2));
                break;
            }
        }
		
		out.close();
	}
    
    static void w(char I, int x, int y, int X, int Y) {
        WindowC window = new WindowC(x, y, X, Y);
        for(Map.Entry<Character, WindowC> e : map.entrySet()) {
            WindowC win = e.getValue();
            win.top.add(window);
        }
        map.put(I, window);
    }
    
    static void t(char I) {
        WindowC window = map.get(I);
        window.top.clear();
        for(Map.Entry<Character, WindowC> e : map.entrySet()) {
            WindowC win = e.getValue();
            if(win != window) {
                win.top.add(window);
            }
        }
    }
    
    static void b(char I) {
        WindowC window = map.get(I);
        for(Map.Entry<Character, WindowC> e : map.entrySet()) {
            WindowC win = e.getValue();
            if(win != window) {
                window.top.add(win);
                win.top.remove(window);
            }
        }
    }
    
    static void d(char I) {
        WindowC window = map.get(I);
        map.remove(I);
        for(Map.Entry<Character, WindowC> e : map.entrySet()) {
            WindowC win = e.getValue();
            win.top.remove(window);
        }
    }
    
    static void s(char I) {
        WindowC window = map.get(I);
        Set<AABB> vis = new HashSet<>();
        vis.add(window.org);
        for(WindowC win : window.top) {
            Set<AABB> addSet = new HashSet<>(), removeSet = new HashSet<>();
            for(AABB v : vis) {
                if(v.collidesWith(win.org)) {
                    removeSet.add(v);
                    addSet.addAll(win.org.block(v));
                }
            }
            vis.removeAll(removeSet);
            vis.addAll(addSet);
        }
        int area = 0;
        for(AABB v : vis) {
            //System.out.println(v);
            area += v.area();
        }
        out.println(String.format("%.3f", 100.0 * area / window.org.area()));
    }
}

class WindowC {
    AABB org;
    Set<WindowC> top = new HashSet<>();
    
    WindowC(int x, int y, int X, int Y) {
        int minX = Math.min(x, X), maxX = Math.max(x, X), minY = Math.min(y, Y), maxY = Math.max(y, Y);
        org = new AABB(minX, minY, maxX, maxY);
    }
}

class AABB {
    int x0, y0, x1, y1;
    
    AABB(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }
    
    int area() {
        return (x1 - x0) * (y1 - y0);
    }
    
    boolean collidesWith(AABB a) {
        if(x0 >= a.x1 || a.x0 >= x1) {
            return false;
        }
        if(y0 >= a.y1 || a.y0 >= y1) {
            return false;
        }
        return true;
    }
    
    // ultimate jank
    List<AABB> block(AABB vis) {
        List<AABB> res = new ArrayList<>();
        List<AABB> tryHarder = new ArrayList<>();
        int overlap = (Math.min(vis.x1, x1) - Math.max(vis.x0, x0)) * (Math.min(vis.y1, y1) - Math.max(vis.y0, y0));
        tryHarder.add(vis);
        while(tryHarder.size() > 0) {
            List<AABB> q = new ArrayList<>();
            for(AABB v : tryHarder) {
                int x = x0;
                if(v.x0 >= x0) {
                    x = Math.min(v.x1, x1);
                }
                int y = y0;
                if(v.y0 >= y0) {
                    y = Math.min(v.y1, y1);
                }
                //System.out.println(x + ", " + y);
                AABB topLeft = new AABB(v.x0, v.y0, x, y), topRight = new AABB(x, v.y0, v.x1, y), 
                     bottomLeft = new AABB(v.x0, y, x, v.y1), bottomRight = new AABB(x, y, v.x1, v.y1);
                res.add(topLeft);
                res.add(topRight);
                res.add(bottomLeft);
                res.add(bottomRight);
                for(int i = 3; i >= 0; i--) {
                    int a = res.get(i).area();
                    if(collidesWith(res.get(i))) {
                        if(a > 0 && a != overlap) {
                            AABB buf = res.remove(i);
                            //System.out.println("added to q: " + buf);
                            q.add(buf);
                        } else {
                            res.remove(i);
                        }
                    } else if(a == 0) {
                        res.remove(i);
                    }
                }
            }
            tryHarder.clear();
            tryHarder.addAll(q);
        }
        return res;
    }
    
    @Override
    public String toString() {
        return "<" + x0 + ", " + y0 + "> -> <" + x1 + ", " + y1 + ">";
    }
}