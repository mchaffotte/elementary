package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.GameContext;

public class ClueEvent implements Event {

    private final String clue;

    public ClueEvent(final String clue) {
        this.clue = clue;
    }

    @Override
    public void execute(final GameContext gameContext) {
        gameContext.addClue(clue);
    }

}
