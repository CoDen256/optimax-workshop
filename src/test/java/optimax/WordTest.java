package optimax;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordTest {

    @Test
    void createWordGetSameLetters() {
        assertArrayEquals(letters("valid"), new Word("valid").getLetters());
        assertArrayEquals(letters("adult"), new Word("adult").getLetters());
        assertArrayEquals(letters("pesto"), new Word("pesto").getLetters());
        assertArrayEquals(letters("xxxxx"), new Word("xxxxx").getLetters());
        assertArrayEquals(letters("äüößz"), new Word("äüößz").getLetters());
    }



    @Test
    void createWordWithNumbersFail() {
        assertThrows(IllegalArgumentException.class, () -> new Word("vali5"));
        assertThrows(IllegalArgumentException.class, () -> new Word("1xxx5"));
        assertThrows(IllegalArgumentException.class, () -> new Word("12345"));
    }


    @Test
    void createWordWithNullFail() {
        assertThrows(NullPointerException.class, () -> new Word(null));
    }

    @Test
    void createWordWithSpecialCharactersFail() {
        assertThrows(IllegalArgumentException.class, () -> new Word("vali%"));
        assertThrows(IllegalArgumentException.class, () -> new Word("-xxx^"));
        assertThrows(IllegalArgumentException.class, () -> new Word("!234'"));
        assertThrows(IllegalArgumentException.class, () -> new Word("     "));
        assertThrows(IllegalArgumentException.class, () -> new Word("\n\n\n\n\n"));
    }

    @Test
    void createTooFewWordLettersFail() {
        assertThrows(IllegalArgumentException.class, () -> new Word("v"));
        assertThrows(IllegalArgumentException.class, () -> new Word("va"));
        assertThrows(IllegalArgumentException.class, () -> new Word("val"));
        assertThrows(IllegalArgumentException.class, () -> new Word("vali"));
    }

    @Test
    void createTooManyWordLettersFail() {
        assertThrows(IllegalArgumentException.class, () -> new Word("validv"));
        assertThrows(IllegalArgumentException.class, () -> new Word("validva"));
        assertThrows(IllegalArgumentException.class, () -> new Word("validval"));
        assertThrows(IllegalArgumentException.class, () -> new Word("hustensaft"));
    }

    @Test
    void createWordIsCaseInsensitive() {
        assertArrayEquals(letters("valid"), new Word("Valid").getLetters());
        assertArrayEquals(letters("valid"), new Word("VALID").getLetters());
        assertArrayEquals(letters("xxxxx"), new Word("XxXxX").getLetters());
    }

    @Test
    void createWordSameOrder() {
        assertArrayEquals(letters("alidv"), new Word("alidv").getLetters());
        assertArrayEquals(letters("abcde"), new Word("abcde").getLetters());
    }

    @Test
    void twoWordsAreNotTheSame() {
        assertNotEquals(new Word("abcde"), new Word("adcdf"));
    }

    @Test
    void twoWordsAreTheSame() {
        assertEquals(new Word("abcde"), new Word("abcde"));
        assertEquals(new Word("ABCDE"), new Word("abcde"));
    }

    private char[] letters(String word) {
        return word.toCharArray();
    }
}
