package optimax.workshop.config.guesser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import optimax.workshop.runner.Guesser;

public class RotatingGuesser implements Supplier<Guesser> {

    private final List<Supplier<Guesser>> guessers;
    private final int totalRuns;
    private int currentRun = 0;

    public RotatingGuesser(int totalRuns, List<Supplier<Guesser>> guessers) {
        this.guessers = new ArrayList<>(guessers);
        this.totalRuns = totalRuns;
    }

    @Override
    public Guesser get() {
        return guessers.get(
                (currentRun++ % totalRuns) * guessers.size() / totalRuns
        ).get();
    }
}
