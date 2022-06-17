package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.formatResult;
import static optimax.workshop.config.observer.ConsoleUtils.formatWord;
import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;
import static optimax.workshop.config.observer.ConsoleUtils.repeated;

import java.util.ArrayList;
import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsolePrettyPrinter implements GameObserver {

    private int guessCount = 0;

    private final Collection<MatchResult> results = new ArrayList<>();
    private Word solution;

    @Override
    public void onCreated(Word solution, Guesser guesser, WordAccepter accepter) {
        guessCount = 0;
        results.clear();
        println(repeated("-", 30, "\n{w", "}"));
        println("{w" + "Worlde game has started!}".toUpperCase());
        println("Guesser: {g`%s`}", guesser.getClass().getSimpleName());
        println("Accepting by: {g`%s`}\n", accepter.getClass().getSimpleName());
        this.solution = solution;
    }

    @Override
    public void onGuessExpected() {
        println("\nExpecting {gGuess #%d}...", ++guessCount);
    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {
        println("Guess {gsubmitted}:");
        printResult(result);
        results.add(result);
    }

    private void printResult(MatchResult result) {
        println(formatResult(ConsoleUtils::getMatchColor, "{b %c ", result));
    }


    @Override
    public void onGuessRejected(Word guess) {
        print("{rGuess #%d rejected}:\n", guessCount--);
        printWord(guess, "{R{b", "}");
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
        printWord(solution, "{G{b", "}");
    }

    private void printWord(Word word, String prefix, String suffix) {
        println(formatWord(" %c ", word, prefix, suffix));
    }
}
