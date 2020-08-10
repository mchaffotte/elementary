package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SectionService {

    public SectionInstance evaluate(final SectionDefinition definition, final GameContext context) {
        final SectionInstance instance = new SectionInstance();
        instance.setId(definition.getId());
        instance.setParagraphs(definition.getParagraphs());
        final List<ActionInstance> actions = new ArrayList<>();
        for (ActionDefinition action : definition.getActions()) {
            ActionInstance actionInstance = action.toInstance(context);
            if (actionInstance != null) {
                actions.add(actionInstance);
            }
        }
        instance.setActions(actions);
        return instance;
    }

}
