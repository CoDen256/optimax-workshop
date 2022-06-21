package optimax.workshop.config.observer;

import static optimax.workshop.config.observer.ConsoleUtils.println;

import optimax.workshop.runner.FullObserver;
import optimax.workshop.stats.AggregatedSnapshots;

public class Greeter implements FullObserver {
    @Override
    public void onRunLaunched(int totalRuns) {
        println("Wordle Guesser Tester started: %d", totalRuns);
    }

    @Override
    public void onRunFinished(AggregatedSnapshots aggregatedSnapshots) {}
}
