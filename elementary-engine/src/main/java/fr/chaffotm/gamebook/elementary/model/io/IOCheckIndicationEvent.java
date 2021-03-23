package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.EventDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;

public class IOCheckIndicationEvent implements IOEvent {

    private String expression;

    private IOIndicationType indication;

    private String value;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public IOIndicationType getIndication() {
        return indication;
    }

    public void setIndication(IOIndicationType indication) {
        this.indication = indication;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public EventDefinition toEventDefinition() {
        return new EventDefinitionBuilder("add-indication")
                .expression(expression)
                .parameter(indication.name().toLowerCase(), value)
                .build();
    }
}
