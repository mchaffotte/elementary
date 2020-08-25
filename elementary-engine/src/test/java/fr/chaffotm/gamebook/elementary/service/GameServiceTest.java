package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.*;
import fr.chaffotm.gamebook.elementary.model.definition.Character;
import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Section;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
public class GameServiceTest {

    @Inject
    GameService service;

    @InjectMock
    StoryDefinitionService storyService;

    @BeforeEach
    public void setUp() {
        service.stopGame();
    }

    private StoryDefinition buildStory(final Event sectionEvent, final Event actionEvent, final Event nextSectionEvent) {
        final SectionDefinition prologue = new SectionDefinition();
        if (sectionEvent != null) {
            prologue.setEvents(List.of(sectionEvent));
        }
        prologue.setParagraphs(List.of("Prologue 1", "Prologue 2"));
        prologue.setActions(List.of(new SimpleActionDefinition(2, null, actionEvent)));
        final SectionDefinition section2 = new SectionDefinition();
        section2.setId(2);
        if (nextSectionEvent != null) {
            section2.setEvents(List.of(nextSectionEvent));
        }
        section2.setParagraphs(List.of("Section 2"));
        section2.setActions(List.of(new SimpleActionDefinition(4)));

        final StoryDefinition definition = new StoryDefinition();
        definition.setName("Test");
        definition.setPrologue(prologue);
        definition.setSections(List.of(section2));
        definition.setCharacter(new Character());
        return definition;
    }

    @Test
    @DisplayName("turnTo should throw an exception if the game is not started")
    public void turnToShouldThrowAnExceptionIfTheGameIsNotStarted() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(485))
                .withMessage("Cannot reach that section");
    }

    @Test
    @DisplayName("startGame should return the prologue")
    public void startGameShouldReturnThePrologue() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(null, null, null));

        final Game game = service.startGame();

        Section section = game.getSection();
        assertThat(section.getActions())
                .containsExactly(new Action(2, null));
    }

    @Test
    @DisplayName("startGame should return an exception if another game is already in progress")
    public void startGameShouldThrowAnExceptionIfAnotherGameIsAlreadyInProgress() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(null, null, null));
        service.startGame();

        assertThatIllegalStateException()
                .isThrownBy(() -> service.startGame())
                .withMessage("Another game is already in progress");
    }

    @Test
    @DisplayName("turnTo should throw an exception if section is not reachable")
    public void turnToShouldThrowAnExceptionIfSectionIsNotReachable() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(null, null, null));
        service.startGame();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(48))
                .withMessage("Cannot reach that section");
    }

    @Test
    @DisplayName("turnTo should go to new section")
    public void turnToShouldGoToNewSection() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(null, null, null));
        service.startGame();

        Game game = service.turnTo(2);

        Section section = game.getSection();
        assertThat(section.getActions())
                .containsExactly(new Action(4, null));
    }

    @Test
    @DisplayName("turnTo should throw an exception if section does not exist")
    public void turnToShouldThrowAnExceptionIfSectionDoesNotExist() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(null, null, null));
        service.startGame();
        service.turnTo(2);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(4))
                .withMessage("Cannot reach that section");
    }

    @Test
    @DisplayName("startGame should throw an exception if section is not reachable")
    public void startGameShouldThrowAnExceptionIfSectionIsNotReachable2() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(new AddClueAndFailEvent("Z"), null, null));

        assertThatIllegalStateException()
                .isThrownBy(() -> service.startGame());
        assertThat(service.getGameInstance().getContext().hasClue("Z")).isFalse();
    }

    @Test
    @DisplayName("turnTo should throw an exception if action event throws an exception")
    public void turnToShouldThrowAnExceptionIfActionEventThrowsAnException() {
    when(storyService.getStoryDefinition()).thenReturn(buildStory(null, new AddClueAndFailEvent("Z"), null));
        service.startGame();

        assertThatIllegalStateException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(service.getGameInstance().getContext().hasClue("Z")).isFalse();
    }

    @Test
    @DisplayName("turnTo should throw an exception if section is not reachable")
    public void turnToShouldThrowAnExceptionIfNextSectionEventThrowsAnException() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(null, null, new AddClueAndFailEvent("Z")));
        service.startGame();

        assertThatIllegalStateException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(service.getGameInstance().getContext().hasClue("Z")).isFalse();
    }

    @Test
    @DisplayName("getGame should throw an exception if no game is started")
    public void getGameShouldThrowAnExceptionIfNoGameIsStarted() {
        assertThatIllegalStateException()
                .isThrownBy(() -> service.getGame())
                .withMessage("Not found");
    }

}
