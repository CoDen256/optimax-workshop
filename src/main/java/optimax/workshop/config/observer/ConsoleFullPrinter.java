package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.formatResult;
import static optimax.workshop.config.observer.ConsoleUtils.formatWord;
import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;
import static optimax.workshop.config.observer.ConsoleUtils.repeated;

import java.util.Arrays;
import java.util.EnumSet;
import optimax.workshop.core.Word;
import optimax.workshop.core.match.MatchResult;
import optimax.workshop.run.single.GameObserver;
import optimax.workshop.run.single.GameSnapshot;
import optimax.workshop.run.single.SingleWordleRunner;

/**
 * Prints out the full lifecycle of the {@link SingleWordleRunner} and
 * corresponding state of a single game
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsoleFullPrinter implements GameObserver {

    private final EnumSet<Config> config;

    public enum Config {
        PRINT_SOLUTION // print solution at the start of the game
    }

    public ConsoleFullPrinter(Config... config) {
        this.config = EnumSet.noneOf(Config.class);
        this.config.addAll(Arrays.asList(config));
    }

    @Override
    public void onGameCreated(GameSnapshot game) {
        print(repeated("-", 15));
        print("{w" + "Worlde Game #%d}",  game.getIndex());
        print(repeated("-", 15));
        println();
        println("Guesser: {g`%s`}", game.getGuesser().name());
        println("Accepting by: {y`%s`}", game.getAccepter().name());
        if (config.contains(Config.PRINT_SOLUTION)){
            println("{ySOLUTION: {g%s}", game.getSolution().toString().toUpperCase());
        }
    }

    @Override
    public void onGuessExpected(GameSnapshot game) {
        println("\nExpecting {gGuess #%d}...", game.getGuessesCount()+1);
    }

    @Override
    public void onGuessSubmitted(GameSnapshot snapshot) {
        println("Guess {gsubmitted}:");
        printResult(snapshot.getLastMatch());
    }

    private void printResult(MatchResult result) {
        println(formatResult(result, "{b %c ", ConsoleUtils::getMatchColor));
    }

    @Override
    public void onGuessRejected(Word guess, GameSnapshot snapshot) {
        print("{rGuess #%d rejected}:\n", snapshot.getGuessesCount()+1);
        printWord(guess, "{R{b", "}");
    }


    @Override
    public void onGameFinished(GameSnapshot game) {
        if (game.isSolved()) {
            println("{gSolved!}");
        } else {
            println("{rFailed!}");
        }
        game.getMatches().forEach(this::printResult);
        println("{gSolution:");
        printWord(game.getSolution(), "{G{b", "}");
    }

    private void printWord(Word word, String prefix, String suffix) {
        println(formatWord(" %c ", word, prefix, suffix));
    }
}
