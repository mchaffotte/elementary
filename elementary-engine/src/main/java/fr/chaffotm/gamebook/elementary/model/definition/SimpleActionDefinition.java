package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

public class SimpleActionDefinition implements ActionDefinition {

    private final int nextId;

    private final String description;

    private final Event event;

    public SimpleActionDefinition(final int nextId) {
        this(nextId, null, null);
    }

    public SimpleActionDefinition(final int nextId, final String description) {
        this(nextId, description, null);
    }

    public SimpleActionDefinition(final int nextId, final String description, final Event event) {
        this.nextId = nextId;
        this.description = description;
        this.event = event;
    }

    @Override
    public ActionInstance toInstance(final GameContext context) {
        return new ActionInstance(nextId, description, event);
    }

}
