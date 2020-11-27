package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionSelection;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.service.action.ActionStrategy;
import fr.chaffotm.gamebook.elementary.service.action.AllActionStrategy;
import fr.chaffotm.gamebook.elementary.service.action.FirstActionStrategy;
import fr.chaffotm.gamebook.elementary.service.event.EventCommand;
import fr.chaffotm.gamebook.elementary.service.event.EventFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class SectionService {

    private final EventFactory eventFactory;

    public SectionService() {
        eventFactory = new EventFactory();
    }

    public SectionInstance evaluate(final SectionDefinition definition, final GameContext context) {
        if (definition == null) {
            throw new IllegalArgumentException("Section does not exist");
        }
        final SectionInstance instance = new SectionInstance();
        instance.setId(definition.getReference());
        instance.setParagraphs(definition.getParagraphs());

        final ActionStrategy strategy = getStrategy(definition.getSelection());
        final List<ActionInstance> actions = strategy.getActionInstances(definition.getActions(), context);
        if (actions.isEmpty() && !definition.getActions().isEmpty()) {
            throw new IllegalStateException("No action is possible");
        }
        instance.setActions(actions);

        for (EventDefinition event : definition.getEvents()) {
            final EventCommand command = eventFactory.getEvent(event.getType());
            command.execute(context, event.getParameters());
        }
        return instance;
    }

    private ActionStrategy getStrategy(ActionSelection selection) {
        if (selection == ActionSelection.FIRST) {
            return new FirstActionStrategy();
        }
        return new AllActionStrategy();
    }

}
