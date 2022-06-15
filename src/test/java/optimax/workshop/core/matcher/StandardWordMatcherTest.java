package optimax.workshop.core.matcher;

import static optimax.workshop.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertEquals;

import optimax.workshop.config.matcher.StandardMatcher;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class StandardWordMatcherTest {
    private final StandardMatcher sut = new StandardMatcher();

    @Test
    void allLettersCorrect() {
        assertMatches("+++++", "abcde", "abcde");
    }

    @Test
    void oneLetterAbsent() {
        assertMatches("x++++", "abcde", "xbcde");
        assertMatches("+x+++", "abcde", "axcde");
        assertMatches("++x++", "abcde", "abxde");
        assertMatches("+++x+", "abcde", "abcxe");
        assertMatches("++++x", "abcde", "abcdx");
    }

    @Test
    void twoLettersAbsent() {
        assertMatches("xx+++", "abcde", "xxcde");
        assertMatches("+xx++", "abcde", "axxde");
        assertMatches("++xx+", "abcde", "abxxe");
        assertMatches("+++xx", "abcde", "abcxx");
        assertMatches("x+++x", "abcde", "xbcdx");
        assertMatches("+x+x+", "abcde", "axcxe");
    }

    @Test
    void allLettersAbsent() {
        assertMatches("xxxxx", "abcde", "xxxxx");
    }

    @Test
    void oneLetterWrongAllAbsent() {
        assertMatches("-xxxx", "abcde", "exxxx");
        assertMatches("x-xxx", "abcde", "xexxx");
        assertMatches("xx-xx", "abcde", "xxexx");
        assertMatches("xxx-x", "abcde", "xxxex");
        assertMatches("xxxx-", "abcde", "xxxxa");
    }

    @Test
    void oneLetterCorrectOtherLettersTheSameAndAbsent() {
        assertMatches("+xxxx", "abcde", "aaaaa");
        assertMatches("x+xxx", "abcde", "bbbbb");
        assertMatches("xx+xx", "abcde", "ccccc");
        assertMatches("xxx+x", "abcde", "ddddd");
        assertMatches("xxxx+", "abcde", "eeeee");
    }

    @Test
    void twoLetterCorrectOtherLettersTheSameAndAbsent() {
        assertMatches("++xxx", "aacde", "aaaaa");
        assertMatches("++xxx", "bbcde", "bbbbb");
        assertMatches("+x+xx", "cbcde", "ccccc");
        assertMatches("+xx+x", "dbcde", "ddddd");
        assertMatches("+xxx+", "ebcde", "eeeee");
    }

    @Test
    void oneLetterWrongOneLetterAbsent() {
        assertMatches("-+++x", "abcde", "ebcdx");
        assertMatches("+-++x", "abcde", "aecdx");
        assertMatches("++-+x", "abcde", "abedx");
        assertMatches("+++-x", "abcde", "abcex");
        assertMatches("x+++-", "abcde", "xbcda");
    }

    @Test
    void matchingFirstLetter() {
        assertMatches("+xxxx", "ayyyy", "axxxx");
        assertMatches("x-xxx", "ayyyy", "xaxxx");
        assertMatches("+xxxx", "ayyyy", "aaxxx");
        assertMatches("x-xxx", "ayyyy", "xaaxx");
        assertMatches("+xxxx", "ayyyy", "aaaxx");
        assertMatches("x-xxx", "ayyyy", "xaaax");
        assertMatches("+xxxx", "ayyyy", "aaaax");
        assertMatches("x-xxx", "ayyyy", "xaaaa");
        assertMatches("+xxxx", "ayyyy", "aaaaa");
    }

    @Test
    void matchingTwoFirstLetters() {
        assertMatches("+xxxx", "aayyy", "axxxx");
        assertMatches("++xxx", "aayyy", "aaxxx");
        assertMatches("x+-xx", "aayyy", "xaaxx");
        assertMatches("xx--x", "aayyy", "xxaax");
        assertMatches("++xxx", "aayyy", "aaaxx");
        assertMatches("x+-xx", "aayyy", "xaaax");
        assertMatches("xx--x", "aayyy", "xxaaa");
        assertMatches("++xxx", "aayyy", "aaaax");
        assertMatches("x+-xx", "aayyy", "xaaaa");
        assertMatches("++xxx", "aayyy", "aaaaa");
    }

    @Test
    void threeLettersInSolution() {
        assertMatches("+xxxx", "aaayy", "axxxx");
        assertMatches("xxxx-", "aaayy", "xxxxa");
        assertMatches("++xxx", "aaayy", "aaxxx");
        assertMatches("x++xx", "aaayy", "xaaxx");
        assertMatches("xx+-x", "aaayy", "xxaax");
        assertMatches("xxx--", "aaayy", "xxxaa");
        assertMatches("+++xx", "aaayy", "aaaxx");
        assertMatches("x++-x", "aaayy", "xaaax");
        assertMatches("xx+--", "aaayy", "xxaaa");
        assertMatches("+++xx", "aaayy", "aaaax");
        assertMatches("x++-x", "aaayy", "xaaaa");
        assertMatches("+++xx", "aaayy", "aaaaa");
    }

    @Test
    void fourLettersInSolution() {
        assertMatches("+xxxx", "aaaay", "axxxx");
        assertMatches("xxxx-", "aaaay", "xxxxa");
        assertMatches("++xxx", "aaaay", "aaxxx");
        assertMatches("x++xx", "aaaay", "xaaxx");
        assertMatches("xx++x", "aaaay", "xxaax");
        assertMatches("xxx+-", "aaaay", "xxxaa");
        assertMatches("+++xx", "aaaay", "aaaxx");
        assertMatches("x+++x", "aaaay", "xaaax");
        assertMatches("xx++-", "aaaay", "xxaaa");
        assertMatches("++++x", "aaaay", "aaaax");
        assertMatches("x+++-", "aaaay", "xaaaa");
        assertMatches("++++x", "aaaay", "aaaaa");
    }

    @Test
    void fiveLettersInSolution() {
        assertMatches("+xxxx", "aaaaa", "axxxx");
        assertMatches("xxxx+", "aaaaa", "xxxxa");
        assertMatches("++xxx", "aaaaa", "aaxxx");
        assertMatches("x++xx", "aaaaa", "xaaxx");
        assertMatches("xx++x", "aaaaa", "xxaax");
        assertMatches("xxx++", "aaaaa", "xxxaa");
        assertMatches("+++xx", "aaaaa", "aaaxx");
        assertMatches("x+++x", "aaaaa", "xaaax");
        assertMatches("xx+++", "aaaaa", "xxaaa");
        assertMatches("++++x", "aaaaa", "aaaax");
        assertMatches("x++++", "aaaaa", "xaaaa");
        assertMatches("+++++", "aaaaa", "aaaaa");
    }

    @Test
    void allLettersOnTheWrongPlaces() {
        assertMatches("-----", "abcde", "eabcd");
        assertMatches("-----", "abcde", "eabcd");
        assertMatches("-----", "abcde", "deabc");
        assertMatches("-----", "abcde", "cdeab");
        assertMatches("-----", "abcde", "bcdea");
    }

    @Test
    void integration() {
        assertMatches("+--xx", "abcdd", "addxx");
        assertMatches("-+++x", "abcde", "ebcdx");
        assertMatches("x++++", "valid", "aalid");
        assertMatches("xxxxx", "valid", "zzzzz");
        assertMatches("x+xxx", "valid", "aaaaa");
        assertMatches("x+x-x", "valid", "kaAVv");
    }

    private void assertMatches(String expected, String solution, String actual) {
        MatchResult result = sut.match(word(solution), word(actual));
        assertEquals(String.format("[%s]", expected), result.toString());
    }
}
