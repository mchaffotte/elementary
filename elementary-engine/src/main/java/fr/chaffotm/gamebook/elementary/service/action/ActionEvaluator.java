package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.entity.ActionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.OptionEntity;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import fr.chaffotm.gamebook.elementary.service.expression.ExpressionEvaluator;

import java.util.List;

public class ActionEvaluator {

    private final ExpressionEvaluator evaluator = new ExpressionEvaluator();

    public ActionInstance toInstance(final ActionEntity action, final GameContext context) {
        final String expression = action.getExpression();
        Integer skill = null;
        if (expression != null && !expression.isBlank()) {
            skill = evaluator.evaluateSkills(expression, context);
        }
        final List<OptionEntity> options = action.getOptions();
        for (OptionEntity option : options) {
            if (evaluator.evaluateIndications(option.getExpression(), context, skill)) {
                return new ActionInstance(option.getNextReference(), option.getDescription(), option.getEvent());
            }
        }
        return null;
    }
}
