package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

public class ConditionActionDefinition implements ActionDefinition {

    private final Condition condition;

    private final int nextId;

    public ConditionActionDefinition(final Condition condition, final int nextId) {
        this.condition = condition;
        this.nextId = nextId;
    }

    @Override
    public ActionInstance toInstance(final GameContext context) {
        if (condition.isTrue(context)) {
            return new ActionInstance(nextId);
        }
        return null;
    }

}
