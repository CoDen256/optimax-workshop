package optimax.workshop.config.source;

import static java.lang.String.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import optimax.workshop.core.Word;

/**
 * @author Denys Chernyshov
 * @since 1.0
 */
public class FileWordSource extends CollectionWordSource {

    public FileWordSource(String path) {
        super(load(path));
    }

    private static Collection<Word> load(String filename){
        File file = createFile(filename);
        return readFile(file);
    }

    private static File createFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) throw new IllegalArgumentException(format("Unable to find file at path %s", file));
        return file;
    }

    private static Collection<Word> readFile(File file) {
        try (FileInputStream resource = new FileInputStream(file)){
            return parse(new String(resource.readAllBytes(), StandardCharsets.UTF_8));
        }catch (IOException ex){
            throw new IllegalStateException(ex);
        }
    }

    private static Collection<Word> parse(String content){
        String[] lines = content.split("\n");
        Collection<Word> words = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.isBlank()) continue;
            words.add(tryCreateWord(i, line));
        }
        return words;
    }

    private static Word tryCreateWord(int lineNumber, String line) {
        try {
            return new Word(line.trim());
        } catch (IllegalArgumentException ex){
            throw new IllegalArgumentException(format("Unable to create word `%s` at line %d", line, lineNumber), ex);
        }
    }
}
