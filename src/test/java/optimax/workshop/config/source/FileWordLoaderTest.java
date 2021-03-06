package optimax.workshop.config.source;

import static com.google.common.truth.Truth.assertThat;
import static optimax.workshop.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import optimax.workshop.config.FileWordLoader;
import optimax.workshop.core.Word;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class FileWordLoaderTest {

    @Test
    void readSmallSet() {
        Collection<Word> expected = Set.of(
                word("adult"),
                word("brews"),
                word("appol"),
                word("valid")
        );

        assertThat(FileWordLoader.load(path("/words.txt"))).containsExactlyElementsIn(expected);
    }

    @Test
    void readWholeSet() {

        assertEquals(5757, FileWordLoader.load(path("/words-full.txt")).size());
    }

    @Test
    void readSpacesDoesNotFail() {
        Collection<Word> expected = Set.of(
                word("adult"),
                word("brews"),
                word("appol"),
                word("valid")
        );
        assertThat(FileWordLoader.load(path("/words-spaces.txt"))).containsExactlyElementsIn(expected);
    }

    @Test
    void failAtNullPath() {
        assertThrows(NullPointerException.class, () ->  FileWordLoader.load(null));
    }

    @Test
    void failAtNonExistingPath() {
        assertThrows(IllegalArgumentException.class, () ->  FileWordLoader.load(UUID.randomUUID().toString()));
    }

    @Test
    void failAtInvalidWords() {
        assertThrows(Word.InvalidWordException.class, () ->  FileWordLoader.load(path("/words-invalid.txt")));
    }

    private String path(String name) {
        return FileWordLoaderTest.class.getResource(name).getPath();
    }
}