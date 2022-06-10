package optimax.game;

import static optimax.game.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import optimax.game.accepter.WordAccepter;
import org.junit.jupiter.api.Test;


/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordAccepterTest {

    @Test
    void givenAccepter_GameAcceptsOnlyWordsFromAccepter() {
        WordAccepter accepter = s -> s.word().charAt(0) == 'v';

        assertFalse(accepter.accept(word("xxxxx")));
        assertFalse(accepter.accept(word("abcde")));
        assertFalse(accepter.accept(word("fghjk")));
        assertFalse(accepter.accept(word("YYYYY")));
        assertFalse(accepter.accept(word("ZZZZZ")));
        assertTrue(accepter.accept(word("Vbcde")));
        assertTrue(accepter.accept(word("vbcde")));
        assertTrue(accepter.accept(word("vaaaa")));
        assertTrue(accepter.accept(word("valis")));
        assertTrue(accepter.accept(word("VVVVV")));
    }


    @Test
    void givenOneWordAccepter_AcceptsNoWordExceptForSolution() {
        Word solution = word("valid");
        WordAccepter accepter = solution::equals;

         assertFalse(accepter.accept(word("xxxxx")));
         assertFalse(accepter.accept(word("abcde")));
         assertFalse(accepter.accept(word("fghjk")));
         assertFalse(accepter.accept(word("YYYYY")));
         assertFalse(accepter.accept(word("ZZZZZ")));
         assertTrue(accepter.accept(solution));
    }

    @Test
    void givenFalseAccepter_AcceptsNoWordExceptForSolution() {
        WordAccepter accepter = s -> false;

        assertFalse(accepter.accept(word("xxxxx")));
        assertFalse(accepter.accept(word("abcde")));
        assertFalse(accepter.accept(word("fghjk")));
        assertFalse(accepter.accept(word("YYYYY")));
        assertFalse(accepter.accept(word("ZZZZZ")));
    }

    @Test
    void givenTrueAccepter_AcceptsAnyWord() {
        WordAccepter accepter = s -> true;
        assertTrue(accepter.accept(word("xxxxx")));
        assertTrue(accepter.accept(word("abcde")));
        assertTrue(accepter.accept(word("fghjk")));
        assertTrue(accepter.accept(word("YYYYY")));
        assertTrue(accepter.accept(word("ZZZZZ")));
    }
}
