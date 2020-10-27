package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.ExpressionEvaluator;
import fr.chaffotm.gamebook.elementary.service.GameContext;

public class ExpressionActionDefinition implements ActionDefinition {

    private final ExpressionEvaluator evaluator;

    private final String expression;

    private final int nextId;

    public ExpressionActionDefinition(final String expression, final int nextId) {
        evaluator = new ExpressionEvaluator();
        this.expression = expression;
        this.nextId = nextId;
    }

    @Override
    public ActionInstance toInstance(final GameContext context) {
        if (evaluator.evaluateIndications(expression, context)) {
            return new ActionInstance(nextId);
        }
        return null;
    }

}
