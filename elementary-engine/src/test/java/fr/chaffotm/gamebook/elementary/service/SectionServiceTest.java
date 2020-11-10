package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.builder.ActionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.EventBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.SectionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.ActionSelection;
import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;
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
        final SectionEntity definition = new SectionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .event(new EventBuilder("add-indication").parameter("clue", "L").build())
                .action(new ActionBuilder(new OptionBuilder(4).expression("false").build()).build())
                .action(new ActionBuilder(2).build())
                .build();
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
        final SectionEntity definition = new SectionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .action(new ActionBuilder(new OptionBuilder(4).expression("false").build()).build())
                .build();

        assertThatIllegalStateException()
                .isThrownBy(() -> service.evaluate(definition, new GameContext(null, null)))
                .withMessage("No action is possible");
    }

    @Test
    @DisplayName("evaluate should construct a section instance without actions")
    public void evaluateShouldConstructASectionInstanceWithoutActions() {
        final SectionEntity definition = new SectionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .build();
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

    @Test
    @DisplayName("evaluate should choose only first action")
    public void evaluateShouldChooseAllActions() {
        final SectionEntity definition = new SectionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .selection((ActionSelection.ALL))
                .action(new ActionBuilder(475).build())
                .action(new ActionBuilder(2).build())
                .build();
        final SectionInstance expected = new SectionInstance();
        expected.setId(7);
        expected.setParagraphs(List.of("Lorem ipsum dolor sit amet"));
        expected.setActions(List.of(new ActionInstance(475), new ActionInstance(2)));

       SectionInstance instance = service.evaluate(definition, new GameContext(null, null));

        assertThat(instance).isEqualTo(expected);
    }

    @Test
    @DisplayName("evaluate should choose only first action")
    public void evaluateShouldChooseOnlyFirstAction() {
        final SectionEntity definition = new SectionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .selection((ActionSelection.FIRST))
                .action(new ActionBuilder(475).build())
                .action(new ActionBuilder(2).build())
                .build();
        final SectionInstance expected = new SectionInstance();
        expected.setId(7);
        expected.setParagraphs(List.of("Lorem ipsum dolor sit amet"));
        expected.setActions(List.of(new ActionInstance(475)));

        SectionInstance instance = service.evaluate(definition, new GameContext(null, null));

        assertThat(instance).isEqualTo(expected);
    }

}
