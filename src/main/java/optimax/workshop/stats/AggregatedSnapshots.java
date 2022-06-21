package optimax.workshop.stats;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AggregatedSnapshots {

    private final List<GameSnapshot> stats;

    public AggregatedSnapshots(List<GameSnapshot> stats) {
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

    public List<GameSnapshot> getStats() {
        return Collections.unmodifiableList(stats);
    }
}
