package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.EventEntity;
import fr.chaffotm.gamebook.elementary.model.entity.ParameterEntity;

public class EventBuilder {

    private EventEntity event;

    public EventBuilder(final String type) {
        event = new EventEntity();
        event.setType(type);
    }

    public EventEntity build() {
        return event;
    }

    public EventBuilder parameter(final String name, final String value) {
        final ParameterEntity parameter = new ParameterEntity();
        parameter.setName(name);
        parameter.setValue(value);
        event.addParameter(parameter);
        return this;
    }
}
