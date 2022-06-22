package optimax.workshop.run;

import java.util.function.Supplier;
import optimax.workshop.run.observer.GameObserver;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RepeatedWordleRunner implements WordleRunner {
    private final Supplier<WordleRunner> runner;
    private final GameObserver observer;
    private final int limit;

    public RepeatedWordleRunner(int limit,
                                GameObserver observer,
                                Supplier<WordleRunner> runner

    ) {
        this.runner = runner;
        this.limit = limit;
        this.observer = observer;
    }

    @Override
    public void run() {
        observer.onRunLaunched(limit);
        for (int i = 0; i < limit; i++) {
            runner.get().run();
        }
        observer.onRunFinished();
    }
}
