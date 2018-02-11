/*
ID: plutonj1
LANG: JAVA
TASK: test
*/

import java.io.*;
import java.util.*;

class test {
	static String name = "test";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input = new StringTokenizer(in.readLine());
		
		Integer a = Integer.parseInt(input.nextToken());
		Integer b = Integer.parseInt(input.nextToken());
		
		out.println(a + b);
		out.close();
	}
}