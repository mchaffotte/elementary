package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.ParameterEntity;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.Set;

public interface EventCommand {

    void execute(GameContext context, Set<ParameterEntity> paramters);

}
