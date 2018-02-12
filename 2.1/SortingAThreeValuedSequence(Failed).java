/*
ID: plutonj1
LANG: JAVA
TASK: sort3
*/

// thought process:
// for the next number x in {1, 2, 3}, 
// index from both front(or (the last index of last x) + 1) and back, 
// move front index until
// records[front index] != x
// the back index then proceeds until it finds x
// (if it does not find it, then the proceed to next x 
// and go to the beginning of the loop)
// and swap(front index, back index) 

import java.io.*;
import java.util.*;

class sort3 {
	static String name = "sort3";
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine());
		int[] records = new int[N];
		for(int i = 0; i < N; i++) {
			records[i] = Integer.parseInt(in.readLine());
		}
		int x = 1, front, back, last = 0, count = 0;
		while(x <= 3) {
			front = last;
			back = N - 1;
			while(front < N && records[front] == x) front++;
			if(front == N) break;			// if front goes to end of records before x reaches 3, break out of loop
			while(back >= 0 && records[back] != x) back--;
			if(front > back) {
				last = front;
				x++;
				continue;
			}
			count++;
			int swap = records[front];
			records[front] = records[back];
			records[back] = swap;
		}
		
		out.println(count);
		out.close();
	}
}