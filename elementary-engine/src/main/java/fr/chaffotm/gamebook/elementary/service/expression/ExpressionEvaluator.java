package fr.chaffotm.gamebook.elementary.service.expression;

import org.mvel2.MVEL;

import java.util.Map;

public class ExpressionEvaluator {

    public boolean evaluateToBoolean(final String expression, final VariableBuilder builder) {
        if (expression == null || expression.isBlank()) {
            return true;
        }
        final Map<String, Object> variables = builder.toVariables();
        return MVEL.evalToBoolean(expression, variables);
    }

    public Object evaluate(final String expression, final VariableBuilder builder) {
        if (expression == null || expression.isBlank()) {
            return null;
        }
        final Map<String, Object> variables = builder.toVariables();
        return MVEL.eval(expression, variables);
    }
}
