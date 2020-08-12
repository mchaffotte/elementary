package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ConditionActionDefinitionTest {

    @Test
    @DisplayName("toInstance should not choose the path because the clue has not yet been found")
    public void toInstanceShouldNotChooseThePathBecauseTheClueHasNotYetBeenFound() {
        final ClueCondition condition = new ClueCondition("A");
        final ConditionActionDefinition actionDefinition = new ConditionActionDefinition(condition, 485);
        final GameContext context = new GameContext(null, null);

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isNull();
    }

    @Test
    @DisplayName("toInstance should choose the path because the clue has been found")
    public void toInstanceShouldChooseThePathBecauseTheClueHasBeenFound() {
        final ClueCondition condition = new ClueCondition("A");
        final ConditionActionDefinition actionDefinition = new ConditionActionDefinition(condition, 485);
        final GameContext context = new GameContext(null, null);
        context.addClue("A");

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isEqualTo(new ActionInstance(485));
    }

}
