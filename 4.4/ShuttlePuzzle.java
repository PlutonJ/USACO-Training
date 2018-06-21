/*
ID: plutonj1
LANG: JAVA
TASK: shuttle
*/

// bfs brute force

import java.io.*;
import java.util.*;

class shuttle {
	static String name = "shuttle";
	static BufferedReader in;
	static PrintWriter out;
    
    static int N, n;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        N = Integer.parseInt(in.readLine());
        n = 2 * N + 1;
        Queue<BFSEntry> bfs = new LinkedList<>();
        StringBuilder start = new StringBuilder(), end = new StringBuilder();
        for(int i = 0; i < N; i++) {
            start.append("W");
            end.append("B");
        }
        start.append(" ");
        end.append(" ");
        for(int i = 0; i < N; i++) {
            start.append("B");
            end.append("W");
        }
        bfs.offer(new BFSEntry(start.toString(), ""));
        String endStr = end.toString();
        while(true) {
            BFSEntry entry = bfs.poll();
            if(new String(entry.state).equals(endStr)) {
                String output = entry.path;
                input = new StringTokenizer(output);
                int count = 0;
                while(input.hasMoreTokens()) {
                    out.print(input.nextToken());
                    if(input.hasMoreTokens()) {
                        if((++count) % 20 != 0) {
                            out.print(" ");
                        } else {
                            out.println("");
                        }
                    }
                }
                out.println("");
                break;
            }
            for(BFSEntry e : entry.get()) {
                bfs.offer(e);
            }
        }
		
		out.close();
	}
}

// W's should always move right and B's should always move left in an optimal sequence
/*
class BFSEntry {
    String state, path;
    
    BFSEntry(String state, String path) {
        this.state = state;
        this.path = path;
    }
    
    List<BFSEntry> get() {
        int i = state.indexOf(" ");
        List<BFSEntry> neighbors = new ArrayList<>(4);
        String zeroToI = state.substring(0, i), iPlusOne = state.substring(i + 1);
        if(i - 2 >= 0 && state.charAt(i - 2) == 'W') {
            neighbors.add(new BFSEntry(state.substring(0, i - 2) + " " + state.charAt(i - 1) + state.charAt(i - 2) + iPlusOne, path + (i - 1) + " "));
        }
        if(i - 1 >= 0 && state.charAt(i - 1) == 'W') {
            neighbors.add(new BFSEntry(state.substring(0, i - 1) + " " + state.charAt(i - 1) + iPlusOne, path + i + " "));
        }
        if(i + 1 < 2 * shuttle.N + 1 && state.charAt(i + 1) == 'B') {
            neighbors.add(new BFSEntry(zeroToI + state.charAt(i + 1) + " " + state.substring(i + 2), path + (i + 2) + " "));
        }
        if(i + 2 < 2 * shuttle.N + 1 && state.charAt(i + 2) == 'B') {
            neighbors.add(new BFSEntry(zeroToI + state.charAt(i + 2) + state.charAt(i + 1) + " " + state.substring(i + 3), path + (i + 3) + " "));
        }
        return neighbors;
    }
}
*/

// using char arrays instead of string cuts about half the time
class BFSEntry {
    char[] state;
    String path;
    
    BFSEntry(String state, String path) {
        this.state = state.toCharArray();
        this.path = path;
    }
    
    BFSEntry(char[] state, String path) {
        this.state = state;
        this.path = path;
    }
    
    List<BFSEntry> get() {
        int i = -1;
        for(int j = 0; j < shuttle.n; j++) {
            if(state[j] == ' ') {
                i = j;
                break;
            }
        }
        List<BFSEntry> neighbors = new ArrayList<>(4);
        if(i - 2 >= 0 && state[i - 2] == 'W') {
            char[] newState = new char[shuttle.n];
            int ind = 0;
            for(int j = 0; j < i - 2; j++) {
                newState[ind++] = state[j];
            }
            newState[ind++] = ' ';
            newState[ind++] = state[i - 1];
            newState[ind++] = state[i - 2];
            for(int j = i + 1; j < shuttle.n; j++) {
                newState[ind++] = state[j];
            }
            neighbors.add(new BFSEntry(newState, path + (i - 1) + " "));
        }
        if(i - 1 >= 0 && state[i - 1] == 'W') {
            char[] newState = new char[shuttle.n];
            int ind = 0;
            for(int j = 0; j < i - 1; j++) {
                newState[ind++] = state[j];
            }
            newState[ind++] = ' ';
            newState[ind++] = state[i - 1];
            for(int j = i + 1; j < shuttle.n; j++) {
                newState[ind++] = state[j];
            }
            neighbors.add(new BFSEntry(newState, path + i + " "));
        }
        if(i + 1 < shuttle.n && state[i + 1] == 'B') {
            char[] newState = new char[shuttle.n];
            int ind = 0;
            for(int j = 0; j < i; j++) {
                newState[ind++] = state[j];
            }
            newState[ind++] = state[i + 1];
            newState[ind++] = ' ';
            for(int j = i + 2; j < shuttle.n; j++) {
                newState[ind++] = state[j];
            }
            neighbors.add(new BFSEntry(newState, path + (i + 2) + " "));
        }
        if(i + 2 < shuttle.n && state[i + 2] == 'B') {
            char[] newState = new char[shuttle.n];
            int ind = 0;
            for(int j = 0; j < i; j++) {
                newState[ind++] = state[j];
            }
            newState[ind++] = state[i + 2];
            newState[ind++] = state[i + 1];
            newState[ind++] = ' ';
            for(int j = i + 3; j < shuttle.n; j++) {
                newState[ind++] = state[j];
            }
            neighbors.add(new BFSEntry(newState, path + (i + 3) + " "));
        }
        return neighbors;
    }
}