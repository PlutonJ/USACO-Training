/*
ID: plutonj1
LANG: JAVA
TASK: milk3
*/

import java.io.*;
import java.util.*;

class milk3 {
	static String name = "milk3";
	
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int[] capacities = {Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken())};
		Set<State> states = new TreeSet<>();
		Queue<State> q = new LinkedList<>();
		q.offer(new State(0, 0, capacities[2]));		// buckets A and B empty, C full
		while(q.size() > 0) {							// search through all achievable states until every possible move returns to an earlier state
			State s = q.poll();
			if(states.contains(s)) continue;			// if searched skip
			states.add(s);
			for(int i = 0; i < 3; i++) {
				if(s.buckets[i] > 0) {					// if bucket is not empty
					for(int j = 0; j < 3; j++) {
						if(j != i && s.buckets[j] < capacities[j]) {		// if a different bucket is not full
							int[] buckets = Arrays.copyOfRange(s.buckets, 0, 3);
							int poured = Math.min(buckets[i], capacities[j] - buckets[j]);		// pour from i until j is full or i is emptied
							buckets[i] -= poured;
							buckets[j] += poured;
							q.offer(new State(buckets));
						}
					}
				}
			}
		}
		
		String print = "";
		for(State s : states) if(s.buckets[0] == 0) print = print + s.toString();
		out.println(print.substring(0, print.length() - 1));
		out.close();
	}
}

class State implements Comparable<State> {
	int[] buckets = new int[3];
	
	State(int a, int b, int c) {
		buckets[0] = a;
		buckets[1] = b;
		buckets[2] = c;
	}
	
	State(int[] buckets) {
		this.buckets = buckets;
	}
	
	@Override
	public int compareTo(State s) {
		return buckets[2] - s.buckets[2] == 0 ? buckets[1] - s.buckets[1] == 0 ? buckets[0] - s.buckets[0] : buckets[1] - s.buckets[1] : buckets[2] - s.buckets[2];
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof State)) return false;
		State s = (State)obj;
		return buckets[0] == s.buckets[0] && buckets[1] == s.buckets[1] && buckets[2] == s.buckets[2];
	}
	
	@Override
	public int hashCode() {
		return buckets[0] * 400 + buckets[1] * 20 + buckets[2];
	}
	
	@Override
	public String toString() {
		return buckets[2] + " ";
	}
}