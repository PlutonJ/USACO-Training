/*
ID: plutonj1
LANG: JAVA
TASK: friday
*/

import java.io.*;
import java.util.*;

class friday {
	static String name = "friday";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		
		int N = Integer.parseInt(in.readLine()), day = 1;
		int[] days = new int[7];
		for(int year = 1900; year < 1900 + N; year++) {
			for(int month = 0; month < 12; month++) {
				days[(day + 13) % 7]++;
				day += (month == 3 || month == 5 || month == 8 || month == 10) ? 30 : 
					(month == 1) ? (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28 : 31;
			}
		}
		
		for(int i = 0; i < 7; i++) {
			out.print(days[i]);
			if(i == 6) {
				out.print("\n");
			} else {
				out.print(" ");
			}
		}
		out.close();
	}
}