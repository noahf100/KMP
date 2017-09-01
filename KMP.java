package Test;

import java.util.Arrays;
import java.util.Random;

public class KMP {

	private static String genString(int len){
		String gen = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		int genLen = gen.length();
		
		for(int i = 0; i < len; i++){
			sb.append(gen.charAt(r.nextInt(genLen)));
		}
		return sb.toString();
	}
	
	public static int naive(String a, String b){
		int al = a.length();
		int bl = b.length();
		
		for(int i = 0; i < al - bl + 1; i++){
			boolean found = true;
			int pointer1 = i;
			for(int j = 0; j < bl; j++){
				if(a.charAt(pointer1) != b.charAt(j)){
					found = false;
					break;
				}
				pointer1 ++;
			}
			if(found){
				return i;
			}
		}
		return -1;
	}
	
	private static int kmp(String a, String b){
		int[] bAr = tableBuilder(b);
		//System.out.println(Arrays.toString(bAr));
		int al = a.length();
		int bl = b.length();
		
		int pointer2 = 0;
		for(int i = 0; i < al; i++){
			if(pointer2 == bl){
				return i - bl;
			}
			if(a.charAt(i) == b.charAt(pointer2)){
				pointer2 ++;
			}
			else{
				if(pointer2 != 0){
					pointer2 = bAr[pointer2 - 1];
					i--;
				}
			}
		}
		if(pointer2 == bl){
			return al - bl;
		}
		return -1;
	}
	
	private static int[] tableBuilder(String b){
		int bl = b.length();
		int[] toReturn = new int[bl];
		
		if(bl == 1){
			return new int[]{0};
		}
		
		int p1 = 0;
		int p2 = 1;
		toReturn[0] = 0;
		
		for(p2 = 1; p2 < bl; p2++){
			if(b.charAt(p1) == b.charAt(p2)){
				toReturn[p2] = p1 + 1;
				p1 ++;
			}
			else{
				if(p1 == 0){
					toReturn[p2] = 0;
					continue;
				}
				p1 = toReturn[toReturn[p1 - 1]];
				if(b.charAt(p1) == b.charAt(p2)){
					toReturn[p2] = p1 + 1;
				}
				else{
					toReturn[p2] = 0;
				}
			}
		}
		return toReturn;
	}
	
	public static void main(String[] args){
		final int NUM_TESTS = 1000000;
		
		/*System.out.println(test(
				"abxabcabcaby", "abcaby"
				));
		System.out.println(
				test(
				"EOFQaIGMUnYOZtayyBemGxyTdtqHOReeHHlfKgnnOYfYmEOPiSFIsGlvCfqtAtJekMzdCqEuSjhadCSmNlXPQmCWkwzNUBMUnxEn", 
				"ImeSCqdBMn")
				);
		System.exit(0);*/
		boolean passed = true;
		for(int i = 0; i < NUM_TESTS; i++){
			String a = genString(100);
			String b = genString(2);
			int f = naive(a, b);
			int g = kmp(a, b);
			if(g != f){
				passed = false;
				System.out.println("Fail: " + f + ", " + g + " --- " + a + " --- " + b);
				break;
			}
		}
		passed = passed && (naive("abxabcabcaby", "abcaby") == kmp("abxabcabcaby", "abcaby"));
		if(passed){
			System.out.println("PASSED!");
		}
	}
	
	private static boolean test(String a, String b){
		int aa = naive(a, b);
		int bb = kmp(a, b);
		System.out.println(aa);
		System.out.println(bb);
		return aa == bb;
	}
}
