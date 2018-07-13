/*
ID: plutonj1
LANG: JAVA
TASK: fence8
*/

// simulated annealing attempt, cannot pass test case 5 under the time constraints
/* best results: 
   Test 1: TEST OK [0.149 secs, 25388 KB]
   Test 2: TEST OK [0.261 secs, 28212 KB]
   Test 3: TEST OK [0.149 secs, 24560 KB]
   Test 4: TEST OK [0.541 secs, 41704 KB]
   Test 5: BADCHECK 0.532 (0.532 secs, 41396 KB)
*/

import java.io.*;
import java.util.*;

class fence8 {
    static String name = "fence8";
    static BufferedReader in;
    static PrintWriter out;
    
    static int N, R;
    static int[] board, rail;

    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        N = Integer.parseInt(in.readLine());
        board = new int[N];
        for(int i = 0; i < N; i++) {
            board[i] = Integer.parseInt(in.readLine());
        }
        R = Integer.parseInt(in.readLine());
        rail = new int[R];
        for(int i = 0; i < R; i++) {
            rail[i] = Integer.parseInt(in.readLine());
        }
        
        // use greedy algorithm to get initial solution
        // b = leftover length on current board, j = current board
        int b = board[0], j = 0, e = 0;
        Set<Integer> used = new HashSet<>();
        List<List<Integer>> s = new ArrayList<>(N);
        for(int i = 0; i < N; i++) {
            s.add(new ArrayList<>());
        }
        for(int i = 0; i < R; i++) {
            if(!used.contains(i) && b >= rail[i]) {
                e++;
                used.add(i);
                s.get(j).add(i);
                if((b -= rail[i]) == 0) {
                    j++;
                }
            } else {
                if(j == N - 1) {
                    break;
                } else {
                    // try this rail on the next board
                    j++;
                    i--;
                }
            }
        }
        
        double t = 10000, minT = 0.01, a = 0.99;
        int loop = R / 8 + 3, e_ = 0, uncut = -1, max = e;
        while(t > minT) {
            for(int i = 0; i < loop; i++) {
                e_ = e;
                // neighbor state: randomly pick a board, randomly uncut a rail(if possible), randomly cut the board into potentially more rails
                // random board
                j = (int)(Math.random() * N);
                // random uncut
                if(s.get(j).size() > 0) {
                    used.remove(uncut = s.get(j).remove((int)(Math.random() * s.get(j).size())));
                    e_--;
                }
                b = board[j];
                for(int r : s.get(j)) {
                    b -= rail[r];
                }
                // random cuts
                List<Integer> cuts = new ArrayList<>();
                while(true) {
                    for(int k = (int)(Math.random() * R); k < R; k++) {
                        if(!used.contains(k) && b >= rail[k]) {
                            e_++;
                            cuts.add(k);
                            used.add(k);
                            s.get(j).add(k);
                            if((b -= rail[k]) == 0) {
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                    break;
                }
                // check if accept new solution
                double d = e_ - e;
                if(d > 0) {
                    e = e_;
                } else {
                    double p = Math.exp((double)d / t);
                    if(p > Math.random()) {
                        e = e_;
                    } else {
                        // revert changes
                        s.get(j).removeAll(cuts);
                        s.get(j).add(uncut);
                        used.removeAll(cuts);
                        used.add(uncut);
                    }
                }
                max = Math.max(max, e);
            }
            t *= a;
        }
        /*for(int i = 0; i < N; i++) {
            for(int k : s.get(i)) {
                System.out.print(k + " ");
            }
            System.out.println("");
        }*/
        
        out.println(max);
        out.close();
    }
}