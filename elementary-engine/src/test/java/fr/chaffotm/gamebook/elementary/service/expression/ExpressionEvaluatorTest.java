package fr.chaffotm.gamebook.elementary.service.expression;

import fr.chaffotm.gamebook.elementary.model.builder.CharacterDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.Indication;
import fr.chaffotm.gamebook.elementary.model.instance.IndicationType;
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

        boolean hasClues = evaluator.evaluateIndications("clue.1", context, null);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate an indication which is not present")
    public void evaluateIndicationsShouldEvaluateAnIndicationWhichIsNotPresent() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "1"));

        boolean hasClues = evaluator.evaluateIndications("!clue.1", context, null);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateIndications should throw an exception with an unknown variable")
    public void evaluateIndicationsShouldThrowAnExceptionWithAnUnknownVariable() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.evaluateIndications("place.1", context, null))
                .withMessageContaining("unresolvable property or identifier: place");
    }

    @Test
    @DisplayName("evaluateIndications should evaluate two indications with or operator")
    public void evaluateIndicationsShouldEvaluateTwoIndicationsWithOrOperator() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("clue.1 || clue.A", context, null);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate two indications with and operator")
    public void evaluateIndicationsShouldEvaluateTwoIndicationsWithAndOperator() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("clue.A && clue.1", context, null);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate with correct operator order")
    public void evaluateIndicationsShouldEvaluateWithCorrectOperatorOrder() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("clue.A || clue.1 && clue.C", context, null);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateIndications should evaluate with parenthesis")
    public void evaluateIndicationsShouldEvaluateWithParenthesis() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        boolean hasClues = evaluator.evaluateIndications("(clue.A || clue.1) && clue.C", context, null);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateIndications should answer false when context is empty using default indication types")
    public void evaluateIndicationsShouldAnswerFalseWhenContextIsEmptyUsingDefaultIndicationTypes() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final GameContext context = new GameContext(new Die(12), null);

        boolean hasIndications = evaluator.evaluateIndications("clue.A || decision.45 || deduction.5 || event.4", context, null);

        assertThat(hasIndications).isFalse();
    }

    @Test
    @DisplayName("evaluateSkills should return the value of the skill")
    public void evaluateSkillsShouldReturnTheValueOfTheSkill() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final CharacterDefinition character = new CharacterDefinitionBuilder("John")
                .skill("observation", 1).build();
        final GameContext context = new GameContext(null, character);

        int value = evaluator.evaluateSkills("skill.observation", context);

        assertThat(value).isEqualTo(1);
    }

    @Test
    @DisplayName("evaluateSkills should throw an exception if skill does not exist")
    public void evaluateSkillsShouldThrowAnExceptionIfSkillDoesNotExist() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final CharacterDefinition character = new CharacterDefinitionBuilder("John")
                .skill("athletics", 1).build();
        final GameContext context = new GameContext(null, character);

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.evaluateSkills("skill.intuition", context))
                .withCause(new IllegalStateException("Skill \"intuition\" does not exist."));
    }

}
