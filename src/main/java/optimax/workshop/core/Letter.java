package optimax.workshop.core;

import java.util.Objects;

public class Letter {
    private final char character;
    private final int pos;

    public Letter(char character, int pos) {
        this.character = character;
        this.pos = pos;
    }

    public char getChar() {
        return character;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter that = (Letter) o;
        return character == that.character && pos == that.pos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, pos);
    }
}