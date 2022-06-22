package optimax.workshop.guesser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The {@code RotatingGuesserFactory} is a {@link Guesser} supplier, that accepts several
 * guesser suppliers and rotates the suppliers depending on the total number of invocations.
 * <p>
 * The guessers suppliers will be evenly distributed across the invocations, so that each
 * supplier will be run approximately the same amount of time.
 */
public class RotatingGuesserFactory implements Supplier<Guesser> {
    /** The list of {@link Guesser} factories */
    private final List<Supplier<Guesser>> guessers;
    /** The total number of invocations */
    private final int totalInvocations;
    /** Current invocation */
    private int currentInvocation = 0;

    public RotatingGuesserFactory(int totalInvocations, List<Supplier<Guesser>> guessers) {
        this.guessers = new ArrayList<>(guessers);
        this.totalInvocations = totalInvocations;
    }

    @Override
    public Guesser get() {
        int currentInvocationModTotal = this.currentInvocation++ % totalInvocations;
        int nextSupplierIndex = currentInvocationModTotal * guessers.size() / totalInvocations;
        return guessers.get(nextSupplierIndex).get();
    }
}
