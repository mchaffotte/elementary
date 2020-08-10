package fr.chaffotm.gamebook.elementary.model;

import java.util.List;

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
}
