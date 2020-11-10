package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.ActionEntity;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.List;

public interface ActionStrategy {

    List<ActionInstance> getActionInstances(List<ActionEntity> actions, GameContext context);

}
