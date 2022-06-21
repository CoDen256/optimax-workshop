package optimax.workshop.runner;

import optimax.workshop.stats.AggregatedSnapshot;

public interface FullObserver {
    void onRunLaunched(int totalRuns);
    void onRunFinished(AggregatedSnapshot aggregatedSnapshot);

}
