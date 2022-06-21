package optimax.workshop.stats;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AggregatedSnapshot {

    private final List<GameSnapshot> stats;

    public AggregatedSnapshot(List<GameSnapshot> stats) {
        this.stats = stats;
    }

    public double getWinRate(){
        double won = stats.stream().filter(GameSnapshot::isSolved).count();
        return won / getGamesCount() * 100;
    }

    public double getAverageGuessesPerGame(){
        return stats.stream().map(GameSnapshot::getGuessesCount)
                .collect(Collectors.averagingDouble(Integer::doubleValue));
    }

    public int getMinGuessesPerGame(){
        return stats.stream().map(GameSnapshot::getGuessesCount)
                .min(Integer::compare)
                .orElse(0);
    }

    public int getGamesCount(){
        return stats.size();
    }

    public long getTotalMillis(){
        return stats.stream().mapToLong(GameSnapshot::getMillis).sum();
    }

    public double getAvgMillis(){
        return stats.stream().mapToLong(GameSnapshot::getMillis).average().orElse(0);
    }

    public List<GameSnapshot> getStats() {
        return Collections.unmodifiableList(stats);
    }
}
