package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.definition.SimpleActionDefinition;
import fr.chaffotm.gamebook.elementary.model.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Section;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.when;

@QuarkusTest
public class GameServiceTest {

    @Inject
    GameService service;

    @InjectMock
    StoryDefinitionService storyService;

    private StoryDefinition buildDefault() {
        final SectionDefinition prologue = new SectionDefinition();
        prologue.setParagraphs(List.of("Prologue 1", "Prologue 2"));
        prologue.setActions(List.of(new SimpleActionDefinition(2)));
        final SectionDefinition section2 = new SectionDefinition();
        section2.setId(2);
        section2.setParagraphs(List.of("Section 2"));
        section2.setActions(List.of(new SimpleActionDefinition(4)));

        final StoryDefinition definition = new StoryDefinition();
        definition.setName("Test");
        definition.setPrologue(prologue);
        definition.setSections(List.of(section2));
        return definition;
    }

    @Test
    @DisplayName("turnTo should throw an exception if the game is not started")
    public void turnToShouldThrowAnExceptionIfTheGameIsNotStarted() {
        service.removeGame();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(485))
                .withMessage("Cannot reach that section");
    }

    @Test
    @DisplayName("startGame should return the prologue")
    public void startGameShouldReturnThePrologue() {
        when(storyService.getStoryDefinition()).thenReturn(buildDefault());

        final Game game = service.startGame();

        Section section = game.getSection();
        assertThat(section.getActions())
                .containsExactly(new Action(2, null));
    }

    @Test
    @DisplayName("turnTo should throw an exception if section is not reachable")
    public void turnToShouldThrowAnExceptionIfSectionIsNotReachable() {
        when(storyService.getStoryDefinition()).thenReturn(buildDefault());
        service.startGame();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(48))
                .withMessage("Cannot reach that section");
    }

    @Test
    @DisplayName("turnTo should go to new section")
    public void turnToShouldGoToNewSection() {
        when(storyService.getStoryDefinition()).thenReturn(buildDefault());
        service.startGame();

        Game game = service.turnTo(2);

        Section section = game.getSection();
        assertThat(section.getActions())
                .containsExactly(new Action(4, null));
    }

    @Test
    @DisplayName("turnTo should throw an exception if section does not exist")
    public void turnToShouldThrowAnExceptionIfSectionDoesNotExist() {
        when(storyService.getStoryDefinition()).thenReturn(buildDefault());
        service.startGame();
        service.turnTo(2);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(4))
                .withMessage("Cannot reach that section");
    }

}
