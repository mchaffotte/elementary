package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.Evaluation;
import fr.chaffotm.gamebook.elementary.model.entity.definition.OptionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import fr.chaffotm.gamebook.elementary.service.expression.ExpressionEvaluator;
import fr.chaffotm.gamebook.elementary.service.expression.VariableBuilder;

import java.util.List;

public class ActionEvaluator {

    private final ExpressionEvaluator evaluator = new ExpressionEvaluator();

    private Object getValue(final String expression, final GameContext context) {
        if (expression == null || expression.isBlank()) {
            return null;
        }
        final VariableBuilder builder = new VariableBuilder(context)
                .skills()
                .die()
                .indications()
                .money();
        return evaluator.evaluate(expression, builder);
    }

    public ActionInstance toInstance(final ActionDefinition action, final GameContext context) {
        if (action.getEvaluation() == Evaluation.POST) {
            return new ActionInstance(action);
        }
        final String expression = action.getExpression();
        final Object value = getValue(expression, context);
        final List<OptionDefinition> options = action.getOptions();
        final VariableBuilder builder = new VariableBuilder(context)
                .indications()
                .value(value);
        for (OptionDefinition option : options) {
            if (evaluator.evaluateToBoolean(option.getExpression(), builder)) {
                return new ActionInstance(option);
            }
        }
        return null;
    }

    public OptionDefinition getOption(final ActionDefinition definition, final String answer) {
        final VariableBuilder builder = new VariableBuilder(null)
                .answer(answer);
        for (OptionDefinition option : definition.getOptions()) {
            if (evaluator.evaluateToBoolean(option.getExpression(), builder)) {
                return option;
            }
        }
        return null;
    }

}
