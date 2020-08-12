package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

public class SimpleActionDefinition implements ActionDefinition {

    private final int nextId;

    private final String description;

    public SimpleActionDefinition(final int nextId) {
        this(nextId, null);
    }

    public SimpleActionDefinition(final int nextId, final String description) {
        this.nextId = nextId;
        this.description = description;
    }

    @Override
    public ActionInstance toInstance(final GameContext context) {
        return new ActionInstance(nextId, description);
    }

}
