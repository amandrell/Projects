import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualsChars() {
        char x = '%';
        char y = '&';
        char upper = 'X';
        char a = 'x';
        char b = 'y';
        char d = 'Y';
        char c = 'x';
        assertFalse(offByOne.equalChars(upper, a));
        assertTrue(offByOne.equalChars(a, b));
        assertFalse(offByOne.equalChars(a, c));
        assertFalse(offByOne.equalChars(a, x));
        assertTrue(offByOne.equalChars(x, y));
        assertFalse(offByOne.equalChars(a, d));

    }
}
