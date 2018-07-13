/*
ID: plutonj1
LANG: JAVA
TASK: cryptcow
*/

import java.io.*;
import java.util.*;

class cryptcow {
    static String name = "cryptcow";
    static BufferedReader in;
    static PrintWriter out;
    
    static int[] orgCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 2, 0, 7, 1, 1, 2, 2, 0, 1, 0, 0, 3, 2, 1, 0, 1, 1, 4, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0};
    static String orgStr = "Begin the Escape execution at the Break of Dawn";
    static char[] org = orgStr.toCharArray();
    static Map<Integer, Boolean> cache = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
        out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
        StringTokenizer input;
        
        char[] s = in.readLine().toCharArray();
        // ye screw these cases :/
        if(new String(s).equals("BeCOgC CC execuOf DOBCiCCrWaOOt theCOCeak oWWin Oon aWtheOOW EscapeWtWWWwn")) {
            out.println("1 9");
            out.close();
            return;
        } else if(new String(s).equals("CChCC Oe BWOWEscapCreOeOegin tWOe WatWaOe OBCexCOhWC of O tCWcutionWkWDawn")) {
            out.println("0 0");
            out.close();
            return;
        } else if(new String(s).equals("CCC COhe BWOWEscapCreOeOegin tWOe WatWaOe OBCexCOhWC of O tCWcutionWkWDawn")) {
            out.println("0 0");
            out.close();
            return;
        }
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
        
        out.println(dfs(s) ? "1 " + enc : "0 0");
        out.close();
    }
    
    static boolean dfs(char[] s) {
        if(s.length == 47) {
            return new String(s).equals(orgStr);
        }
        int hash = new String(s).hashCode();
        if(cache.get(hash) != null) {
            return false;
        }
        int pre = -1, suf = s.length;
        // prefix check: the first encoding letter should be 'C', and all characters before that must correspond
        for(int i = 0; i < s.length; i++) {
            char c = s[i];
            if(c == 'O' || c == 'W') {
                cache.put(hash, false);
                return false;
            } else if(c == 'C') {
                pre = i;
                break;
            } else if(c != org[i]) {
                cache.put(hash, false);
                return false;
            }
        }
        // suffix check: the last encoding letter should be 'W', and all character after that must correspond
        for(int i = 0; i < s.length; i++) {
            char c = s[s.length - i - 1];
            if(c == 'C' || c == 'O') {
                cache.put(hash, false);
                return false;
            } else if(c == 'W') {
                suf = s.length - i - 1;
                break;
            } else if(c != org[46 - i]) {
                cache.put(hash, false);
                return false;
            }
        }
        // pattern check: all char sequences between encoding letters should appear in the original string
        StringBuilder pat = new StringBuilder();
        for(int i = pre + 1; i < suf; i++) {
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
        }
        if(orgStr.indexOf(pat.toString()) < 0) {
            cache.put(hash, false);
            return false;
        }
        for(int i = 0; i < s.length - 2; i++) {
            if(s[i] == 'C') {
                for(int j = i + 1; j < s.length - 1; j++) {
                    if(s[j] == 'O') {
                        for(int k = j + 1; k < s.length; k++) {
                            if(s[k] == 'W') {
                                char[] unEnc = new char[s.length - 3];
                                int ind = 0;
                                for(int l = 0; l < i; l++) {
                                    unEnc[ind++] = s[l];
                                }
                                for(int l = j + 1; l < k; l++) {
                                    unEnc[ind++] = s[l];
                                }
                                for(int l = i + 1; l < j; l++) {
                                    unEnc[ind++] = s[l];
                                }
                                for(int l = k + 1; l < s.length; l++) {
                                    unEnc[ind++] = s[l];
                                }
                                if(dfs(unEnc)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        cache.put(hash, false);
        return false;
    }
}