/*
ID: plutonj1
LANG: JAVA
TASK: ratios
*/

import java.io.*;
import java.util.*;

class ratios {
	static String name = "ratios";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int x, y, z, m[][] = new int[3][3], a, b, c, k;
		input = new StringTokenizer(in.readLine());
		x = Integer.parseInt(input.nextToken());
		y = Integer.parseInt(input.nextToken());
		z = Integer.parseInt(input.nextToken());
		for(int i = 0; i < 3; i++) {
			input = new StringTokenizer(in.readLine());
			for(int j = 0; j < 3; j++) {
				m[j][i] = Integer.parseInt(input.nextToken());
			}
		}
		
		int det = m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) - m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0]) + m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]);
		if(det == 0) {
			out.println("NONE");
			out.close();
			return;
		}
		// the inverse matrix of m = mTran / det
		// the dividing of the determinant is skipped to maintain the integral nature
		int[][] mTran = new int[3][3];
		mTran[0][0] = m[1][1] * m[2][2] - m[1][2] * m[2][1];
		mTran[1][0] = m[1][2] * m[2][0] - m[1][0] * m[2][2];
		mTran[2][0] = m[1][0] * m[2][1] - m[1][1] * m[2][0];
		mTran[0][1] = m[0][2] * m[2][1] - m[0][1] * m[2][2];
		mTran[1][1] = m[0][0] * m[2][2] - m[0][2] * m[2][0];
		mTran[2][1] = m[0][1] * m[2][0] - m[0][0] * m[2][1];
		mTran[0][2] = m[0][1] * m[1][2] - m[0][2] * m[1][1];
		mTran[1][2] = m[0][2] * m[1][0] - m[0][0] * m[1][2];
		mTran[2][2] = m[0][0] * m[1][1] - m[0][1] * m[1][0];
		a = Math.abs(mTran[0][0] * x + mTran[0][1] * y + mTran[0][2] * z);
		b = Math.abs(mTran[1][0] * x + mTran[1][1] * y + mTran[1][2] * z);
		c = Math.abs(mTran[2][0] * x + mTran[2][1] * y + mTran[2][2] * z);
		k = gcf(gcf(a, b), c);
		
		if((a / k * m[0][0] + b / k * m[0][1] + c / k * m[0][2]) / x != (a / k * m[1][0] + b / k * m[1][1] + c / k * m[1][2]) / y) {
			out.println("NONE");
			out.close();
			return;
		}
		if((a / k * m[0][0] + b / k * m[0][1] + c / k * m[0][2]) < x) {
			out.println(x / a * k + " " + y / b * k + " " + z / c * k + " " + 1);
		} else {
			out.println(a / k + " " + b / k + " " + c / k + " " + (a / k * m[0][0] + b / k * m[0][1] + c / k * m[0][2]) / x);
		}
		out.close();
	}
	
	static int gcf(int a, int b) {
		if(a > b) {
			a = a ^ b ^ (b = a);
		}
		if(a == 0) {
			return b;
		}
		if(b % a == 0) {
			return a;
		} else {
			return gcf(b % a, a);
		}
	}
}