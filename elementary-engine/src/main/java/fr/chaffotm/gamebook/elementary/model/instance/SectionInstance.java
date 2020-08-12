package fr.chaffotm.gamebook.elementary.model.instance;

import java.util.List;
import java.util.Objects;

public class SectionInstance {

    private int id;

    private List<String> paragraphs;

    private List<ActionInstance> actions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<ActionInstance> getActions() {
        return actions;
    }

    public void setActions(List<ActionInstance> actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionInstance instance = (SectionInstance) o;
        return id == instance.id &&
                Objects.equals(paragraphs, instance.paragraphs) &&
                Objects.equals(actions, instance.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paragraphs, actions);
    }
}
