package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.OptionDefinition;

public class ActionDefinitionBuilder {

    private final ActionDefinition action;

    public ActionDefinitionBuilder(final OptionDefinition option) {
        action = new ActionDefinition();
        option(option);
    }

    public ActionDefinitionBuilder(final int nextReference) {
        this(null, nextReference);
    }

    public ActionDefinitionBuilder(final String description, final int nextReference) {
        action = new ActionDefinition();
        final OptionDefinition option = new OptionDefinition();
        option.setNextReference(nextReference);
        option.setDescription(description);
        option(option);
    }

    public ActionDefinition build() {
        return action;
    }

    public ActionDefinitionBuilder expression(final String expression) {
        action.setExpression(expression);
        return this;
    }

    public ActionDefinitionBuilder option(final OptionDefinition option) {
        action.addOption(option);
        return this;
    }
}
