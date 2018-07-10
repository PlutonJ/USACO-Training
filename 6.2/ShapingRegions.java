/*
ID: plutonj1
LANG: JAVA
TASK: rect1
*/

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class rect1 {
    static String name = "rect1";
    static BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int A = Integer.parseInt(input.nextToken()), B = Integer.parseInt(input.nextToken()), N = Integer.parseInt(input.nextToken()), p[][] = new int[N][5], area[] = new int[2500];
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            int llx = Integer.parseInt(input.nextToken()), lly = Integer.parseInt(input.nextToken()), urx = Integer.parseInt(input.nextToken()), ury = Integer.parseInt(input.nextToken()), color = Integer.parseInt(input.nextToken()) - 1;
            p[i][0] = llx;
            p[i][1] = lly;
            p[i][2] = urx;
            p[i][3] = ury;
            p[i][4] = color;
        }
        // total non-white area
        int sum = 0;
        for(int i = 0; i < N; i++) {
            if(p[i][4] != 0) {
                Set<int[]> vis = new HashSet<>(), removeSet = new HashSet<>(), addSet = new HashSet<>();
                vis.add(new int[] {p[i][0], p[i][1], p[i][2], p[i][3]});
                // for all patches j above patch i
                for(int j = i + 1; j < N; j++) {
                    removeSet.clear();
                    addSet.clear();
                    for(int[] aabb : vis) {
                        if(aabb[2] <= p[j][0] || aabb[0] >= p[j][2] || aabb[3] <= p[j][1] || aabb[1] >= p[j][3]) {
                            continue;
                        } else {
                            removeSet.add(aabb);
                            if(aabb[0] < p[j][0]) {
                                addSet.add(new int[] {aabb[0], aabb[1], p[j][0], aabb[3]});
                            }
                            if(aabb[1] < p[j][1]) {
                                addSet.add(new int[] {max(aabb[0], p[j][0]), aabb[1], min(aabb[2], p[j][2]), p[j][1]});
                            }
                            if(aabb[2] > p[j][2]) {
                                addSet.add(new int[] {p[j][2], aabb[1], aabb[2], aabb[3]});
                            }
                            if(aabb[3] > p[j][3]) {
                                addSet.add(new int[] {max(aabb[0], p[j][0]), p[j][3], min(aabb[2], p[j][2]), aabb[3]});
                            }
                        }
                    }
                    vis.removeAll(removeSet);
                    vis.addAll(addSet);
                }
                for(int[] aabb : vis) {
                    int a = (aabb[2] - aabb[0]) * (aabb[3] - aabb[1]);
                    //System.out.println((p[i][4] + 1) + ": " + aabb[0] + " " + aabb[1] + " " + aabb[2] + " " + aabb[3]);
                    area[p[i][4]] += a;
                    sum += a;
                }
            }
        }
        area[0] = A * B - sum;
        
        for(int i = 0; i < 2500; i++) {
            if(area[i] > 0) {
                out.println((i + 1) + " " + area[i]);
            }
        }
        out.close();
    }
}