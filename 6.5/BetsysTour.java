/*
ID: plutonj1
LANG: JAVA
TASK: betsy
*/

// https://www.byvoid.com/zhs/blog/usaco-544-betsys-tour
// probably better than the USACO solution as it claims to pass N = 7 in about half a second, while this solution passes it in ~0.3s

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class betsy {
    static String name = "betsy";
    static BufferedReader in;
    static PrintWriter out;
    
    static int N, x = 1, y = 1, dist = 0, cnt = 0, n;
    static int[][] nei;
    static boolean[][] visited;
    static int[] dx = new int[] {0, 1, 0, -1};
    static int[] dy = new int[] {-1, 0, 1, 0};
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        N = Integer.parseInt(in.readLine());
        visited = new boolean[N + 2][N + 2];
        for(int i = 1; i <= N; i++) {
            visited[0][i] = true;
            visited[i][0] = true;
            visited[N + 1][i] = true;
            visited[i][N + 1] = true;
        }
        nei = new int[N + 2][N + 2];
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                for(int k = 0; k < 4; k++) {
                    if(!visited[i + dx[k]][j + dy[k]]) {
                        nei[i + dx[k]][j + dy[k]]++;
                    }
                }
            }
        }
        n = N * N;
        dfs();
        
        out.println(cnt);
        out.close();
    }
    
    static void dfs() {
        // creates an unreachable isolated area, prune
        if(visited[x - 1][y] && visited[x + 1][y] && !visited[x][y - 1] && !visited[x][y + 1] || !visited[x - 1][y] && !visited[x + 1][y] && visited[x][y - 1] && visited[x][y + 1]) {
            return;
        }
        dist++;
        if(x == 1 && y == N) {
            if(dist == n) {
                cnt++;
            }
            dist--;
            return;
        }
        visited[x][y] = true;
        int forceCnt = 0;
        for(int i = 0; i < 4; i++) {
            x += dx[i];
            y += dy[i];
            // if a neighbor(that is not the destination) only has 1 other neighbor left, the next step is forced to be that neighbor
            if(!visited[x][y] && (x != 1 || y != N)) {
                if(--nei[x][y] == 1) {
                    forceCnt++;
                }
            }
            x -= dx[i];
            y -= dy[i];
        }
        // if there are more than 1 forced neighbors, prune
        if(forceCnt > 1) {
            for(int i = 0; i < 4; i++) {
                x += dx[i];
                y += dy[i];
                if(!visited[x][y]) {
                    nei[x][y]++;
                }
                x -= dx[i];
                y -= dy[i];
            }
            visited[x][y] = false;
            dist--;
            return;
        }
        for(int i = 0; i < 4; i++) {
            x += dx[i];
            y += dy[i];
            if(forceCnt == 1) {
                // go to forced neighbor
                if(!visited[x][y] && nei[x][y] == 1) {
                    dfs();
                }
            } else {
                // check every neighbor
                if(!visited[x][y]) {
                    dfs();
                }
            }
            x -= dx[i];
            y -= dy[i];
        }
        for(int i = 0; i < 4; i++) {
            x += dx[i];
            y += dy[i];
            if(!visited[x][y]) {
                nei[x][y]++;
            }
            x -= dx[i];
            y -= dy[i];
        }
        visited[x][y] = false;
        dist--;
    }
}