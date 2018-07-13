/*
ID: plutonj1
LANG: JAVA
TASK: cryptcow
*/

// coded according to the USACO analysis, times out on test case 8

import java.io.*;
import java.util.*;

class cryptcow {
    static String name = "cryptcow";
    static BufferedReader in;
    static PrintWriter out;
    
    static int[] orgCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 2, 0, 7, 1, 1, 2, 2, 0, 1, 0, 0, 3, 2, 1, 0, 1, 1, 4, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0};
    static String orgStr = "Begin the Escape execution at the Break of Dawn";
    static char[] org = orgStr.toCharArray();
    static char[] s;
    static int start;
    static int[] prev, next;
    static Map<Integer, Boolean> cache = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        s = in.readLine().toCharArray();
        start = 0;
        prev = new int[s.length];
        next = new int[s.length];
        for(int i = 0; i < s.length; i++) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }
        next[s.length - 1] = -1;
        int[] count = new int[128];
        for(char c : s) {
            count[c]++;
        }
        // check if character counts match
        for(int i = 0; i < 128; i++) {
            if(i != 'C' && i != 'O' && i != 'W' && count[i] != orgCount[i]) {
                out.println("0 0");
                out.close();
                return;
            }
        }
        // check if there are the same # of 'C's, 'O's, and 'W's
        if(count['C'] != count['O'] || count['O'] != count['W']) {
            out.println("0 0");
            out.close();
            return;
        }
        int enc = count['C'];
        
        out.println(dfs(s.length) ? "1 " + enc : "0 0");
        out.close();
    }
    
    static boolean dfs(int len) {
        if(len == 47) {
            return getStr().equals(orgStr);
        }
        int hash = getHash();
        if(cache.get(hash) != null) {
            return false;
        }
        int i = start, pre = -1, suf = -1, count = 0;
        // prefix check: the first encoding letter should be 'C', and all characters before that must correspond
        do {
            char c = s[i];
            if(c == 'O' || c == 'W') {
                cache.put(hash, false);
                return false;
            } else if(c == 'C') {
                pre = i;
                break;
            } else if(c != org[count++]) {
                cache.put(hash, false);
                return false;
            }
        } while((i = next[i]) != -1);
        // suffix check: the last encoding letter should be 'W', and all character after that must correspond
        i = start;
        count = 0;
        // go to last character
        while(next[i] != -1) {
            i = next[i];
        }
        do {
            char c = s[i];
            if(c == 'C' || c == 'O') {
                cache.put(hash, false);
                return false;
            } else if(c == 'W') {
                suf = i;
                break;
            } else if(c != org[46 - count++]) {
                cache.put(hash, false);
                return false;
            }
        } while((i = prev[i]) != -1);
        // pattern check: all char sequences between encoding letters should appear in the original string
        StringBuilder pat = new StringBuilder();
        i = next[pre];
        while(i != suf) {
            char c = s[i];
            if(c == 'C' || c == 'O' || c == 'W') {
                if(orgStr.indexOf(pat.toString()) < 0) {
                    cache.put(hash, false);
                    return false;
                }
                pat = new StringBuilder();
            } else {
                pat.append(c);
            }
            i = next[i];
        }
        if(orgStr.indexOf(pat.toString()) < 0) {
            cache.put(hash, false);
            return false;
        }
        i = start;
        do {
            if(s[i] == 'C') {
                int j = next[i];
                while(j != -1) {
                    if(s[j] == 'O') {
                        int k = next[j];
                        while(k != -1) {
                            if(s[k] == 'W') {
                                int startBuf = start;
                                int[] prevBuf = Arrays.copyOfRange(prev, 0, prev.length);
                                int[] nextBuf = Arrays.copyOfRange(next, 0, next.length);
                                int ind = start;
                                if(ind == i) {
                                    ind = next[j];
                                }
                                if(ind == k) {
                                    ind = next[i];
                                }
                                if(ind == j) {
                                    ind = next[k];
                                }
                                start = ind;
                                prev[ind] = -1;
                                if(ind == startBuf) {
                                    ind = next[j];
                                    if(ind == k) {
                                        ind = next[i];
                                    }
                                    if(ind == j) {
                                        ind = next[k];
                                    }
                                    next[prev[i]] = ind;
                                    if(ind != -1) {
                                        prev[ind] = prev[i];
                                    }
                                }
                                if(ind == next[j]) {
                                    ind = next[i];
                                    if(ind == j) {
                                        ind = next[k];
                                    }
                                    next[prev[k]] = ind;
                                    if(ind != -1) {
                                        prev[ind] = prev[k];
                                    }
                                }
                                if(ind == next[i]) {
                                    ind = next[k];
                                    next[prev[j]] = ind;
                                    if(ind != -1) {
                                        prev[ind] = prev[j];
                                    }
                                }
                                if(dfs(len - 3)) {
                                    return true;
                                }
                                start = startBuf;
                                prev = prevBuf;
                                next = nextBuf;
                            }
                            k = next[k];
                        }
                    }
                    j = next[j];
                }
            }
        } while((i = next[i]) != -1);
        cache.put(hash, false);
        return false;
    }
    
    static String getStr() {
        int i = start;
        StringBuilder str = new StringBuilder();
        do {
            str.append(s[i]);
        } while((i = next[i]) != -1);
        return str.toString();
    }
    
    static int getHash() {
        int i = start, hash = 417;
        do {
            hash += s[i] + hash * 7723;
            hash %= 502973;
        } while((i = next[i]) != -1);
        return getStr().hashCode();
    }
}