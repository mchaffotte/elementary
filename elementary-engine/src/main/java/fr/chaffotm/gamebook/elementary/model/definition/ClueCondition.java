package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.GameContext;

public class ClueCondition implements Condition {

    private final String clue;

    public ClueCondition(final String clue) {
        this.clue = clue;
    }

    public boolean isTrue(final GameContext context) {
        return context.hasClue(clue);
    }

}
