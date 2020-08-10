package fr.chaffotm.gamebook.elementary.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
        final GameContext context = new GameContext(die);
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
        final GameContext context = new GameContext(die);
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
        final GameContext context = new GameContext(die);
        when(die.roll()).thenReturn(13);

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isNull();
    }
}
