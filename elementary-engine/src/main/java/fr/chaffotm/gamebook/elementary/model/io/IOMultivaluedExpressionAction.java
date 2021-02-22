package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.OptionDefinition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IOMultivaluedExpressionAction implements IOAction{

    private String expression;

    private List<IOOption> options = new ArrayList<>();

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<IOOption> getOptions() {
        return options;
    }

    public void setOptions(List<IOOption> options) {
        this.options = options;
    }

    @Override
    public ActionDefinition toActionDefinition() {
        final Iterator<IOOption> iterator = options.iterator();
        final ActionDefinitionBuilder builder = new ActionDefinitionBuilder(toOptionDefinition(iterator.next()))
                .expression(expression);
        while (iterator.hasNext()) {
            builder.option(toOptionDefinition(iterator.next()));
        }
        return builder.build();
    }

    private OptionDefinition toOptionDefinition(IOOption option) {
        final OptionDefinitionBuilder optionBuilder = new OptionDefinitionBuilder(option.getToReference())
                .expression(option.getExpression());
        if (option.getEvent() != null) {
            optionBuilder.event(option.getEvent().toEventDefinition());
        }
        return optionBuilder.build();
    }
}
