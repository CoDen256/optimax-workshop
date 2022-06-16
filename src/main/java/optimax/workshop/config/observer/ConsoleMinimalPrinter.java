package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.formatResult;
import static optimax.workshop.config.observer.ConsoleUtils.formatWord;
import static optimax.workshop.config.observer.ConsoleUtils.getMatchColor;
import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;

import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsoleMinimalPrinter implements GameObserver {

    private MatchResult result;
    private Word solution;
    private int count = 0;

    @Override
    public void onCreated(WordleGame game, Word solution, Guesser guesser, WordAccepter accepter) {
        solution = game.getSolution();
        count++;
    }

    @Override
    public void onFinished(boolean solved) {
        print("{wGame #%d} ", count);
        println(solved ?  "{gSolved!}" : "{rFailed!}");
//        print(" ");
//        print(formatResult( t -> getMatchColor(t).toLowerCase(), "%c", result));
//        print(":");
//        println(formatWord("%c", solution, "{w", "}"));
    }

    @Override
    public void onGuessExpected() {

    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {
        this.result = result;
    }

    @Override
    public void onGuessRejected(Word guess) {

    }
}
