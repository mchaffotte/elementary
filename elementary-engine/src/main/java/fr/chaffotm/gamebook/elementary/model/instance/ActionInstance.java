package fr.chaffotm.gamebook.elementary.model.instance;

import fr.chaffotm.gamebook.elementary.model.definition.Event;

import java.util.Objects;

public class ActionInstance {

    private final String description;

    private final int nextId;

    private final Event event;

    public ActionInstance(final int nextId) {
        this(nextId, null);
    }

    public ActionInstance(final int nextId, final String description) {
        this(nextId, description, null);
    }

    public ActionInstance(final int nextId, final String description, final Event event) {
        this.nextId = nextId;
        this.description = description;
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public int getNextId() {
        return nextId;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionInstance that = (ActionInstance) o;
        return nextId == that.nextId &&
                Objects.equals(description, that.description) &&
                Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, nextId, event);
    }
}
