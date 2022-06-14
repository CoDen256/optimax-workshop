package optimax.game.run;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public abstract class RepeatedGameRunner extends GameRunner{

    private final int times;

    RepeatedGameRunner(int times) {
        this.times = times;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            super.run();
        }
    }
}
