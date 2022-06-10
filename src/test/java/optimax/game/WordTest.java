package optimax.game;

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
        assertEquals("valid", new Word("valid").word());
        assertEquals("adult", new Word("adult").word());
        assertEquals("pesto", new Word("pesto").word());
        assertEquals("xxxxx", new Word("xxxxx").word());
        assertEquals("äüößz", new Word("äüößz").word());
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
        assertEquals("valid", new Word("Valid").word());
        assertEquals("valid", new Word("VALID").word());
        assertEquals("xxxxx", new Word("XxXxX").word());
    }

    @Test
    void createWordSameOrder() {
        assertEquals("alidv", new Word("alidv").word());
        assertEquals("abcde", new Word("abcde").word());
    }

    @Test
    void twoWordsAreNotTheSame() {
        assertNotEquals(new Word("abcde"), new Word("adcdf"));
    }

    @Test
    void twoWordsAreTheSame() {
        assertEquals(new Word("abcde"), new Word("abcde"));
        assertEquals(new Word("ABCDE"), new Word("abcde"));
        assertEquals(new Word("xxxxx"), new Word("xxxxx"));
    }

    @Test
    void word_string_represents_word() {
        assertEquals("<a,b,c,d,e>", new Word("abcde").toString());
        assertEquals("<v,a,l,i,d>", new Word("VALID").toString());
        assertEquals("<x,x,x,x,x>", new Word("xxxxx").toString());
    }
}
