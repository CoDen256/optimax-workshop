package optimax.workshop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import optimax.workshop.config.FileWordLoader;
import optimax.workshop.config.guesser.RegexBasedGuesser;
import optimax.workshop.config.matcher.StandardMatcher;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;
import org.junit.jupiter.api.Test;

class GuesserTest {
    @Test
    void test() {

        Collection<Word> words = List.of("BATCH",
                        "CATCH",
                        "LATCH",
                        "PATCH",
                        "MATCH")
                .stream().map(Word::new).collect(Collectors.toList());

        Collection<Word> accepted = FileWordLoader.load(GuesserTest.class.getResource("/words-mini.txt").getPath());


        StandardMatcher matcher = new StandardMatcher();
        RegexBasedGuesser guesser = new RegexBasedGuesser(matcher);

        guesser.init(words, accepted);
        guesser.match(new Word("DATCH"), new MatchResult(List.of(
                new Match(MatchType.ABSENT, 0, 'd'),
                new Match(MatchType.CORRECT, 1, 'a'),
                new Match(MatchType.CORRECT, 2, 't'),
                new Match(MatchType.CORRECT, 3, 'c'),
                new Match(MatchType.CORRECT, 4, 'h'))
        ));
        Word word = guesser.nextGuess();
        System.out.println(word);
    }
}
