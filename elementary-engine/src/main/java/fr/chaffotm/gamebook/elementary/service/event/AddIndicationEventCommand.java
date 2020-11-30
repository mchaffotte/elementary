package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ParameterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationType;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.Set;

public class AddIndicationEventCommand implements EventCommand {

    @Override
    public void execute(final GameContext context, final Set<ParameterDefinition> parameters) {
        for (ParameterDefinition parameter : parameters) {
            IndicationInstance indication = build(parameter);
            context.addIndication(indication);
        }
    }

    private IndicationInstance build(final ParameterDefinition parameter) {
        final String name = parameter.getName();
        final IndicationType type = IndicationType.valueOf(name.toUpperCase());
        return new IndicationInstance(type, parameter.getValue());
    }
}
