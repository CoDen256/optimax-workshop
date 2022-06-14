package optimax.workshop.config.runner;

import java.util.function.Supplier;
import optimax.workshop.core.WordleGame;
import optimax.workshop.runner.GameObserver;
import optimax.workshop.runner.Guesser;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class WordleRunner extends BaseGameRunner{

    private final Supplier<WordleGame> gameSupplier;
    private final Supplier<GameObserver> observerSupplier;
    private final Supplier<Guesser> guesserSupplier;

    public WordleRunner(Supplier<WordleGame> gameSupplier,
                                  Supplier<GameObserver> observerSupplier,
                                  Supplier<Guesser> guesserSupplier) {
        this.gameSupplier = gameSupplier;
        this.observerSupplier = observerSupplier;
        this.guesserSupplier = guesserSupplier;

    }
    @Override
    protected WordleGame createGame() {
        return gameSupplier.get();
    }

    @Override
    protected Guesser createGuesser() {
        return guesserSupplier.get();
    }

    @Override
    protected GameObserver createObserver() {
        return observerSupplier.get();
    }

}
