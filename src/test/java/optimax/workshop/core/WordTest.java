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
        assertEquals("valid", new Word("valid").toString());
        assertEquals("adult", new Word("adult").toString());
        assertEquals("pesto", new Word("pesto").toString());
        assertEquals("xxxxx", new Word("xxxxx").toString());
    }

    @Test
    void createWordWithNumbersFail() {
        assertThrows(Word.InvalidLetterException.class, () -> new Word("vali5"));
        assertThrows(Word.InvalidLetterException.class, () -> new Word("1xxx5"));
        assertThrows(Word.InvalidLetterException.class, () -> new Word("12345"));
    }


    @Test
    void createWordWithNullFail() {
        assertThrows(NullPointerException.class, () -> new Word(null));
    }

    @Test
    void createWordWithSpecialCharactersFail() {
        assertThrows(Word.InvalidLetterException.class, () -> new Word("vali%"));
        assertThrows(Word.InvalidLetterException.class, () -> new Word("-xxx^"));
        assertThrows(Word.InvalidLetterException.class, () -> new Word("!234'"));
        assertThrows(Word.InvalidLetterException.class, () -> new Word("     "));
        assertThrows(Word.InvalidLetterException.class, () -> new Word("\n\n\n\n\n"));
    }

    @Test
    void createTooFewWordLettersFail() {
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("v"));
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("va"));
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("val"));
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("vali"));
    }

    @Test
    void createTooManyWordLettersFail() {
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("validv"));
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("validva"));
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("validval"));
        assertThrows(Word.InvalidWordLengthException.class, () -> new Word("hustensaft"));
    }

    @Test
    void createWordIsCaseInsensitive() {
        assertEquals("valid", new Word("Valid").toString());
        assertEquals("valid", new Word("VALID").toString());
        assertEquals("xxxxx", new Word("XxXxX").toString());
    }

    @Test
    void createWordSameOrder() {
        assertEquals("alidv", new Word("alidv").toString());
        assertEquals("abcde", new Word("abcde").toString());
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
        assertEquals("abcde", new Word("abcde").toString());
        assertEquals("valid", new Word("VALID").toString());
        assertEquals("xxxxx", new Word("xxxxx").toString());
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
                'a', 'b', 'c', 'd', 'v').inOrder();
        assertThat(new Word("PQIWU").letters()).containsExactly(
                'p', 'q', 'i', 'w', 'u').inOrder();
        assertThat(new Word("xYZui").lettersAsArray()).asList().containsExactly(
                'x', 'y', 'z', 'u', 'i').inOrder();
        assertThat(new Word("abcde").lettersAsArray()).asList().containsExactly(
                'a', 'b', 'c', 'd', 'e').inOrder();
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

    @Test
    void letterIsValid() {
        assertTrue(Word.isValidLetter('a'));
        assertTrue(Word.isValidLetter('z'));
        assertTrue(Word.isValidLetter('A'));
        assertTrue(Word.isValidLetter('b'));
        assertTrue(Word.isValidLetter('x'));
        assertFalse(Word.isValidLetter(' '));
        assertFalse(Word.isValidLetter('1'));
        assertFalse(Word.isValidLetter('%'));
        assertFalse(Word.isValidLetter('\0'));
        assertFalse(Word.isValidLetter('_'));
    }


    @Test
    void contains() {
        Word word = new Word("abcde");
        assertTrue(word.contains('a'));
        assertTrue(word.contains('b'));
        assertTrue(word.contains('c'));
        assertTrue(word.contains('d'));
        assertTrue(word.contains('e'));
        assertTrue(word.contains('A'));
        assertTrue(word.contains('E'));
        assertTrue(word.contains("a"));
        assertTrue(word.contains("b"));
        assertTrue(word.contains("c"));
        assertTrue(word.contains("d"));
        assertTrue(word.contains("e"));
        assertTrue(word.contains("A"));
        assertTrue(word.contains("E"));
        assertTrue(word.contains("D"));
        assertTrue(word.contains("AB"));

        assertFalse(word.contains(" "));
        assertFalse(word.contains(' '));
        assertFalse(word.contains('z'));
        assertFalse(word.contains('%'));
        assertFalse(word.contains('y'));
        assertFalse(word.contains("f"));
        assertFalse(word.contains("g"));
        assertFalse(word.contains("H"));
    }
}
