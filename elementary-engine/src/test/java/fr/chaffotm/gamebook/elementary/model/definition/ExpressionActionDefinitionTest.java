package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ExpressionActionDefinitionTest {

    @Test
    @DisplayName("toInstance should not choose the path because the clue has not yet been found")
    public void toInstanceShouldNotChooseThePathBecauseTheClueHasNotYetBeenFound() {
        final ExpressionActionDefinition actionDefinition = new ExpressionActionDefinition("clue.A", 485);
        final GameContext context = new GameContext(null, null);

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isNull();
    }

    @Test
    @DisplayName("toInstance should choose the path because the clue has been found")
    public void toInstanceShouldChooseThePathBecauseTheClueHasBeenFound() {
        final ExpressionActionDefinition actionDefinition = new ExpressionActionDefinition("clue.A", 485);
        final GameContext context = new GameContext(null, null);
        context.addIndication(new Indication(IndicationType.CLUE, "A"));

        ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isEqualTo(new ActionInstance(485));
    }

}
