package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.assertion.SectionInstanceAssert;
import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.EventDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.SectionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.SectionInstance;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.*;
import static  fr.chaffotm.gamebook.elementary.assertion.SectionInstanceAssert.assertThat;

@QuarkusTest
public class SectionServiceTest {

    @Inject
    SectionService service;

    @Test
    @DisplayName("evaluate should construct a section instance")
    public void evaluateShouldConstructASectionInstance() {
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .event(new EventDefinitionBuilder("add-indication").parameter("clue", "L").build())
                .action(new ActionDefinitionBuilder(new OptionDefinitionBuilder(4).expression("false").build()).build())
                .action(new ActionDefinitionBuilder(2).build())
                .build();

        SectionInstance instance = service.evaluate(definition, new GameContext(null, new GameInstance()));

        assertThat(instance)
                .hasReference(7)
                .hasText("Lorem ipsum dolor sit amet")
                .hasActions(new ActionInstance(2, null, null));
    }

    @Test
    @DisplayName("evaluate should throw an exception if no action is possible while definition contains actions")
    public void evaluateShouldThrowAnExceptionIfNoActionIsPossibleWhileDefinitionContainsActions() {
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .action(new ActionDefinitionBuilder(new OptionDefinitionBuilder(4).expression("false").build()).build())
                .build();

        assertThatIllegalStateException()
                .isThrownBy(() -> service.evaluate(definition, new GameContext(null, new GameInstance())))
                .withMessage("No action is possible");
    }

    @Test
    @DisplayName("evaluate should construct a section instance without actions")
    public void evaluateShouldConstructASectionInstanceWithoutActions() {
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .build();

        SectionInstance instance = service.evaluate(definition, new GameContext(null, null));

        assertThat(instance)
                .hasReference(7)
                .hasText("Lorem ipsum dolor sit amet")
                .hasNoActions();
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
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .action(new ActionDefinitionBuilder("Lorem", 475).build())
                .action(new ActionDefinitionBuilder("Ipsum", 2).build())
                .build();

       SectionInstance instance = service.evaluate(definition, new GameContext(null, new GameInstance()));

       assertThat(instance)
               .hasReference(7)
               .hasText("Lorem ipsum dolor sit amet")
               .hasActions(
                       new ActionInstance(475, "Lorem", null),
                       new ActionInstance(2, "Ipsum", null));
    }

    @Test
    @DisplayName("evaluate should choose first action without description")
    public void evaluateShouldChooseFirstActionWithoutDescription() {
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .action(new ActionDefinitionBuilder(475).build())
                .action(new ActionDefinitionBuilder("Ipsum", 2).build())
                .build();

        SectionInstance instance = service.evaluate(definition, new GameContext(null, new GameInstance()));

        assertThat(instance)
                .hasReference(7)
                .hasText("Lorem ipsum dolor sit amet")
                .hasActions(new ActionInstance(475, null, null));
    }

    @Test
    @DisplayName("evaluate should stop the action selection with an action without a description")
    public void evaluateShouldStopTheActionSelectionWithAnActionWithoutADescription() {
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .action(new ActionDefinitionBuilder("Ipsum", 475).build())
                .action(new ActionDefinitionBuilder( 2).build())
                .build();

        SectionInstance instance = service.evaluate(definition, new GameContext(null, new GameInstance()));

        assertThat(instance)
                .hasReference(7)
                .hasText("Lorem ipsum dolor sit amet")
                .hasActions(new ActionInstance(475, "Ipsum", null));
    }

    @Test
    @DisplayName("evaluate should choose only first action")
    public void evaluateShouldChooseOnlyFirstAction() {
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .action(new ActionDefinitionBuilder(475).build())
                .action(new ActionDefinitionBuilder(2).build())
                .build();

        SectionInstance instance = service.evaluate(definition, new GameContext(null, new GameInstance()));

        SectionInstanceAssert.assertThat(instance)
                .hasReference(7)
                .hasText("Lorem ipsum dolor sit amet")
                .hasActions(new ActionInstance(475, null, null));
    }

    @Test
    @DisplayName("evaluate should choose only first action with blank description")
    public void evaluateShouldChooseOnlyFirstActionWithBlankDescription() {
        final SectionDefinition definition = new SectionDefinitionBuilder(7)
                .paragraph("Lorem ipsum dolor sit amet")
                .action(new ActionDefinitionBuilder("                       ",475).build())
                .action(new ActionDefinitionBuilder(2).build())
                .build();

        SectionInstance instance = service.evaluate(definition, new GameContext(null, new GameInstance()));

        SectionInstanceAssert.assertThat(instance)
                .hasReference(7)
                .hasText("Lorem ipsum dolor sit amet")
                .hasActions(new ActionInstance(475, null, null));
    }
}
