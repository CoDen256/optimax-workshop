package optimax.workshop.config.observer;

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
public class ScoringObserver implements GameObserver {

    private int lost = 0;
    private int won = 0;
    private int count = 0;

    public ScoringObserver() {
    }

    @Override
    public void onCreated(WordleGame game, Guesser guesser, WordAccepter accepter) {
        count++;
//        System.out.printf("[Scorer] Game %d started", count);
    }

    @Override
    public void onFinished(boolean solved) {
//        System.out.printf("[Scorer] Game %d %s%n", count, solved ? "solved" : "lost");
        if (solved){
            won ++;
        }else {
            lost ++;
        }
        System.out.printf("[Scorer] Games: %d, Solved/Lost: %d/%d, Rate: %f%%%n", count, won, lost, count == 0? 0: (float)won/count*100);
    }

    @Override
    public void onGuessExpected() {

    }

    @Override
    public void onGuessSubmitted(Word guess, MatchResult result) {

    }

    @Override
    public void onGuessRejected(Word guess) {

    }
}
