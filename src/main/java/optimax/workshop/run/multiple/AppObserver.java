package optimax.workshop.run.multiple;

import optimax.workshop.run.full.GameRecorder;

/**
 * Similar to {@link AppLifecycleObserver}, but contains additional
 * information about the state of all games recorded by {@link GameRecorder}
 * and provided in {@link AggregatedSnapshot}
 */
public interface AppObserver {
    /** @see AppLifecycleObserver#onLaunched  */
    void onLaunched(int totalRuns);

    /** @see AppLifecycleObserver#onFinished()   */
    void onFinished(AggregatedSnapshot aggregatedSnapshot);
}
