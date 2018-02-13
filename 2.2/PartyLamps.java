/*
ID: plutonj1
LANG: JAVA
TASK: lamps
*/

// observe that pressing any button twice is the same as not pressing it
// thus the only permutations of the outcome of any number of button pressing
// are the effect of pressing: 
// none, 1, 2, 3, 4, 14, 24, 34
// note: 123 yields the same outcome as none, 23 -> 1, 234 -> 14, 1234 -> 4, 12 -> 3, 13 -> 2, 124 -> 34, 134 -> 24
// also the process of pressing buttons is commutative
//
// thought process:
// find all outcomes of achievable permutations listed above
// and print the ones that consistent with given information

import java.io.*;
import java.util.*;

class lamps {
	static String name = "lamps";
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		int N = Integer.parseInt(in.readLine()), C = Integer.parseInt(in.readLine());
		boolean[] ons = new boolean[N], offs = new boolean[N];
		int index;
		input = new StringTokenizer(in.readLine());
		while((index = Integer.parseInt(input.nextToken())) != -1) ons[index - 1] = true;
		input = new StringTokenizer(in.readLine());
		while((index = Integer.parseInt(input.nextToken())) != -1) offs[index - 1] = true;
		
		List<boolean[]> states = new LinkedList<>();
		boolean[] allOn = new boolean[N];
		for(int i = 0; i < N; i++) allOn[i] = true;
		if(C == 0) {
			states.add(allOn);			// none
		} else if(C == 1) {
			states.add(do1(allOn));		// 1
			states.add(do2(allOn));		// 2
			states.add(do3(allOn));		// 3
			states.add(do4(allOn));		// 4
		} else if(C == 2) {
			states.add(allOn);			// 11
			states.add(do1(allOn));		// 23
			states.add(do2(allOn));		// 13
			states.add(do3(allOn));		// 12
			states.add(do14(allOn));	// 14
			states.add(do24(allOn));	// 24
			states.add(do34(allOn));	// 34
		} else {
			states.add(allOn);			// odd: 11 * n + 123,	even: 11 * n		(n = any nonnegative integer)
			states.add(do1(allOn));		// odd: 11 * n + 1,		even: 11 * n + 23
			states.add(do2(allOn));		// odd: 11 * n + 2,		even: 11 * n + 13
			states.add(do3(allOn));		// odd: 11 * n + 3,		even: 11 * n + 12
			states.add(do4(allOn));		// odd: 11 * n + 4,		even: 11 * n + 1234
			states.add(do14(allOn));	// odd: 11 * n + 234,	even: 11 * n + 14
			states.add(do24(allOn));	// odd: 11 * n + 134,	even: 11 * n + 24
			states.add(do34(allOn));	// odd: 11 * n + 124,	even: 11 * n + 34
		}
		
		for(int i = 0; i < states.size(); i++){
			if(!check(ons, offs, states.get(i))){
				//System.out.println(toString(states.get(i)));
				states.remove(i--);
			}
		}
		
		Set<String> output = new TreeSet<String>();
		for(boolean[] state : states) output.add(toString(state));
		for(String s : output) out.println(s);
		if(output.size() == 0) out.println("IMPOSSIBLE");
		out.close();
	}
	
	static String toString(boolean[] states) {
		StringBuilder s = new StringBuilder();
		for(boolean b : states) {
			s.append(b ? '1' : '0');
		}
		return s.toString();
	}
	
	static boolean check(boolean[] ons, boolean[] offs, boolean[] states) {	// returns true if valid
		for(int i = 0; i < ons.length; i++) if(ons[i] && !states[i]) return false;
		for(int i = 0; i < offs.length; i++) if(offs[i] && states[i]) return false;
		return true;
	}
	
	static boolean[] do1(boolean[] in) {
		boolean[] outcome = new boolean[in.length];
		for(int i = 0; i < in.length; i++) {
			outcome[i] = !in[i];
		}
		return outcome;
	}
	
	static boolean[] do2(boolean[] in) {
		boolean[] outcome = new boolean[in.length];
		for(int i = 0; i < in.length; i++) {
			outcome[i] = in[i];
		}
		for(int i = 0; i < in.length; i += 2) {
			outcome[i] = !in[i];
		}
		return outcome;
	}
	
	static boolean[] do3(boolean[] in) {
		boolean[] outcome = new boolean[in.length];
		for(int i = 0; i < in.length; i++) {
			outcome[i] = in[i];
		}
		for(int i = 1; i < in.length; i += 2) {
			outcome[i] = !in[i];
		}
		return outcome;
	}
	
	static boolean[] do4(boolean[] in) {
		boolean[] outcome = new boolean[in.length];
		for(int i = 0; i < in.length; i++) {
			outcome[i] = in[i];
		}
		for(int i = 0; i < in.length; i += 3) {
			outcome[i] = !in[i];
		}
		return outcome;
	}
	
	static boolean[] do14(boolean[] in) {
		return do1(do4(in));
	}
	
	static boolean[] do24(boolean[] in) {
		return do2(do4(in));
	}
	
	static boolean[] do34(boolean[] in) {
		return do3(do4(in));
	}
}