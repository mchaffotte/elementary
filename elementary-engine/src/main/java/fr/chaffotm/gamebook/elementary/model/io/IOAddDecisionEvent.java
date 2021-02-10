package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.EventDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;

public class IOAddDecisionEvent implements IOEvent{

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public EventDefinition toEventDefinition() {
        return new EventDefinitionBuilder("add-indication")
                .parameter("decision", value)
                .build();
    }
}
