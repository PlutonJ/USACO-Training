/*
ID: plutonj1
LANG: JAVA
TASK: snail
*/

// dfs all possible routes

import java.io.*;
import java.util.*;

class snail {
	static String name = "snail";
	static BufferedReader in;
	static PrintWriter out;
    
    static int N, max = 0;
    static boolean[][] map, visited;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        N = Integer.parseInt(input.nextToken());
        map = new boolean[N][N];
        visited = new boolean[N][N];
        int B = Integer.parseInt(input.nextToken());
        while(B --> 0) {
            String cheese = in.readLine();
            map[Integer.parseInt(cheese.substring(1)) - 1][cheese.charAt(0) - 'A'] = true;
        }
        
        visited[0][0] = true;
        max = Math.max(dfs(new State(0, 0, 1), 1), dfs(new State(0, 0, 2), 1));
		
        out.println(max);
		out.close();
	}
    
    static int dfs(State state, int steps) {
        List<Vec> trace = new ArrayList<>();
        int x = state.x, y = state.y, dx = 0, dy = 0, step = 0;
        switch(state.dir) {
            case 0: 
                dy = -1;
            break;
            case 1: 
                dx = 1;
            break;
            case 2: 
                dy = 1;
            break;
            case 3: 
                dx = -1;
            break;
        }
        while(true) {
            boolean c = legal(x + dx, y + dy);
            /*if(!legal(x + dx, y + dy) || map[y + dy][x + dx] || visited[y + dy][x + dx]) {
                System.out.println(x + ", " + y + ": " + state.dir);
                System.out.println(!legal(x + dx, y + dy) + " " + (c ? map[y + dy][x + dx] : "no") + " " + (c ? visited[y + dy][x + dx] : "no"));
            }*/
            if(!legal(x + dx, y + dy)) {
                if(step == 0) {
                    for(Vec v : trace) {
                        visited[v.y][v.x] = false;
                    }
                    return steps;
                } else {
                    int res = Math.max(dfs(new State(x, y, (state.dir + 3) % 4), steps + step), dfs(new State(x, y, (state.dir + 1) % 4), steps + step));
                    for(Vec v : trace) {
                        visited[v.y][v.x] = false;
                    }
                    return res; 
                }
            } else if(map[y + dy][x + dx]) {
                if(step == 0) {
                    for(Vec v : trace) {
                        visited[v.y][v.x] = false;
                    }
                    return steps;
                } else {
                    int res = Math.max(dfs(new State(x, y, (state.dir + 3) % 4), steps + step), dfs(new State(x, y, (state.dir + 1) % 4), steps + step));
                    for(Vec v : trace) {
                        visited[v.y][v.x] = false;
                    }
                    return res;
                }
            } else if(visited[y + dy][x + dx]) {
                for(Vec v : trace) {
                    visited[v.y][v.x] = false;
                }
                return steps + step;
            } else {
                x += dx;
                y += dy;
                step++;
                visited[y][x] = true;
                trace.add(new Vec(x, y));
            }
        }
    }
    
    static boolean legal(int x, int y) {
        return x >= 0 && x < N & y >= 0 && y < N;
    }
}

class Vec {
    int x, y;
    
    Vec(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class State {
    // dir: 0 = up, 1 = right, 2 = down, 3 = left
    int x, y, dir;
    
    State(int x, int y, int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
}