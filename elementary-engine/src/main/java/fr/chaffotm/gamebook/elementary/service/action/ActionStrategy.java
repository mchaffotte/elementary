package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.List;

public interface ActionStrategy {

    List<ActionInstance> getActionInstances(List<ActionDefinition> actions, GameContext context);

}
