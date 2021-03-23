package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ParameterDefinition;

public class EventDefinitionBuilder {

    private final EventDefinition event;

    public EventDefinitionBuilder(final String type) {
        event = new EventDefinition();
        event.setType(type);
    }

    public EventDefinition build() {
        return event;
    }

    public EventDefinitionBuilder parameter(final String name, final String value) {
        final ParameterDefinition parameter = new ParameterDefinition();
        parameter.setName(name);
        parameter.setValue(value);
        event.addParameter(parameter);
        return this;
    }

    public EventDefinitionBuilder expression(final String expression) {
        event.setExpression(expression);
        return this;
    }
}
