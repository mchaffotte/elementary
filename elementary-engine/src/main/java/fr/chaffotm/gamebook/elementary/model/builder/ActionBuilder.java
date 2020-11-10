package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.ActionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.OptionEntity;

public class ActionBuilder {

    private final ActionEntity action;

    public ActionBuilder(final OptionEntity option) {
        action = new ActionEntity();
        option(option);
    }

    public ActionBuilder(final int nextReference) {
        this(null, nextReference);
    }

    public ActionBuilder(final String description, final int nextReference) {
        action = new ActionEntity();
        final OptionEntity option = new OptionEntity();
        option.setNextReference(nextReference);
        option.setDescription(description);
        option(option);
    }

    public ActionEntity build() {
        return action;
    }

    public ActionBuilder expression(final String expression) {
        action.setExpression(expression);
        return this;
    }

    public ActionBuilder option(final OptionEntity option) {
        action.addOption(option);
        return this;
    }
}
