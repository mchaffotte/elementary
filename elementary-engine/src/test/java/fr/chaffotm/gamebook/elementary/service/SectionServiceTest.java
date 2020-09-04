package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.ClueEvent;
import fr.chaffotm.gamebook.elementary.model.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.definition.SimpleActionDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.SectionInstance;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@QuarkusTest
public class SectionServiceTest {

    @Inject
    SectionService service;

    @Test
    @DisplayName("evaluate should construct a section instance")
    public void evaluateShouldConstructASectionInstance() {
        final SectionDefinition definition = new SectionDefinition();
        definition.setId(7);
        definition.setParagraphs(List.of("Lorem ipsum dolor sit amet"));
        definition.setEvents(List.of(new ClueEvent("L")));
        definition.setActions(List.of(new NoAction(), new SimpleActionDefinition(2)));
        final SectionInstance expected = new SectionInstance();
        expected.setId(7);
        expected.setParagraphs(List.of("Lorem ipsum dolor sit amet"));
        expected.setActions(List.of(new ActionInstance(2)));

        SectionInstance instance = service.evaluate(definition, new GameContext(null, null));

        assertThat(instance).isEqualTo(expected);
    }

    @Test
    @DisplayName("evaluate should throw an exception if no action is possible while definition contains actions")
    public void evaluateShouldThrowAnExceptionIfNoActionIsPossibleWhileDefinitionContainsActions() {
        final SectionDefinition definition = new SectionDefinition();
        definition.setId(7);
        definition.setParagraphs(List.of("Lorem ipsum dolor sit amet"));
        definition.setActions(List.of(new NoAction()));

        assertThatIllegalStateException()
                .isThrownBy(() -> service.evaluate(definition, new GameContext(null, null)))
                .withMessage("No action is possible");
    }

    @Test
    @DisplayName("evaluate should construct a section instance without actions")
    public void evaluateShouldConstructASectionInstanceWithoutActions() {
        final SectionDefinition definition = new SectionDefinition();
        definition.setId(7);
        definition.setParagraphs(List.of("Lorem ipsum dolor sit amet"));
        definition.setActions(List.of());
        final SectionInstance expected = new SectionInstance();
        expected.setId(7);
        expected.setParagraphs(List.of("Lorem ipsum dolor sit amet"));
        expected.setActions(List.of());

        SectionInstance instance = service.evaluate(definition, new GameContext(null, null));

        assertThat(instance).isEqualTo(expected);
    }

    @Test
    @DisplayName("turnTo should throw an exception if definition is null")
    public void evaluateShouldThrowAnExceptionIfDefinitionIsNull() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.evaluate(null, new GameContext(null, null)))
                .withMessage("Section does not exist");
    }

}
