package fr.chaffotm.gamebook.elementary.model.definition;

import java.util.ArrayList;
import java.util.List;

public class SectionDefinition {

    private int id;

    private List<String> paragraphs;

    private List<Event> events;

    private List<ActionDefinition> actions;

    public SectionDefinition() {
        events = new ArrayList<>();
    }

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<ActionDefinition> getActions() {
        return actions;
    }

    public void setActions(List<ActionDefinition> actions) {
        this.actions = actions;
    }
}