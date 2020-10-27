package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Indication;
import fr.chaffotm.gamebook.elementary.model.definition.IndicationType;
import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

public class ExpressionEvaluator {

    public boolean evaluateIndications(final String expression, final GameContext context) {
        final Map<String, Map<String, Boolean>> variables = new HashMap<>();
        for (IndicationType type : IndicationType.values()) {
            variables.put(type.toString(), new ExistenceMap<String, Boolean>());
        }
        for (Indication indication : context.getIndications()) {
            final Map<String, Boolean> type = variables.get(indication.getType().toString());
            type.put(indication.getValue(), true);
        }
        System.out.println(MVEL.eval(expression, variables));
        return MVEL.eval(expression, variables, Boolean.class);
    }

}
