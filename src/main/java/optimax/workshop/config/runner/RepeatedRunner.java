package optimax.workshop.config.runner;

import java.util.function.Supplier;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.GameRunner;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RepeatedRunner implements GameRunner {
    private final Supplier<GameRunner> runner;
    private final GameObserver observer;
    private final int runCount;

    public RepeatedRunner(int runCount,
                          GameObserver observer,
                          Supplier<GameRunner> runner

    ) {
        this.runner = runner;
        this.runCount = runCount;
        this.observer = observer;
    }

    @Override
    public void run() {
        observer.onRunLaunched(runCount);
        for (int i = 0; i < runCount; i++) {
            runner.get().run();
        }
        observer.onRunFinished();
    }
}
