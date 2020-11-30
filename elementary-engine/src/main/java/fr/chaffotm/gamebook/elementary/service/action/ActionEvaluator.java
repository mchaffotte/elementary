package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.OptionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import fr.chaffotm.gamebook.elementary.service.expression.ExpressionEvaluator;

import java.util.List;

public class ActionEvaluator {

    private final ExpressionEvaluator evaluator = new ExpressionEvaluator();

    public ActionInstance toInstance(final ActionDefinition action, final GameContext context) {
        final String expression = action.getExpression();
        final Integer value = evaluator.evaluateSkills(expression, context);
        final List<OptionDefinition> options = action.getOptions();
        for (OptionDefinition option : options) {
            if (evaluator.evaluateIndications(option.getExpression(), context, value)) {
                return new ActionInstance(option);
            }
        }
        return null;
    }
}
