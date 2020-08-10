package fr.chaffotm.gamebook.elementary.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleActionDefinitionTest {

    @Test
    @DisplayName("toInstance should copy definition to instance")
    public void toInstanceShouldCopyDefinitionToInstance() {
        final ActionDefinition actionDefinition = new SimpleActionDefinition(125, "If you want to enter in the cockpit");
        final GameContext context = new GameContext(new Die(12));
        final ActionInstance expected = new ActionInstance(125, "If you want to enter in the cockpit");

        final ActionInstance actionInstance = actionDefinition.toInstance(context);

        assertThat(actionInstance).isEqualTo(expected);
    }

}
