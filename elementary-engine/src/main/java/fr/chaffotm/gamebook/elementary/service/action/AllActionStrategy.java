package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.ArrayList;
import java.util.List;

public class AllActionStrategy implements ActionStrategy {

    private final ActionEvaluator evaluator = new ActionEvaluator();

    @Override
    public List<ActionInstance> getActionInstances(final List<ActionDefinition> actions, final GameContext context) {
        final List<ActionInstance> instances = new ArrayList<>();
        for (ActionDefinition action : actions) {
            ActionInstance instance = evaluator.toInstance(action, context);
            if (instance != null) {
                instances.add(instance);
            }
        }
        return instances;
    }

}
