package optimax.workshop.config.observer;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import optimax.workshop.core.Word;
import optimax.workshop.core.matcher.Match;
import optimax.workshop.core.matcher.MatchResult;
import optimax.workshop.core.matcher.MatchType;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class ConsoleUtils {

    private static final String RESET = "\033[0m";

    public static final String GREEN = "\033[1;92m";
    public static final String YELLOW = "\033[1;93m";
    public static final String WHITE = "\033[1;97m";
    private static final String BLACK = "\033[1;90m";
    private static final String RED = "\033[1;91m";

    private static final String GREEN_BG = "\033[1;102m";
    private static final String YELLOW_BG = "\033[1;103m";
    private static final String WHITE_BG = "\033[1;107m";
    private static final String RED_BG = "\033[1;101m";

    public static void print(String format, Object... params) {
        System.out.printf(pretty(format), params);
    }

    public static void println(String format, Object... params) {
        print(format + "%n", params);
    }

    public static void print() {
        print("");
    }

    public static void println() {
        println("");
    }

    public static String repeated(String repeated, int times) {
        return repeated(repeated, times, "", "");
    }

    public static String repeated(String repeated, int times, String prefix, String suffix) {
        return Stream.generate(() -> repeated)
                .limit(times)
                .collect(Collectors.joining("", prefix, suffix));
    }

    public static String pretty(String format) {
        return format.replace("{g", GREEN)
                .replace("{w", WHITE)
                .replace("{y", YELLOW)
                .replace("{b", BLACK)
                .replace("{r", RED)
                .replace("{G", GREEN_BG)
                .replace("{W", WHITE_BG)
                .replace("{Y", YELLOW_BG)
                .replace("{R", RED_BG)
                .replace("}", RESET)
                .replace("\n", "%n");
    }

    public static String getMatchColor(MatchType type) {
        switch (type) {
            case CORRECT:
                return "{G";
            case WRONG:
                return "{Y";
            default:
                return "{W";
        }
    }

    public static String formatWord(String charFormat, Word guess, String prefix, String suffix) {
        StringBuilder sb = new StringBuilder(prefix);
        for (char c : guess.toString().toUpperCase().toCharArray()) {
            sb.append(String.format(charFormat, c));
        }
        sb.append(suffix);
        return sb.toString();
    }

    public static String formatResult(MatchResult result, String charFormat, Function<MatchType, String> matchFormat) {
        StringBuilder sb = new StringBuilder();

        for (Match match : result.getMatches()) {
            sb.append(matchFormat.apply(match.getType()))
                    .append(String.format(charFormat, Character.toUpperCase(match.getLetter())))
                    .append("}");
        }
        return sb.toString();
    }

}
