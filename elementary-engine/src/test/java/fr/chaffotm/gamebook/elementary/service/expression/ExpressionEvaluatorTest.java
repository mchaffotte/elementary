package fr.chaffotm.gamebook.elementary.service.expression;

import fr.chaffotm.gamebook.elementary.model.definition.Indication;
import fr.chaffotm.gamebook.elementary.model.definition.IndicationType;
import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mvel2.PropertyAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ExpressionEvaluatorTest {

    @Test
    @DisplayName("evaluateIndications should evaluate a single indication")
    public void evaluateIndicationsShouldEvaluateASingleIndication() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "1"));

        boolean hasClues = evaluator.evaluateIndications("clue.1", context);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate an indication which is not present")
    public void evaluateIndicationsShouldEvaluateAnIndicationWhichIsNotPresent() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "1"));

        boolean hasClues = evaluator.evaluateIndications("!clue.1", context);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateIndications should throw an exception with an unknown variable")
    public void evaluateIndicationsShouldThrowAnExceptionWithAnUnknownVariable() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.evaluateIndications("place.1", context))
                .withMessageContaining("could not access: place");
    }

    @Test
    @DisplayName("evaluateIndications should evaluate two indications with or operator")
    public void evaluateIndicationsShouldEvaluateTwoIndicationsWithOrOperator() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("clue.1 || clue.A", context);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate two indications with and operator")
    public void evaluateIndicationsShouldEvaluateTwoIndicationsWithAndOperator() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("clue.A && clue.1", context);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate with correct operator order")
    public void evaluateIndicationsShouldEvaluateWithCorrectOperatorOrder() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("clue.A || clue.1 && clue.C", context);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate with parenthesis")
    public void evaluateIndicationsShouldEvaluateWithParenthesis() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("(clue.A || clue.1) && clue.C", context);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateIndications should answer false when context is empty using default indication types")
    public void evaluateIndicationsShouldAnswerFalseWhenContextIsEmptyUsingDefaultIndicationTypes() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);

        boolean hasIndications = evaluator.evaluateIndications("clue.A || decision.45 || deduction.5 || event.4", context);

        assertThat(hasIndications).isFalse();
    }

}
