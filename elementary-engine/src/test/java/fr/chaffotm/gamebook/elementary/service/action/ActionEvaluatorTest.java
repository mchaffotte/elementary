package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.builder.ActionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.CharacterBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.ActionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
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
        final ActionEntity action = new ActionBuilder("If you want to enter in the cockpit", 125)
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
        final ActionEntity action = new ActionBuilder(
                new OptionBuilder(485)
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
        final ActionEntity action = new ActionBuilder(
                new OptionBuilder(485)
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
        final ActionEntity action = new ActionBuilder(
                new OptionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll()")
                .build();
        final GameContext context = new GameContext(die, new CharacterEntity());
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
        final ActionEntity action = new ActionBuilder(
                new OptionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll()")
                .build();
        final GameContext context = new GameContext(die, new CharacterEntity());
        final ActionEvaluator evaluator = new ActionEvaluator();
        final ActionInstance expected = new ActionInstance(12);
        when(die.roll()).thenReturn(7);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isEqualTo(expected);
     }

    @Test
    @DisplayName("toInstance should choose no option")
    public void toInstanceShouldChooseNoOption() {
        final ActionEntity action = new ActionBuilder(
                new OptionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll()")
                .build();
        final GameContext context = new GameContext(die, new CharacterEntity());
        final ActionEvaluator evaluator = new ActionEvaluator();
        when(die.roll()).thenReturn(13);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isNull();
    }

    @Test
    @DisplayName("toInstance should add the bonus and choose the second option")
    public void toInstanceShouldAddSKillAndChooseTheSecondOption() {
        final ActionEntity action = new ActionBuilder(
                new OptionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll() + skill.observation")
                .build();
        final CharacterEntity character = new CharacterBuilder("John")
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
        final ActionEntity action = new ActionBuilder(
                new OptionBuilder(458)
                        .expression("value <= 6").build())
                .option(new OptionBuilder(12)
                        .expression("value <= 12").build())
                .expression("die.roll() + skill.observation")
                .build();
        final GameContext context = new GameContext(die, new CharacterEntity());
        context.addIndication(new Indication(IndicationType.CLUE, "A"));
        final ActionEvaluator evaluator = new ActionEvaluator();
        when(die.roll()).thenReturn(6);

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.toInstance(action, context))
                .withCause(new IllegalStateException("Skill \"observation\" does not exist."));
    }

    //TODO event
}
