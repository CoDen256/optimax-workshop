package optimax.workshop.config.observer;

import java.util.Arrays;
import java.util.List;
import optimax.workshop.runner.FullObserver;
import optimax.workshop.stats.AggregatedSnapshots;

public class AggregatedFullObserver implements FullObserver {

    private final List<FullObserver> fullObserverList;

    public AggregatedFullObserver(FullObserver... fullObserverList) {
        this.fullObserverList = Arrays.asList(fullObserverList);
    }

    @Override
    public void onRunLaunched(int totalRuns) {
        fullObserverList.forEach(o -> o.onRunLaunched(totalRuns));
    }

    @Override
    public void onRunFinished(AggregatedSnapshots aggregatedSnapshots) {
        fullObserverList.forEach(o -> o.onRunFinished(aggregatedSnapshots));
    }
}
