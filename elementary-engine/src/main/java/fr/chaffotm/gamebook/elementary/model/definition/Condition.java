package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.GameContext;

public interface Condition {

    boolean isTrue(GameContext context);

}
