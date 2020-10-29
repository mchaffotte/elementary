package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;

import java.util.ArrayList;
import java.util.List;

public class AllActionStrategy implements ActionStrategy {

    @Override
    public List<ActionInstance> getActionInstances(final List<ActionDefinition> actions, final GameContext context) {
        final List<ActionInstance> instances = new ArrayList<>();
        for (ActionDefinition action : actions) {
            ActionInstance instance = action.toInstance(context);
            if (instance != null) {
                instances.add(instance);
            }
        }
        return instances;
    }

}
