package fr.chaffotm.gamebook.elementary.model;

public class GameContext {

    private final Die die;

    public GameContext(final Die die) {
        this.die = die;
    }

    public Die getDie() {
        return die;
    }

}
