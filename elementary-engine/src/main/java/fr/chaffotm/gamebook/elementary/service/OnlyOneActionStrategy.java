package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OnlyOneActionStrategy implements ActionStrategy {

    public List<ActionInstance> getActionInstances(final List<ActionDefinition> actions, final GameContext context) {
        final List<ActionInstance> instances = new ArrayList<>();
        final Iterator<ActionDefinition> iterator = actions.iterator();
        while (iterator.hasNext() && instances.size() < 1) {
            ActionDefinition action = iterator.next();
            ActionInstance instance = action.toInstance(context);
            if (instance != null) {
                instances.add(instance);
            }
        }
        return instances;
    }
}
