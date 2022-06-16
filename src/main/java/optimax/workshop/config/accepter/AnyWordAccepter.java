package optimax.workshop.config.accepter;

import optimax.workshop.core.Word;
import optimax.workshop.runner.WordAccepter;

public class AnyWordAccepter implements WordAccepter {
    @Override
    public boolean isAccepted(Word word) {
        return true;
    }
}
