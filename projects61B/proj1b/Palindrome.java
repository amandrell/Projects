public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        String text = word;
        Deque<Character> letters = new ArrayDeque<>();
        for (int i = 0; i < text.length(); i++) {
            char getchar = text.charAt(i);
            letters.addLast(getchar);
        }
        return letters;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        if (word.charAt(0) != word.charAt(word.length() - 1)) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        }
        if (!cc.equalChars(word.charAt(0), word.charAt(word.length() - 1))) {
            return false;
        }
        return isPalindrome((word.substring(1, word.length() - 1)), cc);
    }




}
