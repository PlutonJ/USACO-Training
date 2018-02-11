/*
ID: plutonj1
LANG: JAVA
TASK: namenum
*/

import java.io.*;
import java.util.*;

class namenum {
	static String name = "namenum";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		
		BufferedReader reader = new BufferedReader(new FileReader("dict.txt"));
		
		
		int[] c2n = {2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, -1, 7, 7, 8, 8, 8, 9, 9, 9, -1};
		List<String> list = new ArrayList<String>(5000);
		List<String> numList = new ArrayList<String>(5000);
		String name = null;
		out: while((name = reader.readLine()) != null) {	// parse dictionary into number format
			list.add(name);
			char[] charArr = name.toCharArray();
			StringBuilder buf = new StringBuilder();
			for(char c : charArr) {
				if(c2n[c - 65] == -1) {			// if invalid char append empty string so the mapping of indices doesn't get messed up
					numList.add("");
					continue out;
				}
				buf.append(c2n[c - 65]);
			}
			numList.add(buf.toString());
		}
		
		List<String> validNames = new LinkedList<String>();
		String n = in.readLine();
		for(int i = 0; i < numList.size(); i++) {
			if(numList.get(i).equals(n)) {
				validNames.add(list.get(i));			// able to do this because indices of numList map directly to indices of list
			}
		}
		
		
		// below is dumb me trying to (basically) brute force
		/*TreeSet<String> list = new TreeSet<String>();
		String name = null;
		while((name = reader.readLine()) != null) {
			list.add(name);
		}
		reader.close();
		
		char[][] n2c = {{'A', 'B', 'C'}, {'D', 'E', 'F'}, {'G', 'H', 'I'}, 
						{'J', 'K', 'L'}, {'M', 'N', 'O'}, {'P', 'R', 'S'}, 
						{'T', 'U', 'V'}, {'W', 'X', 'Y'}};
		List<StringBuilder> buf = new LinkedList<StringBuilder>(), bufbuf;
		buf.add(new StringBuilder());
		String n = in.readLine();
		for(int i = 0; i < n.length(); i++) {
			bufbuf = new LinkedList<StringBuilder>();
			for(StringBuilder str : buf) {
				if(list.tailSet(str.toString()).isEmpty()) {
					System.out.println("asd");
					continue;
				}
				for(char c : n2c[Character.getNumericValue(n.charAt(i)) - 2]) {
					bufbuf.add(new StringBuilder(str).append(c));
				}
			}
			buf = bufbuf;
		}
		List<String> validNames = new LinkedList<String>();
		for(StringBuilder nameb : buf) {
			if(nameb.length() == n.length() && list.contains(nameb.toString())) {
				validNames.add(nameb.toString());
			}
		}*/
		
		if(validNames.size() == 0) {
			out.println("NONE");
		} else {
			for(String nameb : validNames) {
				out.println(nameb);
			}
		}
		out.close();
	}
}

/*class Trie {
	boolean root;
	char c;
	Trie parent;
	List<Trie> children;
	
	Trie() {
		root = true;
		children = new LinkedList<>();
	}
	
	Trie(char c) {
		root = false;
		this.c = c;
		children = new LinkedList<>();
	}
	
	Trie has(char c) {
		for(Trie child : children) if(child.c == c) return child;
		return null;
	}
	
	Trie add(char c) {
		Trie child = new Trie(c);
		child.parent = this;
		children.add(child);
		return child;
	}
	
	String get() {
		if(root) return "";
		return parent.get() + c;
	}
}*/