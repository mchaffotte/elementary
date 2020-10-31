package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Character;
import fr.chaffotm.gamebook.elementary.model.definition.*;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Section;
import fr.chaffotm.gamebook.elementary.repository.GameRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class GameServiceTest {

    @Inject
    GameService service;

    @InjectMock
    StoryDefinitionService storyService;

    @InjectMock
    GameRepository gameRepository;

    @BeforeEach
    public void setUp() {
        service.stopGame();
    }

    private StoryDefinition buildStory(final Event sectionEvent, final Event actionEvent, final Event nextSectionEvent) {
        final SectionDefinition prologue = new SectionDefinition();
        prologue.setId(0);
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
        section2.setActions(List.of(new SimpleActionDefinition(4), new SimpleActionDefinition(0)));

        final StoryDefinition definition = new StoryDefinition();
        definition.setName("Test");
        definition.setPrologue(prologue);
        definition.setSections(List.of(section2));
        definition.setCharacter(new Character());
        return definition;
    }

    private GameInstance buildGame(final StoryDefinition story, int nextId) {
        return buildGame(story, new ActionInstance(nextId));
    }

    private GameInstance buildGame(final StoryDefinition story, int nextId, final String clue) {
        return buildGame(story, new ActionInstance(nextId, null, buildClueEvent(clue)));
    }

    private GameInstance buildGame(final StoryDefinition story, ActionInstance action) {
        final SectionInstance section = new SectionInstance();
        section.setActions(List.of(action));
        final GameInstance game = new GameInstance(story);
        game.setSection(section);
        return game;
    }

    private Event buildClueEvent(final String clue) {
        return new IndicationEvent(new Indication(IndicationType.CLUE, clue));
    }

    @Test
    @DisplayName("getGame should throw an exception if no game is started")
    public void getGameShouldThrowAnExceptionIfNoGameIsStarted() {
        assertThatIllegalStateException()
                .isThrownBy(() -> service.getGame())
                .withMessage("Not found");
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
    @DisplayName("startGame should throw an exception if prologue event throws an exception")
    public void startGameShouldThrowAnExceptionIfPrologueEventThrowsAnException() {
        when(storyService.getStoryDefinition()).thenReturn(buildStory(new AddClueAndFailEvent("Z"), null, null));

        assertThatIllegalStateException()
                .isThrownBy(() -> service.startGame());
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("startGame should return an exception if another game is already in progress")
    public void startGameShouldThrowAnExceptionIfAnotherGameIsAlreadyInProgress() {
        final StoryDefinition story = buildStory(null, null, null);
        when(storyService.getStoryDefinition()).thenReturn(story);
        when(gameRepository.getGame()).thenReturn(new GameInstance(story));

        assertThatIllegalStateException()
                .isThrownBy(() -> service.startGame())
                .withMessage("Another game is already in progress");
    }

    @Test
    @DisplayName("turnTo should throw an exception if the game is not started")
    public void turnToShouldThrowAnExceptionIfTheGameIsNotStarted() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(485))
                .withMessage("Cannot reach that section");
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
        final StoryDefinition story = buildStory(null, null, null);
        when(storyService.getStoryDefinition()).thenReturn(story);
        final GameInstance instance = buildGame(story, 2);
        when(gameRepository.getGame()).thenReturn(instance);

        Game game = service.turnTo(2);

        assertThat(game.getSection().getActions())
                .containsExactly(new Action(4, null), new Action(0, null));
    }

    @Test
    @DisplayName("turnTo should update context with action event")
    public void turnToShouldUpdateContextWithActionEvent() {
        final StoryDefinition story = buildStory(null, buildClueEvent("Z"), null);
        when(storyService.getStoryDefinition()).thenReturn(story);
        final GameInstance game = buildGame(story, 2, "Z");
        when(gameRepository.getGame()).thenReturn(game);

        service.turnTo(2);

        assertThat(game.getContext().getIndications())
                .contains(new Indication(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should throw an exception if action event throws an exception")
    public void turnToShouldThrowAnExceptionIfActionEventThrowsAnException() {
        final StoryDefinition story = buildStory(null, new AddClueAndFailEvent("Z"), null);
        when(storyService.getStoryDefinition()).thenReturn(story);
        final GameInstance game = buildGame(story, new ActionInstance(2, null, new AddClueAndFailEvent("Z")));
        when(gameRepository.getGame()).thenReturn(game);

        assertThatIllegalStateException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(gameRepository.getGame().getContext().getIndications())
                .doesNotContain(new Indication(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should update context with next section event")
    public void turnToShouldUpdateContextWithNextSectionEvent() {
        final StoryDefinition story = buildStory(null, null, new IndicationEvent(new Indication(IndicationType.CLUE, "Z")));
        when(storyService.getStoryDefinition()).thenReturn(story);
        final GameInstance game = buildGame(story, 2);
        when(gameRepository.getGame()).thenReturn(game);

        service.turnTo(2);

        assertThat(game.getContext().getIndications())
                .contains(new Indication(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should throw an exception if next section event throws an exception")
    public void turnToShouldThrowAnExceptionIfNextSectionEventThrowsAnException() {
        final StoryDefinition story = buildStory(null, null, new AddClueAndFailEvent("Z"));
        when(storyService.getStoryDefinition()).thenReturn(story);
        final GameInstance game = buildGame(story, 2);
        when(gameRepository.getGame()).thenReturn(game);

        assertThatIllegalStateException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(game.getContext().getIndications())
                .doesNotContain(new Indication(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should restart the game")
    public void turnToShouldRestartTheGame() {
        final StoryDefinition story = buildStory(null, null, null);
        when(storyService.getStoryDefinition()).thenReturn(story);
        final GameContext context = new GameContext(new Die(12), new Character());
        context.addIndication(new Indication(IndicationType.CLUE, "A"));
        final GameInstance instance = buildGame(story, 0);
        instance.setContext(context);
        when(gameRepository.getGame()).thenReturn(instance);

        Game game = service.turnTo(0);

        assertThat(game.getSection().getId()).isEqualTo(0);
        assertThat(instance.getContext().getIndications()).isEmpty();
    }

}
