package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Event;

public class AddClueAndFailEvent implements Event {

    private final String clue;

    public AddClueAndFailEvent(String clue) {
        this.clue = clue;
    }

    @Override
    public void execute(GameContext gameContext) {
        gameContext.addClue(clue);
        throw new IllegalStateException();
    }

}
