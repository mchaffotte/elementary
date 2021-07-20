package fr.chaffotm.gamebook.elementary.service.action;

import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.CharacterDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.*;
import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mvel2.PropertyAccessException;

import static fr.chaffotm.gamebook.elementary.assertion.ActionInstanceAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionEvaluatorTest {

    private ActionEvaluator evaluator;

    @Mock
    private Die die;

    private GameInstance buildGame() {
        final GameInstance game = new GameInstance();
        game.setCharacter(new CharacterInstance());
        return game;
    }

    private GameInstance buildGame(CharacterDefinition character) {
        final GameInstance game = new GameInstance();
        game.setCharacter(new CharacterInstance(character));
        return game;
    }

    @BeforeEach
    public void setUp() {
        evaluator = new ActionEvaluator();
    }

    @Test
    @DisplayName("toInstance should create an instance with reference and description")
    public void toInstanceShouldCopyDefinitionToInstance() {
        final ActionDefinition action = new ActionDefinitionBuilder("If you want to enter in the cockpit", 125)
                .build();
        final GameContext context = new GameContext(new Die(12), new GameInstance());

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance)
                .hasNextReference(125)
                .hasDescription("If you want to enter in the cockpit")
                .hasNoEvent();
    }

    @Test
    @DisplayName("toInstance should not choose the path because the clue has not yet been found")
    public void toInstanceShouldNotChooseThePathBecauseTheClueHasNotYetBeenFound() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(485)
                        .expression("clue.A")
                        .build())
                .build();
        final GameContext context = new GameContext(null, buildGame());

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).isNull();
    }

    @Test
    @DisplayName("toInstance should choose the path because the clue has been found")
    public void toInstanceShouldChooseThePathBecauseTheClueHasBeenFound() {
        final ActionDefinition action = new ActionDefinitionBuilder(
                new OptionDefinitionBuilder(485)
                        .expression("clue.A")
                        .build())
                .build();
        final GameContext context = new GameContext(null, buildGame());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance)
                .hasNextReference(485)
                .hasNoDescription()
                .hasNoEvent();
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
        final GameContext context = new GameContext(die, buildGame());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        when(die.roll()).thenReturn(6);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance)
                .hasNextReference(458)
                .hasNoDescription()
                .hasNoEvent();
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
        final GameContext context = new GameContext(die, buildGame());
        when(die.roll()).thenReturn(7);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance).
                hasNextReference(12)
                .hasNoDescription()
                .hasNoEvent();
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
        final GameContext context = new GameContext(die, buildGame());
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
                .skill("observation", 1)
                .build();
        final GameContext context = new GameContext(die, buildGame(character));
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        when(die.roll()).thenReturn(6);

        final ActionInstance actionInstance = evaluator.toInstance(action, context);

        assertThat(actionInstance)
                .hasNextReference(12)
                .hasNoDescription()
                .hasNoEvent();
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
        final GameContext context = new GameContext(die, buildGame());
        context.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        when(die.roll()).thenReturn(6);

        assertThatExceptionOfType(PropertyAccessException.class)
                .isThrownBy(() -> evaluator.toInstance(action, context))
                .withCause(new IllegalStateException("Skill \"observation\" does not exist."));
    }

    //TODO event
}
