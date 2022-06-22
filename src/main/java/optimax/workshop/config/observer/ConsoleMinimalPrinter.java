package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.formatResult;
import static optimax.workshop.config.observer.ConsoleUtils.formatWord;
import static optimax.workshop.config.observer.ConsoleUtils.getMatchColor;
import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;
import optimax.workshop.core.Word;
import optimax.workshop.run.single.GameObserver;
import optimax.workshop.run.single.GameSnapshot;
import optimax.workshop.run.single.SingleWordleRunner;

/**
 * Prints out minimally the lifecycle of the {@link SingleWordleRunner} and
 * corresponding state of a single game
 *
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsoleMinimalPrinter implements GameObserver {

    private final EnumSet<Config> config;

    public enum Config {
        PRINT_GUESSER, // print the guesser that was used
        PRINT_SOLUTION,  // print the solution
        PRINT_GUESSES, // print all the submitted guesses
        PRINT_GUESSES_NUM // print the number of guesses
    }

    public ConsoleMinimalPrinter(Config... config) {
        this.config = EnumSet.noneOf(Config.class);
        this.config.addAll(Arrays.asList(config));
    }

    @Override
    public void onGameCreated(GameSnapshot snapshot) {
        print("{wRun #%-5d}", snapshot.getIndex());
        if (isGuesserPrinted()) {
            print("{w [}{g%s}{w] }", snapshot.getGuesser().name());
        }
    }

    @Override
    public void onGuessExpected(GameSnapshot snapshot) {

    }

    @Override
    public void onGuessSubmitted(GameSnapshot snapshot) {

    }

    @Override
    public void onGuessRejected(Word guess, GameSnapshot snapshot) {

    }

    @Override
    public void onGameFinished(GameSnapshot game) {
        print(game.isSolved() ? "{gSolved!}" : "{rFailed!}");
        if (isGuessesNumPrinted()) {
            print("\t({g%d} {wguesses})", game.getGuessesCount());
        }
        if (isSolutionPrinted()) {
            print("\t" + formatWord("%c", game.getSolution(), "{g", "}"));
        }
        if (isGuessesListPrinted()) {
            print("\t");
            print(game.getMatches()
                    .stream()
                    .map(m -> formatResult(m, "%c", t -> getMatchColor(t).toLowerCase()))
                    .collect(Collectors.joining(", ", "[", "]")));
        }
        println();
    }

    private boolean isGuesserPrinted() {
        return config.contains(Config.PRINT_GUESSER);
    }

    private boolean isGuessesListPrinted() {
        return config.contains(Config.PRINT_GUESSES);
    }

    private boolean isGuessesNumPrinted() {
        return config.contains(Config.PRINT_GUESSES_NUM);
    }

    private boolean isSolutionPrinted() {
        return config.contains(Config.PRINT_SOLUTION);
    }
}
