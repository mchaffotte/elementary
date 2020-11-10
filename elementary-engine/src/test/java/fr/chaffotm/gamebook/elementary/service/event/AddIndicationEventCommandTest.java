package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.ParameterEntity;
import fr.chaffotm.gamebook.elementary.model.instance.Indication;
import fr.chaffotm.gamebook.elementary.model.instance.IndicationType;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class AddIndicationEventCommandTest {

    @Test
    @DisplayName("execute should add the clue in the context")
    public void executeShouldAddTheClueInTheContext() {
        final AddIndicationEventCommand command = new AddIndicationEventCommand();
        final ParameterEntity parameter = new ParameterEntity();
        parameter.setName("clue");
        parameter.setValue("V");
        final Set<ParameterEntity> parameters = Set.of(parameter);
        final GameContext context = new GameContext(null, null);

        command.execute(context, parameters);

        assertThat(context.getIndications()).contains(new Indication(IndicationType.CLUE, "V"));
    }

    @Test
    @DisplayName("execute should add the clue in the context")
    public void executeShould2AddTheClueInTheContext() {
        final AddIndicationEventCommand command = new AddIndicationEventCommand();
        final ParameterEntity parameter = new ParameterEntity();
        parameter.setName("place");
        parameter.setValue("V");
        final Set<ParameterEntity> parameters = Set.of(parameter);
        final GameContext context = new GameContext(null, null);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> command.execute(context, parameters))
                .withMessage("No enum constant fr.chaffotm.gamebook.elementary.model.instance.IndicationType.PLACE");
    }
}
