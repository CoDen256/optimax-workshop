package optimax.workshop.config.observer;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.groupingBy;
import static optimax.workshop.config.observer.ConsoleUtils.print;
import static optimax.workshop.config.observer.ConsoleUtils.println;
import static optimax.workshop.config.observer.ConsoleUtils.repeated;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import optimax.workshop.run.observer.FullObserver;
import optimax.workshop.score.AggregatedSnapshot;
import optimax.workshop.score.GameSnapshot;

public class ScorePrettyPrinter implements FullObserver {
    public static final int FIRST_COLUMN_WIDTH = 32;
    public static final int SECOND_COLUMN_WIDTH = 12;
    public static final String BORDER = formatBorder(FIRST_COLUMN_WIDTH, SECOND_COLUMN_WIDTH);
    public static final String TITLE_FORMAT = "| {g%-43s} |";

    public static final Comparator<Map.Entry<String, AggregatedSnapshot>> WIN_RATE_COMPARATOR = comparingDouble(
            (Map.Entry<String, AggregatedSnapshot> entry) -> entry.getValue().getWinRate()
    ).reversed();

    public static final Comparator<Map.Entry<String, AggregatedSnapshot>> AVG_GUESSES_COMPARATOR = comparingDouble(
            entry -> entry.getValue().getAverageGuessesPerGame()
    );

    @Override
    public void onRunLaunched(int totalRuns) {}

    @Override
    public void onRunFinished(AggregatedSnapshot aggregatedSnapshot) {
        printScores(aggregatedSnapshot);
    }

    public void printScores(AggregatedSnapshot aggregatedSnapshot) {
        printTotalGamesHeader(aggregatedSnapshot);
        groupByGuesser(aggregatedSnapshot)
                .entrySet()
                .stream()
                .map(this::aggregate)
                .sorted(WIN_RATE_COMPARATOR.thenComparing(AVG_GUESSES_COMPARATOR))
                .forEachOrdered(stats -> {
                    printGuesserHeader(stats.getKey());
                    printStats(stats.getValue());
                    printFooter();
                });
    }



    private void printGuesserHeader(String guesserHeader) {
        println(BORDER);
        printTitle(guesserHeader);
        println(BORDER);
    }

    private void printTitle(String title) {
        println(String.format(TITLE_FORMAT, title));
    }

    private void printTotalGamesHeader(AggregatedSnapshot aggregatedSnapshot) {
        println(BORDER);
        printTitle(String.format("TOTAL GAMES: %d", aggregatedSnapshot.getGamesCount()));
        printFooter();
    }

    private void printFooter() {
        println(BORDER);
        println();
    }

    private void printStats(AggregatedSnapshot stats) {
        println("| {w%-30s} |{g %-10d} |", "Total Games", stats.getGamesCount());
        println("| {w%-30s} |{g %-8.2f %% }|", "Win Rate", stats.getWinRate());
        println("| {w%-30s} |{g %-10.2f }|", "Avg. guesses per game", stats.getAverageGuessesPerGame());
        println("| {w%-30s} |{g %-10d }|", "Min. guesses per game", stats.getMinGuessesPerGame());
        println("| {w%-30s} |{g %-8.4f s }|", "Avg. time per game", stats.getAvgMillis()/100.f);
    }

    private Map<String, List<GameSnapshot>> groupByGuesser(AggregatedSnapshot byGuesser) {
        return byGuesser.getStats()
                .stream().collect(groupingBy(s -> s.getGuesser().name()));
    }

    private Map.Entry<String, AggregatedSnapshot> aggregate(Map.Entry<String, List<GameSnapshot>> entry) {
        return Map.entry(entry.getKey(), new AggregatedSnapshot(entry.getValue()));
    }
    private static String formatBorder(int firstColumnWidth, int secondColumnWidth) {
        return String.format("+%s+%s+", repeated("-", firstColumnWidth), repeated("-", secondColumnWidth));
    }

}
