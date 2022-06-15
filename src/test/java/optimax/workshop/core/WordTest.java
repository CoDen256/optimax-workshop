package optimax.workshop.core;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void wordStringRepresentsWord() {
        assertEquals("<abcde>", new Word("abcde").toString());
        assertEquals("<valid>", new Word("VALID").toString());
        assertEquals("<xxxxx>", new Word("xxxxx").toString());
    }

    @Test
    void letter() {
        Word word = new Word("aBcDV");
        assertEquals('a', word.letter(0));
        assertEquals('b', word.letter(1));
        assertEquals('c', word.letter(2));
        assertEquals('d', word.letter(3));
        assertEquals('v', word.letter(4));
    }


    @Test
    void letters() {
        assertThat(new Word("aBcDV").letters()).containsExactly(
                new Letter('a', 0),
                new Letter('b', 1),
                new Letter('c', 2),
                new Letter('d', 3),
                new Letter('v', 4)
        ).inOrder();
        assertThat(new Word("PQIWU").letters()).containsExactly(
                new Letter('p', 0),
                new Letter('q', 1),
                new Letter('i', 2),
                new Letter('w', 3),
                new Letter('u', 4)
        ).inOrder();
    }

    @Test
    void wordIsValid() {
        assertTrue(Word.isValid("asdfg"));
        assertTrue(Word.isValid("ASDFg"));
        assertTrue(Word.isValid("AAAAA"));
        assertTrue(Word.isValid("xxxxx"));
        assertTrue(Word.isValid("AxXaX"));
        assertFalse(Word.isValid("AxXaXaX"));
        assertFalse(Word.isValid(""));
        assertFalse(Word.isValid(" "));
        assertFalse(Word.isValid("12"));
        assertFalse(Word.isValid("12345"));
        assertFalse(Word.isValid("12345"));
        assertFalse(Word.isValid("%%%%%"));
        assertFalse(Word.isValid("     "));
    }

}
