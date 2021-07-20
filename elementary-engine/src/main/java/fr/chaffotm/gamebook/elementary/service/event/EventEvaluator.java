package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import fr.chaffotm.gamebook.elementary.service.expression.ExpressionEvaluator;
import fr.chaffotm.gamebook.elementary.service.expression.VariableBuilder;

public class EventEvaluator {

    private final ExpressionEvaluator evaluator = new ExpressionEvaluator();

    private final EventFactory eventFactory = new EventFactory();;

    public void execute(final EventDefinition event, final GameContext context) {
        final String expression = event.getExpression();
        final VariableBuilder builder = new VariableBuilder(context)
                .indications();
        if (evaluator.evaluateToBoolean(expression, builder)) {
            final EventCommand command = eventFactory.getEvent(event.getType());
            command.execute(context, event.getParameters());
        }
    }
}
