package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.EventEntity;
import fr.chaffotm.gamebook.elementary.model.entity.OptionEntity;

public class OptionBuilder {

    private OptionEntity option;

    public OptionBuilder(int nextReference) {
        option = new OptionEntity();
        option.setNextReference(nextReference);
    }

    public OptionEntity build() {
        return option;
    }

    public OptionBuilder description(final String description) {
        option.setDescription(description);
        return this;
    }

    public OptionBuilder expression(final String expression) {
        option.setExpression(expression);
        return this;
    }

    public OptionBuilder event(final EventEntity event) {
        option.setEvent(event);
        return this;
    }
}
