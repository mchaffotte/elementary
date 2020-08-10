package fr.chaffotm.gamebook.elementary.model;

public class Option {

    private final int upperLimit;

    private final int nextId;

    public Option(int upperLimit, int nextId) {
        this.upperLimit = upperLimit;
        this.nextId = nextId;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public int getNextId() {
        return nextId;
    }

}
