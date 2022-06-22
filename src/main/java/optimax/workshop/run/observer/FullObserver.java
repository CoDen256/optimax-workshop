package optimax.workshop.run.observer;

import optimax.workshop.score.AggregatedSnapshot;

public interface FullObserver {
    void onRunLaunched(int totalRuns);
    void onRunFinished(AggregatedSnapshot aggregatedSnapshot);

}
