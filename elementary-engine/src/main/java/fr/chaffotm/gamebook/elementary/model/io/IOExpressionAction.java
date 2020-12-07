package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;

public class IOExpressionAction implements IOAction {

    private String expression;

    private int toReference;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getToReference() {
        return toReference;
    }

    public void setToReference(int toReference) {
        this.toReference = toReference;
    }

    @Override
    public ActionDefinition toActionDefinition() {
        return new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(toReference)
                        .expression(expression)
                        .build())
                .build();
    }
}
