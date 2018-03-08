/*
ID: plutonj1
LANG: JAVA
TASK: comehome
*/

import java.io.*;
import java.util.*;

class comehome {
	static String name = "comehome";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int P = Integer.parseInt(in.readLine());
		
		
		out.close();
	}
}

class Cow {
	char pasture;
	
	Cow(char Pasture) {
		
	}
}