package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Event;
import fr.chaffotm.gamebook.elementary.model.definition.Indication;
import fr.chaffotm.gamebook.elementary.model.definition.IndicationType;

public class AddClueAndFailEvent implements Event {

    private final String clue;

    public AddClueAndFailEvent(String clue) {
        this.clue = clue;
    }

    @Override
    public void execute(GameContext gameContext) {
        gameContext.addIndication(new Indication(IndicationType.CLUE, clue));
        throw new IllegalStateException();
    }

}
