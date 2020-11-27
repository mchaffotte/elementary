package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.OptionDefinition;

public class OptionDefinitionBuilder {

    private OptionDefinition option;

    public OptionDefinitionBuilder(int nextReference) {
        option = new OptionDefinition();
        option.setNextReference(nextReference);
    }

    public OptionDefinition build() {
        return option;
    }

    public OptionDefinitionBuilder description(final String description) {
        option.setDescription(description);
        return this;
    }

    public OptionDefinitionBuilder expression(final String expression) {
        option.setExpression(expression);
        return this;
    }

    public OptionDefinitionBuilder event(final EventDefinition event) {
        option.setEvent(event);
        return this;
    }
}
