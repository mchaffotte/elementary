package fr.chaffotm.gamebook.elementary.service.expression;

import fr.chaffotm.gamebook.elementary.model.builder.CharacterDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationType;
import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mvel2.PropertyAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ExpressionEvaluatorTest {

    private ExpressionEvaluator evaluator;

    @BeforeEach
    public void setUp() {
        evaluator = new ExpressionEvaluator();
    }

    @Test
    @DisplayName("evaluateToBoolean should evaluate a single indication")
    public void evaluateToBooleanShouldEvaluateASingleIndication() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "1"));
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        boolean hasClues = evaluator.evaluateToBoolean("clue.1", variableBuilder);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateToBoolean should evaluate an indication which is not present")
    public void evaluateToBooleanShouldEvaluateAnIndicationWhichIsNotPresent() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "1"));
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        boolean hasClues = evaluator.evaluateToBoolean("!clue.1", variableBuilder);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateToBoolean should throw an exception with an unknown variable")
    public void evaluateToBooleanShouldThrowAnExceptionWithAnUnknownVariable() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.evaluateToBoolean("place.1", variableBuilder))
                .withMessageContaining("unresolvable property or identifier: place");
    }

    @Test
    @DisplayName("evaluateToBoolean should evaluate two indications with or operator")
    public void evaluateToBooleanShouldEvaluateTwoIndicationsWithOrOperator() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        boolean hasClues = evaluator.evaluateToBoolean("clue.1 || clue.A", variableBuilder);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateToBoolean should evaluate two indications with and operator")
    public void evaluateToBooleanShouldEvaluateTwoIndicationsWithAndOperator() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        boolean hasClues = evaluator.evaluateToBoolean("clue.A && clue.1", variableBuilder);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateToBoolean should evaluate with correct operator order")
    public void evaluateToBooleanShouldEvaluateWithCorrectOperatorOrder() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        boolean hasClues = evaluator.evaluateToBoolean("clue.A || clue.1 && clue.C", variableBuilder);

        assertThat(hasClues).isTrue();
    }

    @Test
    @DisplayName("evaluateToBoolean should evaluate with parenthesis")
    public void evaluateToBooleanShouldEvaluateWithParenthesis() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        boolean hasClues = evaluator.evaluateToBoolean("(clue.A || clue.1) && clue.C", variableBuilder);

        assertThat(hasClues).isFalse();
    }

    @Test
    @DisplayName("evaluateToBoolean should answer false when context is empty using default indication types")
    public void evaluateToBooleanShouldAnswerFalseWhenContextIsEmptyUsingDefaultIndicationTypes() {
        final GameContext context = new GameContext(new Die(12), new GameInstance());
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.indications();

        boolean hasIndications = evaluator.evaluateToBoolean("clue.A || decision.45 || deduction.5 || event.4", variableBuilder);

        assertThat(hasIndications).isFalse();
    }

    @Test
    @DisplayName("evaluateToBoolean should evaluate the possibility to pay")
    public void evaluateToBooleanShouldEvaluateThePossibilityToPay() {
        final CharacterInstance character = new CharacterInstance();
        character.setMoney(6);
        GameInstance game = new GameInstance();
        game.setCharacter(character);
        final GameContext context = new GameContext(new Die(12), game);
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.money();

        boolean canPay = evaluator.evaluateToBoolean("character.canPay(\"£0 0s 7d\")", variableBuilder);

        assertThat(canPay).isFalse();
    }

    @Test
    @DisplayName("evaluate should return the value of the skill")
    public void evaluateShouldReturnTheValueOfTheSkill() {
        final CharacterDefinition character = new CharacterDefinitionBuilder("John")
                .skill("observation", 1)
                .build();
        final GameInstance game = new GameInstance();
        game.setCharacter(new CharacterInstance(character));
        final GameContext context = new GameContext(null, game);
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.skills();

        Object value = evaluator.evaluate("skill.observation", variableBuilder);

        assertThat(value).isEqualTo(1);
    }

    @Test
    @DisplayName("evaluate should return null if the expression is null")
    public void evaluateShouldReturnNullIfExpressionIsNull() {
        final GameInstance game = new GameInstance();
        game.setCharacter(new CharacterInstance());
        final GameContext context = new GameContext(null, game);
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.skills();

        Object value = evaluator.evaluate(null, variableBuilder);

        assertThat(value).isNull();
    }

    @Test
    @DisplayName("evaluate should return null if expression is empty")
    public void evaluateShouldReturnNullIfExpressionIsEmpty() {
        final GameInstance game = new GameInstance();
        game.setCharacter(new CharacterInstance());
        final GameContext context = new GameContext(null, game);
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.skills();

        Object value = evaluator.evaluate("  ", variableBuilder);

        assertThat(value).isNull();
    }

    @Test
    @DisplayName("evaluate should throw an exception if skill does not exist")
    public void evaluateShouldThrowAnExceptionIfSkillDoesNotExist() {
        final CharacterDefinition character = new CharacterDefinitionBuilder("John")
                .skill("athletics", 1)
                .build();
        final GameInstance game = new GameInstance();
        game.setCharacter(new CharacterInstance(character));
        final GameContext context = new GameContext(null, game);
        final VariableBuilder variableBuilder = new VariableBuilder(context);
        variableBuilder.skills();

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.evaluate("skill.intuition", variableBuilder))
                .withCause(new IllegalStateException("Skill \"intuition\" does not exist."));
    }

}
