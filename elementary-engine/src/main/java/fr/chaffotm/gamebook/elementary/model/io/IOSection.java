package fr.chaffotm.gamebook.elementary.model.io;

import java.util.ArrayList;
import java.util.List;

public class IOSection {

    private int reference;

    private List<String> paragraphs;

    private List<IOAction> actions = new ArrayList<>();

    private List<IOEvent> events = new ArrayList<>();

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

    public List<IOAction> getActions() {
        return actions;
    }

    public void setActions(List<IOAction> actions) {
        this.actions = actions;
    }

    public List<IOEvent> getEvents() {
        return events;
    }

    public void setEvents(List<IOEvent> events) {
        this.events = events;
    }
}
