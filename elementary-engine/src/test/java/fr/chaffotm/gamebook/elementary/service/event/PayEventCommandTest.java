package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ParameterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PayEventCommandTest {

    private ParameterDefinition buildParameter(String name, String value) {
        final ParameterDefinition parameter = new ParameterDefinition();
        parameter.setName(name);
        parameter.setValue(value);
        return parameter;
    }

    @Test
    public void test() {
        final CharacterInstance character = new CharacterInstance();
        character.setMoney(240);
        final EventCommand command = new PayEventCommand();
        final Set<ParameterDefinition> parameters = Set.of(buildParameter("amount", "£0 1s 0d"));
        GameInstance gameInstance = new GameInstance();
        gameInstance.setCharacter(character);
        final GameContext context = new GameContext(null, gameInstance);

        command.execute(context, parameters);

        assertThat(character.getMoney()).isEqualTo(228);
    }

    @Test
    public void testPence() {
        final CharacterInstance character = new CharacterInstance();
        character.setMoney(240);
        final EventCommand command = new PayEventCommand();
        final Set<ParameterDefinition> parameters = Set.of(buildParameter("amount", "£0 0s 1d"));
        GameInstance gameInstance = new GameInstance();
        gameInstance.setCharacter(character);
        final GameContext context = new GameContext(null, gameInstance);

        command.execute(context, parameters);

        assertThat(character.getMoney()).isEqualTo(239);
    }
}
