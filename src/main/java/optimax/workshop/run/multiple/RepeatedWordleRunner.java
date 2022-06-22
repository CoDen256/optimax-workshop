package optimax.workshop.run.multiple;

import java.util.function.Supplier;
import optimax.workshop.run.WordleRunner;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class RepeatedWordleRunner implements WordleRunner {
    private final Supplier<WordleRunner> runner;
    private final AppLifecycleObserver observer;
    private final int limit;

    public RepeatedWordleRunner(int limit,
                                AppLifecycleObserver observer,
                                Supplier<WordleRunner> runner

    ) {
        this.runner = runner;
        this.limit = limit;
        this.observer = observer;
    }

    @Override
    public void run() {
        observer.onLaunched(limit);
        for (int i = 0; i < limit; i++) {
            runner.get().run();
        }
        observer.onFinished();
    }
}
