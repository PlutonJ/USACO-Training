/*
ID: plutonj1
LANG: JAVA
TASK: gift1
*/

import java.io.*;
import java.util.*;

class gift1 {
	static String name = "gift1";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		
		int NP = Integer.parseInt(in.readLine());
		String[] names = new String[NP];
		Map<String, Integer> money = new HashMap<>();
		for(int i = 0; i < NP; i++) {
			names[i] = in.readLine();
			money.put(names[i], 0);
		}
		for(int i = 0; i < NP; i++) {
			String name = in.readLine();
			StringTokenizer input = new StringTokenizer(in.readLine());
			int gift = Integer.parseInt(input.nextToken()), NG = Integer.parseInt(input.nextToken());
			int indGift = NG > 0 ? gift / NG : 0, remainder = NG > 0 ? gift % NG : 0;
			money.put(name, money.get(name) - gift + remainder);
			for(int j = 0; j < NG; j++) {
				String receiver = in.readLine();
				money.put(receiver, money.get(receiver) + indGift);
			}
		}
		
		for(int i = 0; i < NP; i++) {
			out.println(names[i] + " " + money.get(names[i]));
		}
		out.close();
	}
}