/*
ID: plutonj1
LANG: JAVA
TASK: palsquare
*/

import java.io.*;
import java.util.*;

class palsquare {
	static String name = "palsquare";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		
		int B = Integer.parseInt(in.readLine());
		char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
		for(int i = 1; i <= 300; i++) {
			StringBuilder buf = new StringBuilder();
			int n = i * i;
			while(n > 0) {
				buf.append(digits[n % B]);
				n /= B;
			}
			if(buf.toString().equals(buf.reverse().toString())) {
				StringBuilder buf2 = new StringBuilder();
				int j = i;
				while(j > 0) {
					buf2.append(digits[j % B]);
					j /= B;
				}
				out.println(buf2.reverse().toString() + " " + buf.toString());
			}
		}
		
		out.close();
	}
}