package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestActionDefinitionTest {

    @Mock
    private Die die;

    @Test
    @DisplayName("toInstance should choose the first option")
    public void toInstanceShouldChooseTheFirstOption() {
        final List<Option> options = List.of(new Option(6, 458), new Option(12, 486));
        final TestActionDefinition actionDefinition = new TestActionDefinition(options);
        final GameContext context = new GameContext(die, null);
        final ActionInstance expected = new ActionInstance(458);
        when(die.roll()).thenReturn(6);

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isEqualTo(expected);
    }

    @Test
    @DisplayName("toInstance should choose the second option")
    public void toInstanceShouldChooseTheSecondOption() {
        final List<Option> options = List.of(new Option(6, 458), new Option(12, 486));
        final TestActionDefinition actionDefinition = new TestActionDefinition(options);
        final GameContext context = new GameContext(die, null);
        final ActionInstance expected = new ActionInstance(486);
        when(die.roll()).thenReturn(7);

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isEqualTo(expected);
    }

    @Test
    @DisplayName("toInstance should choose no option")
    public void toInstanceShouldChooseNoOption() {
        final List<Option> options = List.of(new Option(6, 458), new Option(12, 486));
        final TestActionDefinition actionDefinition = new TestActionDefinition(options);
        final GameContext context = new GameContext(die, null);
        when(die.roll()).thenReturn(13);

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isNull();
    }

    @Test
    @DisplayName("toInstance should add the bonus and choose the second option")
    public void toInstanceShouldAddBonusAndChooseTheSecondOption() {
        final List<Option> options = List.of(new Option(6, 458), new Option(12, 486));
        final TestActionDefinition actionDefinition = new TestActionDefinition(options, new SkillBonus("observation"));
        final Character character = new Character();
        character.setSkills(Set.of(new Skill("observation", 1)));
        final GameContext context = new GameContext(die, character);
        final ActionInstance expected = new ActionInstance(486);
        when(die.roll()).thenReturn(6);

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isEqualTo(expected);
    }

}
