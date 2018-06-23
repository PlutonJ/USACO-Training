/*
ID: plutonj1
LANG: JAVA
TASK: starry
*/

import java.io.*;
import java.util.*;

class starry {
	static String name = "starry";
	static BufferedReader in;
	static PrintWriter out;
    
    static int W, H;
    static boolean[][] map;
    static char[][] res;
    static List<Cluster> clusters = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        W = Integer.parseInt(in.readLine());
        H = Integer.parseInt(in.readLine());
        map = new boolean[H][W];
        res = new char[H][W];
        for(int i = 0; i < H; i++) {
            String cheese = in.readLine();
            for(int j = 0; j < W; j++) {
                map[i][j] = cheese.charAt(j) == '1' ? true : false;
                res[i][j] = '0';
            }
        }
        for(int i = 0; i < H; i++) {
            for(int j = 0; j < W; j++) {
                cluster(j, i);
            }
        }
		
        for(int i = 0; i < H; i++) {
            out.println(new String(res[i]));
        }
		out.close();
	}
    
    static void cluster(int x, int y) {
        if(!map[y][x]) {
            return;
        } else {
            List<V> clusterList = cluster(new V(x, y), new HashSet<>());
            int minX = 100, minY = 100, maxX = 0, maxY = 0;
            for(V v : clusterList) {
                if(v.x < minX) {
                    minX = v.x;
                }
                if(v.x > maxX) {
                    maxX = v.x;
                }
                if(v.y < minY) {
                    minY = v.y;
                }
                if(v.y > maxY) {
                    maxY = v.y;
                }
            }
            boolean[][] cluster = new boolean[maxY - minY + 1][maxX - minX + 1];
            for(V v : clusterList) {
                cluster[v.y - minY][v.x - minX] = true;
            }
            for(Cluster c : clusters) {
                if(c.equals(cluster)) {
                    for(V v : clusterList) {
                        map[v.y][v.x] = false;
                        res[v.y][v.x] = c.c;
                    }
                    return;
                }
            }
            Cluster c = new Cluster(cluster);
            clusters.add(c);
            for(V v : clusterList) {
                map[v.y][v.x] = false;
                res[v.y][v.x] = c.c;
            }
        }
    }
    
    // flood fill
    static List<V> cluster(V v, Set<V> visited) {
        List<V> cluster = new ArrayList<>();
        if(map[v.y][v.x]) {
            cluster.add(v);
            visited.add(v);
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(i != 0 || j != 0) {
                        V vec = new V(v.x + i, v.y + j);
                        if(!visited.contains(vec) && vec.legal()) {
                            cluster.addAll(cluster(vec, visited));
                        }
                    }
                }
            }
        }
        return cluster;
    }
}

class Cluster {
    static char id = 'a';
    
    char c;
    boolean[][][] shapes;
    
    Cluster(boolean[][] shape) {
        c = id++;
        shapes = new boolean[8][][];
        shapes[0] = shape;
        shapes[1] = reflectX(shapes[0]);
        shapes[2] = reflectY(shapes[0]);
        shapes[3] = reflectY(shapes[1]);
        shapes[4] = transpose(shapes[0]);
        shapes[5] = transpose(shapes[1]);
        shapes[6] = transpose(shapes[2]);
        shapes[7] = transpose(shapes[3]);
        /*for(boolean[][] sh : shapes) {
            print(sh);
        }*/
    }
    
    boolean equals(boolean[][] shape) {
        List<boolean[][]> orientations = new ArrayList<>(4);
        if(shape.length == shapes[0].length && shape[0].length == shapes[0][0].length) {
            for(int i = 0; i < 4; i++) {
                orientations.add(shapes[i]);
            }
        }
        if(shape.length == shapes[0][0].length && shape[0].length == shapes[0].length) {
            for(int i = 4; i < 8; i++) {
                orientations.add(shapes[i]);
            }
        }
        if(orientations.size() == 0) {
            return false;
        }
        for(int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[i].length; j++) {
                for(int k = orientations.size() - 1; k >= 0; k--) {
                    // if mismatch remove from pool
                    if(shape[i][j] != orientations.get(k)[i][j]) {
                        orientations.remove(k);
                    }
                }
            }
        }
        // return if there's any match
        return orientations.size() > 0;
    }
    
    static boolean[][] reflectX(boolean[][] shape) {
        boolean[][] res = new boolean[shape.length][shape[0].length];
        for(int i = 0; i < res.length; i++) {
            for(int j = 0; j < res[i].length; j++) {
                res[i][j] = shape[res.length - i - 1][j];
            }
        }
        return res;
    }
    
    static boolean[][] reflectY(boolean[][] shape) {
        boolean[][] res = new boolean[shape.length][shape[0].length];
        for(int i = 0; i < res.length; i++) {
            for(int j = 0; j < res[i].length; j++) {
                res[i][j] = shape[i][res[i].length - j - 1];
            }
        }
        return res;
    }
    
    static boolean[][] transpose(boolean[][] shape) {
        boolean[][] res = new boolean[shape[0].length][shape.length];
        for(int i = 0; i < res.length; i++) {
            for(int j = 0; j < res[i].length; j++) {
                res[i][j] = shape[j][i];
            }
        }
        return res;
    }
    
    static void print(boolean[][] cluster) {
        for(int i = 0; i < cluster.length; i++) {
            for(int j = 0; j < cluster[i].length; j++) {
                if(cluster[i][j]) {
                    System.out.print('x');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
}

class V {
    int x, y;
    
    V(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    boolean legal() {
        return x >= 0 && x < starry.W && y >= 0 && y < starry.H;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof V)) {
            return false;
        } else {
            V v = (V)o;
            return x == v.x && y == v.y;
        }
    }
    
    @Override
    public int hashCode() {
        return x * y;
    }
}