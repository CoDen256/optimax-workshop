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
import optimax.workshop.runner.FullObserver;
import optimax.workshop.stats.AggregatedSnapshots;
import optimax.workshop.stats.GameSnapshot;

public class ScorePrettyPrinter implements FullObserver {
    public static final int FIRST_COLUMN_WIDTH = 32;
    public static final int SECOND_COLUMN_WIDTH = 10;
    public static final String BORDER = formatBorder(FIRST_COLUMN_WIDTH, SECOND_COLUMN_WIDTH);
    public static final String TITLE_FORMAT = "| {g%-41s} |";

    public static final Comparator<Map.Entry<String, AggregatedSnapshots>> WIN_RATE_COMPARATOR = comparingDouble(
            (Map.Entry<String, AggregatedSnapshots> entry) -> entry.getValue().getWinRate()
    ).reversed();

    public static final Comparator<Map.Entry<String, AggregatedSnapshots>> AVG_GUESSES_COMPARATOR = comparingDouble(
            entry -> entry.getValue().getAverageGuessesPerGame()
    );

    @Override
    public void onRunLaunched(int totalRuns) {}

    @Override
    public void onRunFinished(AggregatedSnapshots aggregatedSnapshots) {
        printScores(aggregatedSnapshots);
    }

    public void printScores(AggregatedSnapshots aggregatedSnapshots) {
        printTotalGamesHeader(aggregatedSnapshots);
        groupByGuesser(aggregatedSnapshots)
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

    private void printTotalGamesHeader(AggregatedSnapshots aggregatedSnapshots) {
        println(BORDER);
        printTitle(String.format("TOTAL GAMES: %d", aggregatedSnapshots.getGamesCount()));
        printFooter();
    }

    private void printFooter() {
        println(BORDER);
        println();
    }

    private void printStats(AggregatedSnapshots stats) {
        println("| {w%-30s} | {g%-8d} |", "Total Games", stats.getGamesCount());
        println("| {w%-30s} | {g%-6.2f %% }|", "Win Rate", stats.getWinRate());
        println("| {w%-30s} | {g%-8.2f }|", "Avg. guesses per game", stats.getAverageGuessesPerGame());
        println("| {w%-30s} | {g%-8d }|", "Min. guesses per game", stats.getMinGuessesPerGame());
    }

    private Map<String, List<GameSnapshot>> groupByGuesser(AggregatedSnapshots byGuesser) {
        return byGuesser.getStats()
                .stream().collect(groupingBy(s -> s.getGuesser().name()));
    }

    private Map.Entry<String, AggregatedSnapshots> aggregate(Map.Entry<String, List<GameSnapshot>> entry) {
        return Map.entry(entry.getKey(), new AggregatedSnapshots(entry.getValue()));
    }
    private static String formatBorder(int firstColumnWidth, int secondColumnWidth) {
        return String.format("+%s+%s+", repeated("-", firstColumnWidth), repeated("-", secondColumnWidth));
    }

}
