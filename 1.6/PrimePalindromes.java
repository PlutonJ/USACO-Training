/*
ID: plutonj1
LANG: JAVA
TASK: pprime
*/

import java.io.*;
import java.util.*;

class pprime {
	static String name = "pprime";
	
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int a = Integer.parseInt(input.nextToken()), b = Integer.parseInt(input.nextToken());
		out: for(int z = 0; z == 0; z++) {		// this loop is only for easy termination
			if(5 == a) out.println(5);
			if(7 >= a && 7 <= b) out.println(7);
			if(11 >= a && 11 <= b) out.println(11);									// all prime palindromes < 100 from sample
			boolean evenPalindrome2Big = false;										// "evenPalindromeTooBig"
			String print = "";
			for(int i = 1; i <= 9; i++) {
				for(int j = 0; j <= 9; j++) {
					int x = getOddPalindrome(i, j);
					if(x > b) break out;											// since checking all palindromes in ascending order, if any > b then terminate
					if(x >= a && isPrime(x)) out.println(x);
					if(!evenPalindrome2Big)	{										// since even length palindromes have 1 more digit than the odd length palindromes, this is required
						x = getEvenPalindrome(i, j);								// so the computation does not terminate when an even palindrome > b is found and finishes computing
						if(x > b) evenPalindrome2Big = true;						// the odd length ones
						if(x >= a && isPrime(x)) print = print + x + "\n";			// since even length palindromes are larger, hey are stored to be printed after the odd length ones
					}
				}
			}
			out.print(print);
			print = "";
			if(evenPalindrome2Big) break;
			
			for(int i = 1; i <= 9; i++) {
				for(int j = 0; j <= 9; j++) {
					for(int k = 0; k <= 9; k++) {
						int x = getOddPalindrome(i, j, k);
						if(x > b) break out;											// since checking all palindromes in ascending order, if any > b then terminate
						if(x >= a && isPrime(x)) out.println(x);
						if(!evenPalindrome2Big)	{										// since even length palindromes have 1 more digit than the odd length palindromes, this is required
							x = getEvenPalindrome(i, j, k);								// so the computation does not terminate when an even palindrome > b is found and finishes computing
							if(x > b) evenPalindrome2Big = true;						// the odd length ones
							if(x >= a && isPrime(x)) print = print + x + "\n";			// since even length palindromes are larger, hey are stored to be printed after the odd length ones
						}
					}
				}
			}
			out.print(print);
			print = "";
			if(evenPalindrome2Big) break;
			
			for(int i = 1; i <= 9; i++) {
				for(int j = 0; j <= 9; j++) {
					for(int k = 0; k <= 9; k++) {
						for(int l = 0; l <= 9; l++) {
							int x = getOddPalindrome(i, j, k, l);
							if(x > b) break out;											// since checking all palindromes in ascending order, if any > b then terminate
							if(x >= a && isPrime(x)) out.println(x);
							if(!evenPalindrome2Big)	{										// since even length palindromes have 1 more digit than the odd length palindromes, this is required
								x = getEvenPalindrome(i, j, k, l);							// so the computation does not terminate when an even palindrome > b is found and finishes computing
								if(x > b) evenPalindrome2Big = true;						// the odd length ones
								if(x >= a && isPrime(x)) print = print + x + "\n";			// since even length palindromes are larger, hey are stored to be printed after the odd length ones
							}
						}
					}
				}
			}
			out.print(print);
			print = "";
			if(evenPalindrome2Big) break;
			
			for(int i = 1; i <= 9; i++) {
				for(int j = 0; j <= 9; j++) {
					for(int k = 0; k <= 9; k++) {
						for(int l = 0; l <= 9; l++) {
							for(int m = 0; m <= 9; m++) {
								int x = getOddPalindrome(i, j, k, l, m);
								if(x > b) break out;											// since checking all palindromes in ascending order, if any > b then terminate
								if(x >= a && isPrime(x)) out.println(x);
							}
						}
					}
				}
			}
		}
		
		out.close();
	}
	
	static int getEvenPalindrome(int... digits) {		// e.g. 123321
		int n = digits.length, x = 0;
		for(int i = 0; i < n; i++) {
			x += digits[i] * (int)Math.pow(10, i) + digits[i] * (int)Math.pow(10, 2 * n - i - 1);
		}
		//System.out.println(x);
		return x;
	}
	
	static int getOddPalindrome(int... digits) {		// e.g. 12321
		int n = digits.length - 1, x = 0;
		for(int i = 0; i < n; i++) {
			x += digits[i] * (int)Math.pow(10, i) + digits[i] * (int)Math.pow(10, 2 * n - i);
		}
		//System.out.println(x + digits[n] * (int)Math.pow(10, n));
		return x + digits[n] * (int)Math.pow(10, n);
	}
	
	static boolean isPrime(int p) {
		int maxDivisor = (int)Math.sqrt(p);
		//System.out.println(p);
		for(int i = 2; i <= maxDivisor; i++) {
			if(p % i == 0) return false;
		}
		return true;
	}
}