package optimax.workshop.config.guesser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import optimax.workshop.core.match.Match;
import optimax.workshop.core.match.MatchType;
import optimax.workshop.core.match.StandardMatcher;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.guesser.Guesser;
import optimax.workshop.core.match.WordMatcher;

/**
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class CategoryDivisionGuesser implements Guesser {

    private final List<String> solutions = new ArrayList<>();
    private final List<String> acceptedWords = new ArrayList<>();
    private final List<String> guesses = new ArrayList<>();

    private final WordMatcher matcher = new StandardMatcher();

    private final RegexFilter filter = new RegexFilter();

    @Override
    public void init(Collection<Word> solutions, Collection<Word> accepted) {
        this.solutions.addAll(wordsToString(solutions));
        this.acceptedWords.addAll(wordsToString(accepted));
        filter.init(this.solutions);
    }

    private List<String> wordsToString(Collection<Word> solutions) {
        return solutions.stream().map(Word::toString).collect(Collectors.toList());
    }

    @Override
    public Word nextGuess() {
        List<String> matches = getAllMatches();

        if (containsOnlyTwoPossibleMatches(matches)) return new Word(matches.get(0));
        if (guesses.isEmpty()) return new Word("trace");

        List<GroupSet> groupSets = new ArrayList<>();
        for (String accepted : acceptedWords) {
            if (alreadyGuessed(accepted)) continue;
            GroupSet groupSet = new GroupSet(accepted);
            Map<MatchResult, List<String>> groups = new HashMap<>();
            for (String match : matches) {
                MatchResult result = matcher.match(new Word(match), new Word(accepted));
                groups.computeIfAbsent(result, r -> new ArrayList<>());
                groups.computeIfPresent(result, (r, w) -> {
                    w.add(match);
                    return w;
                });
            }
            groups.forEach((r, w) -> {
                groupSet.add(new Group(r, w));
            });
            groupSets.add(groupSet);
        }

        GroupSet bestGroupSet = computeBestGroupSet(groupSets);
        return new Word(bestGroupSet.word);
    }

    private boolean alreadyGuessed(String accepted) {
        return guesses.contains(accepted);
    }

    private boolean containsOnlyTwoPossibleMatches(List<String> matches) {
        return matches.size() <= 2;
    }

    private List<String> getAllMatches() {
        return filter.getMatches().collect(Collectors.toList());
    }

    private GroupSet computeBestGroupSet(List<GroupSet> groupSets) {
        return groupSets.stream().max(Comparator.comparing(GroupSet::size)).get();
    }

    @Override
    public void match(Word guess, MatchResult result) {
        guesses.add(guess.toString());
        filter.updateRegex(result);
    }

    private static class GroupSet {
        private final String word;
        private final List<Group> groups = new ArrayList<>();

        public GroupSet(String word) {
            this.word = word;
        }

        public void add(Group group){
            groups.add(group);
        }

        public String getWord() {
            return word;
        }

        public List<Group> getGroups() {
            return groups;
        }

        public int size(){
            return groups.size();
        }

        @Override
        public String toString() {
            return word+" "+groups.size();
        }
    }
    private static class Group{
        private final List<String> words = new ArrayList<>();
        private final MatchResult match;


        public Group(MatchResult match) {
            this.match = match;
        }
        public Group(MatchResult match, List<String> words) {
            this.match = match;
            this.words.addAll(words);
        }


        public void add(String word){
            words.add(word);
        }

        public List<String> getWords() {
            return Collections.unmodifiableList(words);
        }

        public MatchResult getMatch() {
            return match;
        }

        public int size(){
            return words.size();
        }

        @Override
        public String toString() {
            return match.toString()+" "+words.size();
        }
    }
}
