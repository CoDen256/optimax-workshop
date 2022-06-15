package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;
import static optimax.workshop.config.observer.ConsoleUtils.repeated;

import java.util.ArrayList;
import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsolePrettyPrinter implements GameObserver {

    private int guessCount = 0;

    private Collection<MatchResult> results = new ArrayList<>();
    private WordleGame game;

    @Override
    public void onCreated(WordleGame game, Guesser guesser, WordAccepter accepter) {
        guessCount = 0;
        results.clear();
        println(repeated("-", 30, "\n{w", "}"));
        println("{w"+"Worlde game has started!}".toUpperCase());
        println("Guesser: {g`%s`}", guesser.getClass().getSimpleName());
        println("Accepting by: {g`%s`}\n", accepter.getClass().getSimpleName());
        this.game = game;
    }

    @Override
    public void onGuessExpected() {
        println("\nExpecting {gguess #%d}...", ++guessCount);
    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {
        println("Guess {gsubmitted}:");
        printResult(result);
        results.add(result);
    }

    private void printResult(MatchResult result) {
        for (Match match : result.getMatches()) {
            print(getMatchColor(match.getType()));
            print("{b %c ", Character.toUpperCase(match.getLetter()));
            print("}");
        }
        println();
    }

    @Override
    public void onGuessRejected(Word guess) {
        print("{rGuess #%d rejected}:\n", guessCount--);
        printWord(guess, "{R{b", "}");
    }

    private void printWord(Word guess, String prefix, String suffix) {
        print(prefix);
        for (char c : guess.word().toUpperCase().toCharArray()) {
            print(" %c ", c);
        }
        println(suffix);
    }

    @Override
    public void onFinished(boolean solved) {
        if (solved) {
            println("{gSolved!}");
        } else {
            println("{rFailed!}");
        }
        results.forEach(this::printResult);
        println("{gSolution:");
        printWord(game.getSolution(), "{G{b", "}");

    }

    private String getMatchColor(MatchType type) {
        switch(type){
            case CORRECT: return "{G";
            case WRONG: return "{Y";
            default:return "{W";
        }
    }


}
