import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {

        String edge0 = "";
        String edge1 = "a";
        String test0 = "racecar";
        String test1 = "noon";
        String test2 = "project";
        String test3 = "flake";
        String test4 = "aaaa";
        assertTrue(palindrome.isPalindrome(edge0));
        assertTrue(palindrome.isPalindrome(edge1));
        assertFalse(palindrome.isPalindrome(test2));
        assertTrue(palindrome.isPalindrome(test0));
        assertTrue(palindrome.isPalindrome(test1));
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome(test3, obo));
        assertFalse(palindrome.isPalindrome(test4, obo));
    }

}

