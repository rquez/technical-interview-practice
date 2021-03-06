import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ricardo on 10/21/15.
 */
public class StringProblems {

    // Implement an algorithm to determine if a string has all unique characters.
    // What if you cannot use additional data structures?
    // Lets assume the string has only ASCII characters.
    // O(n) time, O(1) space
    public static boolean hasUniqueCharacters(String s) {

        if (s.length() > 128) { return false; }

        // boolean are initialized to false by default
        boolean[] asciiTable = new boolean[128];

        for (int i = 0; i < s.length(); i++) {
            if (asciiTable[s.charAt(i)]) {
                return false;
            } else {
                asciiTable[s.charAt(i)] = true;
            }
        }
        return true;
    }
    public static boolean hasUniqueCharactersAZ(String s) {

        int checker = 0;

        for (int i = 0; i < s.length(); i++) {

            int value = s.charAt(i) - 'a';
            int bitShift = 1 << value;
            if ((checker & bitShift) > 0) {
               return false;
            }

            checker = checker | bitShift;
        }

        return true;
    }

    // Given two strings, write a method to decide if one is a permutation of the other.
    // O(n^2) time, O(1) space
    public static boolean isPermutation(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            if(b.indexOf(a.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
    // O(2n) time, O(n) space
    public static boolean isPermutationHash(String a, String b) {
        if (a.length() != b.length()) { return false; }
        HashSet<Character> hashSet = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            if (!hashSet.contains(a.charAt(i))) {
                hashSet.add(a.charAt(i));
            }
        }
        for (int i = 0; i < b.length(); i++) {
            if (!hashSet.contains(b.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    // O(n) time, O(1) space, only a-z
    public static boolean isPermutationBitShift(String a, String b) {
        if (a.length() != b.length()) { return false; }
        int aVal = 0, bVal = 0;
        for (int i = 0; i < a.length(); i++) {
            aVal |= 1 << (a.charAt(i) - 'a');
            bVal |= 1 << (b.charAt(i) - 'a');
        }
        if (aVal == bVal) { return true; }
        else { return false; }
    }
    // O(2n) time, O(1) space, assume 256 letters
    public static boolean isPermutationFrequencyTable(String a, String b) {
        if (a.length() != b.length()) { return false; }

        int[] letters = new int[256];
        for (int i = 0; i < a.length(); i++) {
            letters[a.charAt(i)]++;
        }
        for (int i = 0; i < b.length(); i++) {
            if (--letters[b.charAt(i)] < 0) {
                return false;
            }
        }
        return true;
    }

    /*
    Implement a method to perform basic string compression using the counts of repeated characters.
    For example, the string aabcccccaaa would become a2blc5a3. If the "compressed" string would not become smaller
    than the original string, your method should return the original string.
    */
    // O(n) time, O(n) space
    public static String compress(String s) {
        if (s.length() < 3) { return s; }

        StringBuffer sb = new StringBuffer();

        char temp = s.charAt(0);
        int count = 1;
        sb.append(temp);

        for (int i = 1; i < s.length(); i++) {
            if (temp != s.charAt(i)) {
                sb.append(count);
                count = 1;
                temp = s.charAt(i);
                sb.append(temp);
            } else {
                count++;
                if (i == s.length() - 1) {
                    sb.append(count);
                }
            }
        }

        String result = sb.toString();
        if (result.length() > s.length())
            return s;
        else
            return result;
    }

    /*
    Assume you have a method isSubstring which checks if one word is a substring of another.
    Given two strings, s1 and s2, write code to check if s2 is a rotation of s1, using only one call to
    isSubstring (e.g., "waterbottle" is a rotation of "erbottlewat")
     */
    // O(2n) time, O(n) space
    public static boolean isRotation(String s1, String s2) {

        if (s1.length() != s2.length()) { return false; }
        int length = s1.length();

        char c = s1.charAt(0);
        int cIndex = s2.indexOf(c);
        if (cIndex == -1) { return false; }

        StringBuffer sb = new StringBuffer();
        int modIndex = cIndex + 1;
        while(sb.length() < length - 1) {
            sb.append(s2.charAt(modIndex % length));
            modIndex++;
        }

        return isSubstring(s1, sb.toString());
    }
    // O(n) time, O(n) space
    public static boolean isRotationFaster(String s1, String s2) {
        if (s1.length() == s2.length()) {
            return isSubstring(s1+s1, s2);
        } else {
            return false;
        }
    }
    public static boolean isSubstring(String s1, String sub) {
        if (s1.indexOf(sub) != -1) return true;
        else return false;
    }

    public static class UnitTest {
        @Test
        public void testHasUniqueCharacters() {
            assertTrue(!hasUniqueCharacters("hello"));
            assertTrue(hasUniqueCharacters("helo"));
            assertTrue(!hasUniqueCharacters(new String(new char[129])));
            assertTrue(hasUniqueCharactersAZ("helo"));
            assertTrue(!hasUniqueCharactersAZ("hello"));


        }

        @Test
        public void testIsPermutation() {
            assertTrue(isPermutation("hello", "elloh"));
            assertTrue(!isPermutation("hellt", "elloh"));
            assertTrue(!isPermutation("hllo", "elloh"));
            assertTrue(isPermutationHash("hello", "elloh"));
            assertTrue(!isPermutationHash("hellt", "elloh"));
            assertTrue(!isPermutationHash("hllo", "elloh"));
            assertTrue(isPermutationBitShift("hello", "elloh"));
            assertTrue(!isPermutationBitShift("hellt", "elloh"));
            assertTrue(!isPermutationBitShift("hllo", "elloh"));
            assertTrue(isPermutationBitShift("lobster", "lobster"));
            assertTrue(!isPermutationBitShift("lobstxr", "lobster"));
            assertTrue(isPermutationFrequencyTable("hello", "elloh"));
            assertTrue(!isPermutationFrequencyTable("hellt", "elloh"));
            assertTrue(!isPermutationFrequencyTable("hllo", "elloh"));
        }

        @Test
        public void testCompress() {
            assertEquals("a2b1c5a4", compress("aabcccccaaaa"));
            assertEquals("abcd", compress("abcd"));
            assertEquals("a5b5c1d1r1f1a6c5", compress("aaaaabbbbbcdrfaaaaaaccccc"));
        }

        @Test
        public void testIsRotation() {
            assertTrue(isRotation("waterbottle", "bottlewater"));
            assertTrue(isRotation("Hello", "oHell"));
            assertTrue(isRotation("waterbottle", "terbottlewa"));
            assertTrue(!isRotation("waterbottel", "terbottlewa"));
            assertTrue(isRotationFaster("waterbottle", "bottlewater"));
            assertTrue(isRotationFaster("Hello", "oHell"));
            assertTrue(isRotationFaster("waterbottle", "terbottlewa"));
            assertTrue(!isRotationFaster("waterbottel", "terbottlewa"));
        }
    }
}
