package optimax.workshop.config.observer;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void print(String format, Object... params){
        System.out.printf(pretty(format), params);
    }

    public static void println(String format, Object... params){
        print(format + "%n", params);
    }

    public static void print(){
        print("");
    }

    public static void println(){
        println("");
    }

    public static String repeated(String repeated, int times){
        return repeated(repeated, times, "", "");
    }

    public static String repeated(String repeated, int times, String prefix, String suffix){
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

}
