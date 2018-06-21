/*
ID: plutonj1
LANG: JAVA
TASK: lgame
*/

import java.io.*;
import java.util.*;

class lgame {
	static String name = "lgame";
	static BufferedReader in, dict;
	static PrintWriter out;
    
    static int[] values = new int[] {2, 5, 4, 4, 1, 6, 5, 5, 1, 7, 6, 3, 5, 2, 3, 5, 7, 2, 1, 2, 4, 6, 6, 7, 5, 7};
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        dict = new BufferedReader(new FileReader(name + ".dict"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
        
        int maxPossibleScore = 0;
        int[] maxCount = new int[26];
        for(char c : in.readLine().toCharArray()) {
            maxPossibleScore += values[c - 'a'];
            maxCount[c - 'a']++;
        }
        
        List<Entry> entries = new ArrayList<>();
        String cheese;
        next: while(!".".equals(cheese = dict.readLine())) {
            int[] count = new int[26];
            int score = 0;
            for(char c : cheese.toCharArray()) {
                score += values[c - 'a'];
                if(score > maxPossibleScore) {
                    continue next;
                }
                count[c - 'a']++;
                if(count[c - 'a'] > maxCount[c - 'a']) {
                    continue next;
                }
            }
            Entry entry = new Entry(cheese, score);
            if(cheese.length() <= 4) {
                for(int i = 0; i < 26; i++) {
                    count[i] = maxCount[i] - count[i];
                }
                entry.set(count);
            }
            entries.add(entry);
        }
        Collections.sort(entries, (a, b) -> b.score - a.score);
        int maxScore = entries.get(0).score;
        Set<String> max = new TreeSet<>();
        for(Entry entry : entries) {
            if(entry.score == maxScore) {
                max.add(entry.word);
            } else {
                break;
            }
        }
        for(Entry entry : entries) {
            if(entry.other != null) {
                List<Entry> other = new ArrayList<>();
                next: for(Entry e : entries) {
                    int[] count = new int[26];
                    for(char c : e.word.toCharArray()) {
                        count[c - 'a']++;
                        if(count[c - 'a'] > entry.other[c - 'a']) {
                            continue next;
                        }
                    }
                    other.add(e);
                }
                Collections.sort(other, (a, b) -> b.score - a.score);
                if(other.size() > 0) {
                    if(entry.score + other.get(0).score > maxScore) {
                        maxScore = entry.score + other.get(0).score;
                        max.clear();
                    }
                    for(Entry e : other) {
                        if(entry.score + e.score == maxScore) {
                            max.add(make(entry.word, e.word));
                        } else {
                            break;
                        }
                    }
                }
            }
        }
		
        out.println(maxScore);
        for(String m : max) {
            out.println(m);
        }
		out.close();
	}
    
    static String make(String a, String b) {
        if(a.compareTo(b) < 0) {
            return a + " " + b;
        } else {
            return b + " " + a;
        }
    }
}

class Entry {
    String word;
    int score;
    int[] other;
    
    Entry(String word, int score) {
        this.word = word;
        this.score = score;
    }
    
    void set(int[] c) {
        other = c;
    }
}