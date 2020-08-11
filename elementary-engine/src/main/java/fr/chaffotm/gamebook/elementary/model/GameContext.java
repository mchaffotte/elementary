package fr.chaffotm.gamebook.elementary.model;

public class GameContext {

    private final Die die;

    private final Character character;

    public GameContext(final Die die, final Character character) {
        this.die = die;
        this.character = character;
    }

    public Die getDie() {
        return die;
    }

    public Character getCharacter() {
        return character;
    }
}
