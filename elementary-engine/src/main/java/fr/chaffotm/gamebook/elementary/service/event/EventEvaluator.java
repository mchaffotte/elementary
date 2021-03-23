package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import fr.chaffotm.gamebook.elementary.service.expression.ExpressionEvaluator;

public class EventEvaluator {

    private final ExpressionEvaluator evaluator = new ExpressionEvaluator();

    private final EventFactory eventFactory = new EventFactory();;

    public void execute(final EventDefinition event, final GameContext context) {
        final String expression = event.getExpression();
        if (evaluator.evaluateIndications(expression, context, null)) {
            final EventCommand command = eventFactory.getEvent(event.getType());
            command.execute(context, event.getParameters());
        }
    }
}
