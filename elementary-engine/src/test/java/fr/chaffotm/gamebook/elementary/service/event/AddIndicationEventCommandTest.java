package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ParameterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationType;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class AddIndicationEventCommandTest {

    private ParameterDefinition buildParameter(String name, String value) {
        final ParameterDefinition parameter = new ParameterDefinition();
        parameter.setName(name);
        parameter.setValue(value);
        return parameter;
    }

    @Test
    @DisplayName("execute should add the clue in the context")
    public void executeShouldAddTheClueInTheContext() {
        final AddIndicationEventCommand command = new AddIndicationEventCommand();
        final Set<ParameterDefinition> parameters = Set.of(buildParameter("clue", "V"));
        final GameContext context = new GameContext(null, new GameInstance());

        command.execute(context, parameters);

        assertThat(context.getIndications()).contains(new IndicationInstance(IndicationType.CLUE, "V"));
    }

    @Test
    @DisplayName("execute should throw an exception if the parameter is unknown")
    public void executeShouldThrowAnExceptionIfTheParameterTypeIsUnknown() {
        final AddIndicationEventCommand command = new AddIndicationEventCommand();
        final Set<ParameterDefinition> parameters = Set.of(buildParameter("place", "R"));
        final GameContext context = new GameContext(null, null);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> command.execute(context, parameters))
                .withMessage("No enum constant fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationType.PLACE");
    }
}
