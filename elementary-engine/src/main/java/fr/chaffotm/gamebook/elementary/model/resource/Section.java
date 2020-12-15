package fr.chaffotm.gamebook.elementary.model.resource;

import java.util.List;
import java.util.Objects;

public class Section {

    private int reference;

    private List<String> paragraphs;

    private List<Action> actions;

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return reference == section.reference && Objects.equals(paragraphs, section.paragraphs) && Objects.equals(actions, section.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, paragraphs, actions);
    }
}
