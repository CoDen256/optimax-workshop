package optimax.workshop.run.multiple;

import optimax.workshop.run.WordleRunner;
import optimax.workshop.run.single.SingleWordleRunner;

/**
 * The {@code AppLifecycleObserver} represents an observer, that
 * observes {@link RepeatedWordleRunner}
 */
public interface AppLifecycleObserver {
    /**
     * Called when the runner was launched, providing the amount of single
     * {@link WordleRunner} that will run
     *
     * @param totalRuns
     *         the amount of single runs
     */
    void onLaunched(int totalRuns);

    /**
     * Called when the runner has finished, and no more single runs
     * will be launched
     */
    void onFinished();
}
