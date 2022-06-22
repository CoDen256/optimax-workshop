package optimax.workshop.run.multiple;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import optimax.workshop.run.single.GameSnapshot;

/**
 * A {@code AggregatedSnapshot} represents a set of recorded {@link GameSnapshot}s corresponding
 * to the set of different <b>multiple</b> games being run.
 * <p>
 * It contains utility method to compute the needed statistics and metrics
 * based on the given set of {@link GameSnapshot}s.
 */
public class AggregatedSnapshot {
    /** All the recorded snapshots of multiple games */
    private final List<GameSnapshot> stats;

    public AggregatedSnapshot(List<GameSnapshot> snapshots) {
        this.stats = snapshots;
    }

    public double getWinRate() {
        double won = stats.stream().filter(GameSnapshot::isSolved).count();
        return won / getGamesCount() * 100;
    }

    public double getAverageGuessesPerGame() {
        return stats.stream().map(GameSnapshot::getGuessesCount)
                .collect(Collectors.averagingDouble(Integer::doubleValue));
    }

    public int getMinGuessesPerGame() {
        return stats.stream().map(GameSnapshot::getGuessesCount)
                .min(Integer::compare)
                .orElse(0);
    }

    public int getGamesCount() {
        return stats.size();
    }

    public long getTotalMillis() {
        return stats.stream().mapToLong(GameSnapshot::getDurationMillis).sum();
    }

    public double getAvgMillis() {
        return stats.stream().mapToLong(GameSnapshot::getDurationMillis).average().orElse(0);
    }

    public List<GameSnapshot> getSnapshots() {
        return Collections.unmodifiableList(stats);
    }
}
