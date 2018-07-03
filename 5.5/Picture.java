/*
ID: plutonj1
LANG: JAVA
TASK: picture
*/

import java.io.*;
import java.util.*;

class picture {
	static String name = "picture";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        // edge = [start ? 0 : 1, x0 or y0, x1 or y1, y or x, id]
        // sort by y or x then start -> end then id
        Set<int[]> hor = new TreeSet<>((a, b) -> a[3] == b[3] ? a[0] == b[0] ? a[4] - b[4] : a[0] - b[0] : a[3] - b[3]);
        Set<int[]> ver = new TreeSet<>((a, b) -> a[3] == b[3] ? a[0] == b[0] ? a[4] - b[4] : a[0] - b[0] : a[3] - b[3]);
        int N = Integer.parseInt(in.readLine());
        for(int i = 0; i < N; i++) {
            input = new StringTokenizer(in.readLine());
            int x0 = Integer.parseInt(input.nextToken()), y0 = Integer.parseInt(input.nextToken()), x1 = Integer.parseInt(input.nextToken()), y1 = Integer.parseInt(input.nextToken());
            hor.add(new int[] {0, x0, x1, y0, i});
            hor.add(new int[] {1, x0, x1, y1, i});
            ver.add(new int[] {0, y0, y1, x0, i});
            ver.add(new int[] {1, y0, y1, x1, i});
        }
        //System.out.println(hor.size());
        //System.out.println(ver.size());
        int p = 0;
        int[] scan = new int[20001];
        for(int[] e : hor) {
            switch(e[0]) {
                case 0: 
                    for(int i = e[1]; i < e[2]; i++) {
                        scan[i + 10000]++;
                        if(scan[i + 10000] == 1) {
                            p++;
                        }
                    }
                break;
                case 1:
                    for(int i = e[1]; i < e[2]; i++) {
                        scan[i + 10000]--;
                        if(scan[i + 10000] == 0) {
                            p++;
                        }
                    }
                break;
            }
        }
        Arrays.fill(scan, 0);
        for(int[] e : ver) {
            switch(e[0]) {
                case 0: 
                    for(int i = e[1]; i < e[2]; i++) {
                        scan[i + 10000]++;
                        if(scan[i + 10000] == 1) {
                            p++;
                        }
                    }
                break;
                case 1:
                    for(int i = e[1]; i < e[2]; i++) {
                        scan[i + 10000]--;
                        if(scan[i + 10000] == 0) {
                            p++;
                        }
                    }
                break;
            }
        }
		
        out.println(p);
		out.close();
	}
}