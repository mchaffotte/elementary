package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.CharacterDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.Indication;
import fr.chaffotm.gamebook.elementary.model.instance.IndicationType;
import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mvel2.PropertyAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionEvaluatorTest {

    @Mock
    private Die die;

    @Test
    @DisplayName("toInstance should create an instance with reference and description")
    public void toInstanceShouldCopyDefinitionToInstance() {
        final ActionDefinition action = new ActionDefinitionBuilder("If you want to enter in the cockpit", 125)
                .build();
        final GameContext context = new GameContext(new Die(12), null);
        final ActionEvaluator evaluator = new ActionEvaluator();
        final ActionInstance expected = new ActionInstance(125, "If you want to enter in the cockpit");

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isEqualTo(expected);
    }

    @Test
    @DisplayName("toInstance should not choose the path because the clue has not yet been found")
    public void toInstanceShouldNotChooseThePathBecauseTheClueHasNotYetBeenFound() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(485)
                        .expression("clue.A").build())
                .build();
        final GameContext context = new GameContext(null, null);
        final ActionEvaluator evaluator = new ActionEvaluator();

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isNull();
    }

    @Test
    @DisplayName("toInstance should choose the path because the clue has been found")
    public void toInstanceShouldChooseThePathBecauseTheClueHasBeenFound() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(485)
                        .expression("clue.A").build())
                .build();
        final GameContext context = new GameContext(null, null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));
        final ActionEvaluator evaluator = new ActionEvaluator();

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isEqualTo(new ActionInstance(485));
    }


    @Test
    @DisplayName("toInstance should choose the first option")
    public void toInstanceShouldChooseTheFirstOption() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionDefinitionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll()")
                .build();
        final GameContext context = new GameContext(die, new CharacterDefinition());
        context.addIndication(new Indication(IndicationType.CLUE, "A"));
        final ActionEvaluator evaluator = new ActionEvaluator();
        final ActionInstance expected = new ActionInstance(458);
        when(die.roll()).thenReturn(6);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isEqualTo(expected);
    }

    @Test
    @DisplayName("toInstance should choose the second option")
    public void toInstanceShouldChooseTheSecondOption() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionDefinitionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll()")
                .build();
        final GameContext context = new GameContext(die, new CharacterDefinition());
        final ActionEvaluator evaluator = new ActionEvaluator();
        final ActionInstance expected = new ActionInstance(12);
        when(die.roll()).thenReturn(7);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isEqualTo(expected);
     }

    @Test
    @DisplayName("toInstance should choose no option")
    public void toInstanceShouldChooseNoOption() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionDefinitionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll()")
                .build();
        final GameContext context = new GameContext(die, new CharacterDefinition());
        final ActionEvaluator evaluator = new ActionEvaluator();
        when(die.roll()).thenReturn(13);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isNull();
    }

    @Test
    @DisplayName("toInstance should add the bonus and choose the second option")
    public void toInstanceShouldAddSKillAndChooseTheSecondOption() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionDefinitionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll() + skill.observation")
                .build();
        final CharacterDefinition character = new CharacterDefinitionBuilder("John")
                .skill("observation", 1).build();
        final GameContext context = new GameContext(die, character);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));
        final ActionEvaluator evaluator = new ActionEvaluator();
        final ActionInstance expected = new ActionInstance(12);
        when(die.roll()).thenReturn(6);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isEqualTo(expected);
    }

    @Test
    @DisplayName("toInstance should add the bonus and choose the second option")
    public void toInstanceShouldAddBonusAndChooseTheSecondOption() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionDefinitionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll() + skill.observation")
                .build();
        final GameContext context = new GameContext(die, new CharacterDefinition());
        context.addIndication(new Indication(IndicationType.CLUE, "A"));
        final ActionEvaluator evaluator = new ActionEvaluator();
        when(die.roll()).thenReturn(6);

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.toInstance(action, context))
                .withCause(new IllegalStateException("Skill \"observation\" does not exist."));
    }

    //TODO event
}
