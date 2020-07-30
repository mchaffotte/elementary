package fr.chaffotm.gamebook.elementary.model.resource;

public class Action {

    private final int id;

    private final String description;

    public Action(final String description, final int id) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
