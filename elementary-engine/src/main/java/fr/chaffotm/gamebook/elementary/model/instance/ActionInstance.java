package fr.chaffotm.gamebook.elementary.model.instance;

import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;

import java.util.Objects;

public class ActionInstance {

    private final String description;

    private final int nextReference;

    private final EventDefinition event;

    public ActionInstance(final int nextReference) {
        this(nextReference, null);
    }

    public ActionInstance(final int nextReference, final String description) {
        this(nextReference, description, null);
    }

    public ActionInstance(final int nextReference, final String description, final EventDefinition event) {
        this.nextReference = nextReference;
        this.description = description;
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public int getNextReference() {
        return nextReference;
    }

    public EventDefinition getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionInstance that = (ActionInstance) o;
        return nextReference == that.nextReference &&
                Objects.equals(description, that.description) &&
                Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, nextReference, event);
    }

    @Override
    public String toString() {
        return "ActionInstance{" +
                "description='" + description + '\'' +
                ", nextReference=" + nextReference +
                ", event=" + event +
                '}';
    }
}
