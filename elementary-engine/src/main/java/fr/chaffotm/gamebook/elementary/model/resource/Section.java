package fr.chaffotm.gamebook.elementary.model.resource;

import java.util.List;
import java.util.Objects;

public class Section {

    private long storyId;

    private int reference;

    private String text;

    private List<Action> actions;

    public long getStoryId() {
        return storyId;
    }

    public void setStoryId(long storyId) {
        this.storyId = storyId;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return storyId == section.storyId && reference == section.reference && Objects.equals(text, section.text) && Objects.equals(actions, section.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId, reference, text, actions);
    }
}
