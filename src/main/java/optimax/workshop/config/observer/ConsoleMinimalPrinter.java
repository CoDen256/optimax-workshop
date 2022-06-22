package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.formatResult;
import static optimax.workshop.config.observer.ConsoleUtils.formatWord;
import static optimax.workshop.config.observer.ConsoleUtils.getMatchColor;
import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;

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

    private final boolean printGuesser;
    private final boolean printGuessesNumber;
    private final boolean printSolution;
    private final boolean printGuesses;

    public ConsoleMinimalPrinter(boolean printSolution, boolean printGuessesNumber, boolean printGuesser, boolean printGuesses) {
        this.printGuesser = printGuesser;
        this.printGuessesNumber = printGuessesNumber;
        this.printSolution = printSolution;
        this.printGuesses = printGuesses;
    }

    @Override
    public void onGameCreated(GameSnapshot snapshot) {

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
        print("{wRun #%-5d}", game.getIndex());
        if (printGuesser) {
            print("{w [}{g%s}{w] }", game.getGuesser().name());
        }
        print(game.isSolved() ? "{gSolved!}" : "{rFailed!}");
        if (printGuessesNumber) {
            print("\t({g%d} {wguesses})", game.getGuessesCount());
        }
        if (printSolution) {
            print("\t" + formatWord("%c", game.getSolution(), "{g", "}"));
        }
        if (printGuesses) {
            print("\t");
            print(game.getMatches()
                    .stream()
                    .map(m -> formatResult(m, "%c", t -> getMatchColor(t).toLowerCase()))
                    .collect(Collectors.joining(", ", "[", "]")));
        }
        println();
    }
}
