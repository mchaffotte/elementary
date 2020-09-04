package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;

public class NoAction implements ActionDefinition {

    @Override
    public ActionInstance toInstance(final GameContext context) {
        return null;
    }

}
