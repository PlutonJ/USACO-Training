/*
ID: plutonj1
LANG: JAVA
TASK: fence4
*/

// overly mathematical solution compared to the USACO analysis

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

class fence4 {
    static String name = "fence4";
    static BufferedReader in;
    static PrintWriter out;
    
    static int x = 0, y = 1, min = 0, max = 1;
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        int N = Integer.parseInt(in.readLine());
        input = new StringTokenizer(in.readLine());
        int px = Integer.parseInt(input.nextToken()), py = Integer.parseInt(input.nextToken());
        // point of each vertex, direction vector of each fence, vector from the given point to each vertex, normal vector in the direction from the given point to each fence
        int[][] f = new int[N][2], vf = new int[N][2], pf = new int[N][2], vn = new int[N][2];
        double[] mag = new double[N];
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            f[i][x] = Integer.parseInt(input.nextToken());
            f[i][y] = Integer.parseInt(input.nextToken());
        }
        // check crossings
        for(int i = 0; i < N; i++) {
            int[] f0 = f[i];
            int[] f1 = f[(i + 1) % N];
            vf[i][x] = f1[x] - f0[x];
            vf[i][y] = f1[y] - f0[y];
            pf[i][x] = f0[x] - px;
            pf[i][y] = f0[y] - py;
            mag[i] = mag(pf[i]);
            vn[i][x] = -vf[i][y];
            vn[i][y] = vf[i][x];
            // make sure that the direction of the normal vector goes from the given point to the fence
            if(obtuse(vn[i], pf[i])) {
                vn[i][x] = -vn[i][x];
                vn[i][y] = -vn[i][y];
            }
            // not modding j + 1 by N because j is at most N - 1
            for(int j = 0; j < i; j++) {
                int[] f0f2 = new int[] {f[j][x] - f0[x], f[j][y] - f0[y]};
                int[] f0f3 = new int[] {f[j + 1][x] - f0[x], f[j + 1][y] - f0[y]};
                int[] f2f0 = new int[] {f0[x] - f[j][x], f0[y] - f[j][y]};
                int[] f2f1 = new int[] {f1[x] - f[j][x], f1[y] - f[j][y]};
                if(zCross(vf[i], f0f2) * zCross(vf[i], f0f3) < 0 && zCross(vf[j], f2f0) * zCross(vf[j], f2f1) < 0) {
                    out.println("NOFENCE");
                    out.close();
                    return;
                }
            }
        }
        // the comparator is as such because of the anti-human output format
        Set<int[]> v = new TreeSet<>((a, b) -> a[1] == b[1] ? a[0] - b[0] : a[1] - b[1]);
        fence: for(int i = 0; i < N; i++) {
            //System.out.println(i);
            int f0 = i, f1 = (i + 1) % N;
            // centroid of triangle created by p, f0, and f1
            double[] o = new double[] {(double)(px + f[f0][x] + f[f1][x]) / 3, (double)(py + f[f0][y] + f[f1][y]) / 3};
            double[] po = new double[] {o[x] - px, o[y] - py};
            double[] f0o = new double[] {o[x] - f[f0][x], o[y] - f[f0][y]};
            double zPf0CrossPo = pf[f0][x] * po[y] - pf[f0][y] * po[x];
            double zPf1CrossPo = pf[f1][x] * po[y] - pf[f1][y] * po[x];
            double zF0f1CrossF0o = vf[f0][x] * f0o[y] - vf[f0][y] * f0o[x];
            List<double[]> vis = new ArrayList<>();
            // the fence includes all points in f[f0] + r * vf[f0], where r is in [0, 1]
            vis.add(new double[] {0, 1});
            // fences parallel to the line of sight are ignored(not considered visible)
            if(!parallel(pf[f0], pf[f1])) {
                for(int j = 0; j < N; j++) {
                    if(i == j) {
                        continue;
                    }
                    int jp1 = (j + 1) % N;
                    // p, f0, and f1 create a triangle that represents the field of view of the observer toward fence i
                    int jDiffPf0 = zCross(pf[f0], pf[j]) * zCross(pf[f0], pf[jp1]);
                    int pf0DiffJ = (vf[j][x] * (py - f[j][y]) - vf[j][y] * (px - f[j][x])) * (vf[j][x] * (f[f0][y] - f[j][y]) - vf[j][y] * (f[f0][x] - f[j][x]));
                    int jDiffPf1 = zCross(pf[f1], pf[j]) * zCross(pf[f1], pf[jp1]);
                    int pf1DiffJ = (vf[j][x] * (py - f[j][y]) - vf[j][y] * (px - f[j][x])) * (vf[j][x] * (f[f1][y] - f[j][y]) - vf[j][y] * (f[f1][x] - f[j][x]));
                    boolean crossPf0 = jDiffPf0 < 0 && pf0DiffJ < 0;
                    boolean crossPf1 = jDiffPf1 < 0 && pf1DiffJ < 0;
                    // to test if a point(e.g. f[j]) is on a segment(e.g. pf0), see if the t's of
                    //     f[j][x] = px + t * pf[f0][x] and f[j][y] = py + t * pf[f0][y]
                    //     are equal and in the range [0, 1]
                    boolean onPf0 = (f[j][x] - px) * pf[f0][y] == (f[j][y] - py) * pf[f0][x] && (f[j][x] - px) * pf[f0][x] >= 0 && (f[j][y] - py) * pf[f0][y] >= 0 && Math.abs(f[j][x] - px) <= Math.abs(pf[f0][x]) && Math.abs(f[j][y] - py) <= Math.abs(pf[f0][y]) || (f[jp1][x] - px) * pf[f0][y] == (f[jp1][y] - py) * pf[f0][x] && (f[jp1][x] - px) * pf[f0][x] >= 0 && (f[jp1][y] - py) * pf[f0][y] >= 0 && Math.abs(f[jp1][x] - px) <= Math.abs(pf[f0][x]) && Math.abs(f[jp1][y] - py) <= Math.abs(pf[f0][y]);
                    boolean onPf1 = (f[j][x] - px) * pf[f1][y] == (f[j][y] - py) * pf[f1][x] && (f[j][x] - px) * pf[f1][x] >= 0 && (f[j][y] - py) * pf[f1][y] >= 0 && Math.abs(f[j][x] - px) <= Math.abs(pf[f1][x]) && Math.abs(f[j][y] - py) <= Math.abs(pf[f1][y]) || (f[jp1][x] - px) * pf[f1][y] == (f[jp1][y] - py) * pf[f1][x] && (f[jp1][x] - px) * pf[f1][x] >= 0 && (f[jp1][y] - py) * pf[f1][y] >= 0 && Math.abs(f[jp1][x] - px) <= Math.abs(pf[f1][x]) && Math.abs(f[jp1][y] - py) <= Math.abs(pf[f1][y]);
                    //System.out.println("\t" + j + ": crossPf0: " + crossPf0 + " onPf0: " + onPf0 + " crossPf1: " + crossPf1 + " onPf1: " + onPf1);
                    // note that crossPf0 && onPf0 == false, crossPf1 && onPf1 == false at all times
                    // 8 possibilities: 
                    //     1. ends on both sides: completely blocked
                    //     2. end on one side + intersect other side: completely blocked
                    //     3. intersect both sides: completely blocked
                    //     4. end on one side + no intersection, other end strictly inside of the triangle: partially blocked
                    //     5. end on one side + no intersection, other end strictly outside of the triangle: not blocked
                    //     6. intersect one side(one end strictly inside and the other strictly outside of the triangle): partially blocked
                    //     7. both ends strictly inside the triangle: partially blocked
                    //     8. both ends strictly outside the triangle: not blocked
                    if(onPf0 && onPf1) {
                        // possibility 1
                        continue fence;
                    } else if(onPf0 || onPf1) {
                        if(crossPf0 || crossPf1) {
                            // possibility 2
                            continue fence;
                        } else {
                            if(zPf0CrossPo * zCross(pf[f0], pf[j]) < 0 || zPf1CrossPo * zCross(pf[f1], pf[j]) < 0 || zF0f1CrossF0o * (vf[f0][x] * (f[j][y] - f[f0][y]) - vf[f0][y] * (f[j][x] - f[f0][x])) < 0 || zPf0CrossPo * zCross(pf[f0], pf[jp1]) < 0 || zPf1CrossPo * zCross(pf[f1], pf[jp1]) < 0 || zF0f1CrossF0o * (vf[f0][x] * (f[jp1][y] - f[f0][y]) - vf[f0][y] * (f[jp1][x] - f[f0][x])) < 0) {
                                // if one end is strictly outside of the triangle => possibility 5
                                continue;
                            }
                            // possibility 4
                        }
                    } else {
                        if(crossPf0 && crossPf1) {
                            // possibility 3
                            continue fence;
                        } else {
                            if((zPf0CrossPo * zCross(pf[f0], pf[j]) < 0 || zPf1CrossPo * zCross(pf[f1], pf[j]) < 0 || zF0f1CrossF0o * (vf[f0][x] * (f[j][y] - f[f0][y]) - vf[f0][y] * (f[j][x] - f[f0][x])) < 0) && (zPf0CrossPo * zCross(pf[f0], pf[jp1]) < 0 || zPf1CrossPo * zCross(pf[f1], pf[jp1]) < 0 || zF0f1CrossF0o * (vf[f0][x] * (f[jp1][y] - f[f0][y]) - vf[f0][y] * (f[jp1][x] - f[f0][x])) < 0)) {
                                // if both ends are strictly outside of the triangle => possibility 8
                                continue;
                            }
                            // possibilities 6 or 7
                        }
                    }
                    // examining every other fence j that at least one vertex between the observing point and fence i
                    if(j != f0 && (!obtuse(vn[f0], pf[j]) && !parallel(vf[f0], pf[j]) || !obtuse(vn[f0], pf[jp1]) && !parallel(vf[f0], pf[jp1]))) {
                        // a and b are the intersections between the line of sight through each end of the fence j and the virtual line through each end of the fence i
                        // thus the section of the line between a and b are blocked from the view
                        double a;
                        // if the first vertex of fence j is not between the observing point and fence i, then a would be +/- infinity depending on pf[j]'s direction relative to fence i
                        if(obtuse(vn[f0], pf[j])) {
                            // project pf[j] onto vf[f0] and check the direction
                            double projC = (double)dot(pf[j], vf[f0]) / dot(vf[f0], vf[f0]);
                            double[] proj = new double[] {projC * vf[f0][x], projC * vf[f0][y]};
                            // essentially checking for obtuse(vf[f0], proj)
                            if(vf[f0][x] * proj[x] + vf[f0][y] * proj[y] < 0) {
                                a = Double.NEGATIVE_INFINITY;
                            } else {
                                a = Double.POSITIVE_INFINITY;
                            }
                        } else if(parallel(vf[f0], pf[j])) {
                            if(obtuse(vf[f0], pf[j])) {
                                // parallel and obtuse => vf[f0] and pf[j] make an angle of 180 deg
                                a = Double.NEGATIVE_INFINITY;
                            } else {
                                // vf[f0] and pf[j] have the same direction
                                a = Double.POSITIVE_INFINITY;
                            }
                        } else {
                            a = (double)(pf[f0][y] * pf[j][x] - pf[f0][x] * pf[j][y]) / (vf[f0][x] * pf[j][y] - vf[f0][y] * pf[j][x]);
                        }
                        double b;
                        // if the second vertex of fence j is not between the observing point and fence i, then b would be +/- infinity depending on pf[jp1]'s direction relative to fence i
                        if(obtuse(vn[f0], pf[jp1])) {
                            // project pf[j + 1] onto vf[f0] and check the direction
                            double projC = (double)dot(pf[jp1], vf[f0]) / dot(vf[f0], vf[f0]);
                            double[] proj = new double[] {projC * vf[f0][x], projC * vf[f0][y]};
                            // essentially checking for obtuse(vf[f0], proj)
                            if(vf[f0][x] * proj[x] + vf[f0][y] * proj[y] < 0) {
                                b = Double.NEGATIVE_INFINITY;
                            } else {
                                b = Double.POSITIVE_INFINITY;
                            }
                        } else if(parallel(vf[f0], pf[jp1])) {
                            if(obtuse(vf[f0], pf[jp1])) {
                                // parallel and obtuse => vf[f0] and pf[jp1] make an angle of 180 deg
                                b = Double.NEGATIVE_INFINITY;
                            } else {
                                // vf[f0] and pf[jp1] have the same direction
                                b = Double.POSITIVE_INFINITY;
                            }
                        } else {
                            b = (double)(pf[f0][y] * pf[jp1][x] - pf[f0][x] * pf[jp1][y]) / (vf[f0][x] * pf[jp1][y] - vf[f0][y] * pf[jp1][x]);
                        }
                        if(b < a) {
                            double swap = a;
                            a = b;
                            b = swap;
                        }
                        //System.out.println("\t" + j + ": " + a + " " + b);
                        for(int k = vis.size() - 1; k >= 0; k--) {
                            // 5 possibilities: the shadow blocks: nothing, the 0 side, the 1 side, part of the middle, the entire segment
                            double[] seg = vis.get(k);
                            if(b <= seg[min] || a >= seg[max]) {
                                // blocks nothing
                                continue;
                            } else if(a <= seg[min]) {
                                if(b >= seg[max]) {
                                    // blocks the entire segment
                                    vis.remove(k);
                                } else {
                                    // blocks the 0 side
                                    seg[min] = b;
                                }
                            } else {
                                if(b >= seg[max]) {
                                    // blocks the 1 side
                                    seg[max] = a;
                                } else {
                                    // blocks part of the middle
                                    vis.remove(k);
                                    vis.add(new double[] {seg[min], a});
                                    vis.add(new double[] {b, seg[max]});
                                }
                            }
                        }
                        // remove empty segments
                        for(int k = vis.size() - 1; k >= 0; k--) {
                            double[] seg = vis.get(k);
                            if(seg[min] >= seg[max]) {
                                vis.remove(k);
                            }
                        }
                    }
                }
                // if there are still visible parts of the fence
                if(vis.size() > 0) {
                    // anti-human outpu format
                    v.add(new int[] {Math.min(f0, f1), Math.max(f0, f1)});
                }
            }
        }
        
        out.println(v.size());
        for(int[] vis : v) {
            out.println(f[vis[0]][x] + " " + f[vis[0]][y] + " " + f[vis[1]][x] + " " + f[vis[1]][y]);
        }
        out.close();
    }
    
    static double mag(int[] v) {
        return Math.sqrt(v[x] * v[x] + v[y] * v[y]);
    }
    
    static int dot(int[] v0, int[] v1) {
        return v0[x] * v1[x] + v0[y] * v1[y];
    }
    
    static int zCross(int[] v0, int[]v1) {
        return v0[x] * v1[y] - v1[x] * v0[y];
    }
    
    static boolean obtuse(int[] v0, int[] v1) {
        return dot(v0, v1) < 0;
    }
    
    static boolean parallel(int[] v0, int[] v1) {
        return zCross(v0, v1) == 0;
    }
}