package fr.chaffotm.gamebook.elementary.model.instance;

import java.util.Objects;

public class ActionInstance {

    private final String description;

    private final int nextId;

    public ActionInstance(final int nextId) {
        this(nextId, null);
    }

    public ActionInstance(final int nextId, final String description) {
        this.nextId = nextId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getNextId() {
        return nextId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionInstance that = (ActionInstance) o;
        return nextId == that.nextId &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, nextId);
    }
}
