package optimax.workshop.config.runner;

import static java.lang.String.format;

import java.util.Collection;
import optimax.workshop.core.Word;
import optimax.workshop.core.WordleGame;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.WordMatcher;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;
import optimax.workshop.runner.Guesser;
import optimax.workshop.runner.SolutionGenerator;
import optimax.workshop.runner.WordAccepter;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleRunner implements GameRunner {

    private final int maxAttempts;
    private final Collection<Word> visibleSolutions;
    private final Collection<Word> visibleAccepted;
    private final WordAccepter accepter;
    private final SolutionGenerator generator;
    private final Guesser guesser;
    private final GameObserver observer;
    private final WordMatcher matcher;

    public WordleRunner(int maxAttempts,
                        Collection<Word> visibleSolutions,
                        Collection<Word> visibleAccepted,
                        SolutionGenerator generator,
                        WordAccepter accepter,
                        Guesser guesser,
                        GameObserver observer,
                        WordMatcher matcher) {
        this.maxAttempts = maxAttempts;
        this.generator = generator;
        this.visibleSolutions = visibleSolutions;
        this.visibleAccepted = visibleAccepted;
        this.matcher = matcher;
        this.observer = observer;
        this.guesser = guesser;
        this.accepter = accepter;
    }

    public void run(){
        Word solution = generator.nextSolution();
        WordleGame game = new WordleGame(maxAttempts, solution, matcher);
        verifySolution(solution);
        guesser.init(visibleSolutions, visibleAccepted);
        observer.onCreated(game, solution,  guesser, accepter);
        while(!game.isFinished()){
            nextGuess(game);
        }
        observer.onFinished(game.isSolved());
    }


    private void nextGuess(WordleGame game) {
        observer.onGuessExpected();
        Word guess = guesser.nextGuess();
        if (accepter.isNotAccepted(guess)){
            observer.onGuessRejected(guess);
            return;
        }
        MatchResult result = game.submit(guess);
        observer.onGuessSubmitted(guess, result);
        guesser.match(guess, result);
    }

    private void verifySolution(Word solution){
        if (accepter.isNotAccepted(solution))
            throw new IllegalArgumentException(format("Solution (%s) is not accepted", solution));
    }
}
