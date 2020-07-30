package fr.chaffotm.gamebook.elementary.model;

public class ActionDefinition {

    private String description;

    private int nextSectionId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNextSectionId() {
        return nextSectionId;
    }

    public void setNextSectionId(int nextSectionId) {
        this.nextSectionId = nextSectionId;
    }
}
