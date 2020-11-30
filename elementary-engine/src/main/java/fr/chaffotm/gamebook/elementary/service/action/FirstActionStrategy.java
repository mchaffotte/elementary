package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FirstActionStrategy implements ActionStrategy {

    private final ActionEvaluator evaluator = new ActionEvaluator();

    public List<ActionInstance> getActionInstances(final List<ActionDefinition> actions, final GameContext context) {
        final List<ActionInstance> instances = new ArrayList<>();
        final Iterator<ActionDefinition> iterator = actions.iterator();
        while (iterator.hasNext() && instances.size() < 1) {
            ActionDefinition action = iterator.next();
            ActionInstance instance = evaluator.toInstance(action, context);
            if (instance != null) {
                instances.add(instance);
            }
        }
        return instances;
    }
}
