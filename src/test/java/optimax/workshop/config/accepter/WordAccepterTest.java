package optimax.workshop.config.accepter;

import static optimax.workshop.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import optimax.workshop.core.Word;
import optimax.workshop.runner.WordAccepter;
import org.junit.jupiter.api.Test;


/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class WordAccepterTest {

    @Test
    void accepterAcceptsOnlyWordsMatchingPredicate() {
        WordAccepter accepter = s -> s.toString().charAt(0) == 'v';

        assertFalse(accepter.isAccepted(word("xxxxx")));
        assertFalse(accepter.isAccepted(word("abcde")));
        assertFalse(accepter.isAccepted(word("fghjk")));
        assertFalse(accepter.isAccepted(word("YYYYY")));
        assertFalse(accepter.isAccepted(word("ZZZZZ")));
        assertTrue(accepter.isAccepted(word("Vbcde")));
        assertTrue(accepter.isAccepted(word("vbcde")));
        assertTrue(accepter.isAccepted(word("vaaaa")));
        assertTrue(accepter.isAccepted(word("valis")));
        assertTrue(accepter.isAccepted(word("VVVVV")));
    }


    @Test
    void oneWordAccepterAcceptsNoWordExceptForSolution() {
        Word solution = word("valid");
        WordAccepter accepter = solution::equals;

         assertFalse(accepter.isAccepted(word("xxxxx")));
         assertFalse(accepter.isAccepted(word("abcde")));
         assertFalse(accepter.isAccepted(word("fghjk")));
         assertFalse(accepter.isAccepted(word("YYYYY")));
         assertFalse(accepter.isAccepted(word("ZZZZZ")));
         assertTrue(accepter.isAccepted(solution));
    }

    @Test
    void falseAccepterAcceptsNoWords() {
        WordAccepter accepter = s -> false;

        assertFalse(accepter.isAccepted(word("xxxxx")));
        assertFalse(accepter.isAccepted(word("abcde")));
        assertFalse(accepter.isAccepted(word("fghjk")));
        assertFalse(accepter.isAccepted(word("YYYYY")));
        assertFalse(accepter.isAccepted(word("ZZZZZ")));
    }

    @Test
    void trueAccepterAcceptsAnyWord() {
        WordAccepter accepter = s -> true;
        assertTrue(accepter.isAccepted(word("xxxxx")));
        assertTrue(accepter.isAccepted(word("abcde")));
        assertTrue(accepter.isAccepted(word("fghjk")));
        assertTrue(accepter.isAccepted(word("YYYYY")));
        assertTrue(accepter.isAccepted(word("ZZZZZ")));
    }
}
