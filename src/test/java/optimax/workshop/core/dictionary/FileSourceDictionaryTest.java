package optimax.workshop.core.dictionary;

import static com.google.common.truth.Truth.assertThat;
import static optimax.workshop.core.TestUtilities.word;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import optimax.workshop.config.source.FileWordSource;
import optimax.workshop.core.Word;
import org.junit.jupiter.api.Test;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
class FileSourceDictionaryTest {

    @Test
    void readSmallSet() {
        Collection<Word> expected = Set.of(
                word("adult"),
                word("brews"),
                word("appol"),
                word("valid")
        );
        FileWordSource dict = getDictionary("/words.txt");

        assertThat(dict.getAll()).containsExactlyElementsIn(expected);
    }

    @Test
    void readWholeSet() {
        FileWordSource dict = getDictionary("/words-full.txt");

        assertEquals(5757, dict.getAll().size());
    }

    @Test
    void readSpacesDoesNotFail() {
        Collection<Word> expected = Set.of(
                word("adult"),
                word("brews"),
                word("appol"),
                word("valid")
        );
        FileWordSource dict = getDictionary("/words-spaces.txt");
        assertThat(dict.getAll()).containsExactlyElementsIn(expected);
    }

    @Test
    void failAtNullPath() {
        assertThrows(NullPointerException.class, () -> new FileWordSource(null));
    }

    @Test
    void failAtNonExistingPath() {
        assertThrows(IllegalArgumentException.class, () -> new FileWordSource(UUID.randomUUID().toString()));
    }

    @Test
    void failAtInvalidWords() {
        assertThrows(IllegalArgumentException.class, () -> new FileWordSource("/words-invalid.txt"));
    }

    private FileWordSource getDictionary(String name) {
        return new FileWordSource(FileSourceDictionaryTest.class.getResource(name).getPath());
    }
}