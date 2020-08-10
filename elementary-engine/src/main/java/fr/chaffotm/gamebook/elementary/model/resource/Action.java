package fr.chaffotm.gamebook.elementary.model.resource;

import java.util.Objects;

public class Action {

    private final int id;

    private final String description;

    public Action(final int id, final String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return id == action.id &&
                Objects.equals(description, action.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
