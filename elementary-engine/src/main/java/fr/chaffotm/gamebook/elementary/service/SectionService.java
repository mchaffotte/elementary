package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.service.action.ActionEvaluator;
import fr.chaffotm.gamebook.elementary.service.event.EventEvaluator;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class SectionService {

    private final EventEvaluator eventEvaluator;

    private final ActionEvaluator evaluator;

    public SectionService() {
        eventEvaluator = new EventEvaluator();
        evaluator = new ActionEvaluator();
    }

    public SectionInstance evaluate(final SectionDefinition definition, final GameContext context) {
        if (definition == null) {
            throw new IllegalArgumentException("Section does not exist");
        }
        final SectionInstance instance = new SectionInstance();
        instance.setReference(definition.getReference());
        instance.setText(definition.getText());

        final List<ActionInstance> actions = getActions(definition.getActions(), context);
        if (actions.isEmpty() && !definition.getActions().isEmpty()) {
            throw new IllegalStateException("No action is possible");
        }
        for (ActionInstance action : actions) {
            instance.addAction(action);
        }

        for (EventDefinition event : definition.getEvents()) {
            eventEvaluator.execute(event, context);
        }
        return instance;
    }

    private List<ActionInstance> getActions(List<ActionDefinition> actions, GameContext context) {
        final List<ActionInstance> instances = new ArrayList<>();
        final Iterator<ActionDefinition> actionIterator = actions.iterator();
        boolean enough = false;
        ActionInstance lastInstance = null;
        while (actionIterator.hasNext() && !enough) {
            final ActionDefinition action = actionIterator.next();
            final ActionInstance instance = evaluator.toInstance(action, context);
            if (instance != null) {
                enough = lastInstance != null && (hasNoDescription(lastInstance) || hasNoDescription(instance));
                if (!enough) {
                    instances.add(instance);
                    lastInstance = instance;
                    enough = instance.getDescription() == null;
                }
            }
        }
        return instances;
    }

    private boolean hasNoDescription(final ActionInstance instance) {
        final String description = instance.getDescription();
        return description == null || description.isEmpty();
    }
}
