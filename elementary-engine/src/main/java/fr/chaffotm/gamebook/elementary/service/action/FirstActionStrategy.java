package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.ActionEntity;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FirstActionStrategy implements ActionStrategy {

    private final ActionEvaluator evaluator = new ActionEvaluator();

    public List<ActionInstance> getActionInstances(final List<ActionEntity> actions, final GameContext context) {
        final List<ActionInstance> instances = new ArrayList<>();
        final Iterator<ActionEntity> iterator = actions.iterator();
        while (iterator.hasNext() && instances.size() < 1) {
            ActionEntity action = iterator.next();
            ActionInstance instance = evaluator.toInstance(action, context);
            if (instance != null) {
                instances.add(instance);
            }
        }
        return instances;
    }
}
