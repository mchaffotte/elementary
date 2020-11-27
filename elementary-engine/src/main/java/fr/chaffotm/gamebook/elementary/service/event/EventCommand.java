package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ParameterDefinition;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.Set;

public interface EventCommand {

    void execute(GameContext context, Set<ParameterDefinition> parameters);

}
