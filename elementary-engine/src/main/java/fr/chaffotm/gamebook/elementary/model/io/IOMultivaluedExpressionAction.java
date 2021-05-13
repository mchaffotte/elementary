package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.Evaluation;
import fr.chaffotm.gamebook.elementary.model.entity.definition.OptionDefinition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IOMultivaluedExpressionAction implements IOAction {

    private String description;

    private String expression;

    private IOEvaluation evaluation;

    private List<IOOption> options = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public IOEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(IOEvaluation evaluation) {
        this.evaluation = evaluation;
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
                .expression(expression)
                .evaluation(evaluation == null ? Evaluation.PRE : Evaluation.valueOf(evaluation.name()));
        while (iterator.hasNext()) {
            builder.option(toOptionDefinition(iterator.next()));
        }
        return builder.build();
    }

    private OptionDefinition toOptionDefinition(IOOption option) {
        final OptionDefinitionBuilder optionBuilder = new OptionDefinitionBuilder(option.getToReference())
                .description(description)
                .expression(option.getExpression());
        if (option.getEvent() != null) {
            optionBuilder.event(option.getEvent().toEventDefinition());
        }
        return optionBuilder.build();
    }
}
