package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.GameContext;

public class IndicationEvent implements Event {

    private final Indication indication;

    public IndicationEvent(final Indication indication) {
        this.indication = indication;
    }

    @Override
    public void execute(final GameContext gameContext) {
        gameContext.addIndication(indication);
    }

}
