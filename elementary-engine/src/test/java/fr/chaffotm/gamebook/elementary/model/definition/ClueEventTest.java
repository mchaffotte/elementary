package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClueEventTest {

    @Test
    @DisplayName("execute should add the clue in the context")
    public void executeShouldAddTheClueInTheContext() {
        final Event event = new ClueEvent("V");
        final GameContext context = new GameContext(null, null);

        event.execute(context);

        assertThat(context.hasClue("V")).isTrue();
    }

}