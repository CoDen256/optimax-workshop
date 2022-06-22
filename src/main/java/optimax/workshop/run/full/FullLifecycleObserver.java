package optimax.workshop.run.full;

import optimax.workshop.run.multiple.AppLifecycleObserver;
import optimax.workshop.run.multiple.RepeatedWordleRunner;
import optimax.workshop.run.single.GameLifecycleObserver;
import optimax.workshop.run.single.SingleWordleRunner;

/**
 * Contains the logic for observing both the {@link RepeatedWordleRunner}
 * as well as {@link SingleWordleRunner}
 */
public interface FullLifecycleObserver extends AppLifecycleObserver, GameLifecycleObserver {
}
