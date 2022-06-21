package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.formatResult;
import static optimax.workshop.config.observer.ConsoleUtils.formatWord;
import static optimax.workshop.config.observer.ConsoleUtils.getMatchColor;
import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;

import java.util.stream.Collectors;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.stats.GameSnapshot;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsoleMinimalPrinter extends EmptyGameStateObserver {

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
