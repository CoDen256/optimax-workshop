package optimax.workshop.runner;

import optimax.workshop.stats.AggregatedSnapshots;

public interface FullObserver {
    void onRunLaunched(int totalRuns);
    void onRunFinished(AggregatedSnapshots aggregatedSnapshots);

}
