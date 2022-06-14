package optimax.workshop.config.runner;

import java.util.function.IntPredicate;
import optimax.workshop.runner.GameRunner;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RepeatedRunner implements GameRunner {
    private final GameRunner runner;
    private final IntPredicate runNext;

    public RepeatedRunner(IntPredicate runCondition, GameRunner runner) {
        this.runner = runner;
        this.runNext = runCondition;
    }

    @Override
    public void run() {
        int i = 0;
        while(runNext.test(i)){
            runner.run();
            i++;
        }
    }
}
