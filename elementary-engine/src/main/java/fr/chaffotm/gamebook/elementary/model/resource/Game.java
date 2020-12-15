package fr.chaffotm.gamebook.elementary.model.resource;

import java.util.Objects;

public class Game {

    private Section section;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(section, game.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section);
    }
}
