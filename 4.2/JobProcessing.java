/*                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
ID: plutonj1
LANG: JAVA
TASK: job
*/

import java.io.*;
import java.util.*;

class job {
	static String name = "job";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        input = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(input.nextToken()), M1 = Integer.parseInt(input.nextToken()), M2 = Integer.parseInt(input.nextToken());
        int[] A = new int[M1], B = new int[M2];
        input = new StringTokenizer(in.readLine());
        for(int i = 0; i < M1; i++) {
            A[i] = Integer.parseInt(input.nextToken());
        }
        input = new StringTokenizer(in.readLine());
        for(int i = 0; i < M2; i++) {
            B[i] = Integer.parseInt(input.nextToken());
        }
        int[] a = new int[M1], b = new int[M2];
        for(int i = 0; i < M1; i++) {
            a[i] = A[i];
        }
        for(int i = 0; i < M2; i++) {
            b[i] = B[i];
        }
        int[] timeA = new int[M1], timeB = new int[M2];
        int[] time1 = new int[N], time2 = new int[N];
        int k = -1;
        int min = 0;
        for(int i = 0; i < N; i++) {
            min = 20000;
            for(int j = 0; j < M1; j++) {
                if(timeA[j] + A[j] < min) {
                    min = timeA[j] + A[j];
                    k = j;
                }
            }
            timeA[k] = time1[i] = min;
        }
        /*for(int i : time1) {
            System.out.println(i);
        }*/
        out.print(min + " ");
        for(int i = 0; i < N; i++) {
            min = 20000;
            for(int j = 0; j < M2; j++) {
                if(timeB[j] + B[j] < min) {
                    min = timeB[j] + B[j];
                    k = j;
                }
            }
            timeB[k] = time2[i] = min;
        }
        int max = 0;
        for(int i = 0; i < N; i++) {
            if(time1[i] + time2[N - i - 1] > max) {
                max = time1[i] + time2[N - i - 1];
            }
        }
		
        out.println(max);
		out.close();
	}
}