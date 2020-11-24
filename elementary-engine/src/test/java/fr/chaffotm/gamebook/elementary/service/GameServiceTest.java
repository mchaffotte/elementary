package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.builder.*;
import fr.chaffotm.gamebook.elementary.model.entity.*;
import fr.chaffotm.gamebook.elementary.model.instance.*;
import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Section;
import fr.chaffotm.gamebook.elementary.repository.GameRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class GameServiceTest {

    @Inject
    GameService service;

    @InjectMock
    StoryService storyService;

    @InjectMock
    GameRepository gameRepository;

    @BeforeEach
    public void setUp() {
        service.stopGame();
    }

    private SectionEntity buildSection2(final EventEntity nextSectionEvent) {
        final SectionEntity section2 = new SectionEntity();
        section2.setReference(2);
        if (nextSectionEvent != null) {
            section2.setEvents(List.of(nextSectionEvent));
        }
        section2.setParagraphs(List.of("Section 2"));
        section2.setActions(List.of(buildAction(4, null, null), buildAction(0, null, null)));
        return section2;
    }

    private StoryEntity buildStory(final EventEntity actionEvent) {
        final SectionEntity prologue = new SectionEntity();
        prologue.setReference(0);
        prologue.setParagraphs(List.of("Prologue 1", "Prologue 2"));
        prologue.setActions(List.of(buildAction(2, null, actionEvent)));

        final StoryEntity definition = new StoryEntity();
        definition.setName("Test");
        definition.setPrologue(prologue);
        return definition;
    }

    private ActionEntity buildAction(int nextReference, String description, EventEntity event) {
        final OptionEntity option = new OptionEntity();
        option.setNextReference(nextReference);
        option.setDescription(description);
        option.setEvent(event);
        final ActionEntity action = new ActionEntity();
        action.addOption(option);
        return action;
    }

    private GameInstance buildGame(final StoryEntity story, int nextId) {
        return buildGame(story, new ActionInstance(nextId));
    }

    private GameInstance buildGame(final StoryEntity story, int nextId, final String clue) {
        return buildGame(story, new ActionInstance(nextId, null, buildClueEvent(clue)));
    }

    private GameInstance buildGame(final StoryEntity story, ActionInstance action) {
        final SectionInstance section = new SectionInstance();
        section.setActions(List.of(action));
        final GameInstance game = new GameInstance(story, new CharacterEntity());
        game.setSection(section);
        return game;
    }

    private EventEntity buildClueEvent(final String clue) {
        ParameterEntity parameter = new ParameterEntity();
        parameter.setName("clue");
        parameter.setValue(clue);
        EventEntity event = new EventEntity();
        event.setType("add-indication");
        event.setParameters(Set.of(parameter));
        return event;
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
        when(storyService.getStoryEntity()).thenReturn(buildStory(null));
        when(storyService.getCharacter(any())).thenReturn(new CharacterEntity());

        final Game game = service.startGame();

        Section section = game.getSection();
        assertThat(section.getActions())
                .containsExactly(new Action(2, null));
    }

    @Test
    @DisplayName("startGame should throw an exception if prologue event throws an exception")
    public void startGameShouldThrowAnExceptionIfPrologueEventThrowsAnException() {
        final StoryEntity story = new StoryBuilder("Once upon a time")
                .prologue(new SectionBuilder(0)
                        .event(new EventBuilder("add-indication")
                                .parameter("clue", "Q").build())
                        .event(new EventBuilder("unknown").build())
                        .build())
                .build().getStory();
        when(storyService.getStoryEntity()).thenReturn(story);
        when(storyService.getCharacter(any())).thenReturn(new CharacterEntity());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.startGame());
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("startGame should return an exception if another game is already in progress")
    public void startGameShouldThrowAnExceptionIfAnotherGameIsAlreadyInProgress() {
        final StoryEntity story = buildStory(null);
        when(storyService.getStoryEntity()).thenReturn(story);
        when(gameRepository.getGame()).thenReturn(new GameInstance(story, new CharacterEntity()));

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
        when(storyService.getStoryEntity()).thenReturn(buildStory(null));
        when(storyService.getCharacter(any())).thenReturn(new CharacterEntity());
        service.startGame();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(48))
                .withMessage("Cannot reach that section");
    }

    @Test
    @DisplayName("turnTo should go to new section")
    public void turnToShouldGoToNewSection() {
        final StoryEntity story = buildStory(null);
        when(storyService.getStoryEntity()).thenReturn(story);
        when(storyService.getSection(story, 2)).thenReturn(buildSection2(null));
        final GameInstance instance = buildGame(story, 2);
        when(gameRepository.getGame()).thenReturn(instance);

        Game game = service.turnTo(2);

        assertThat(game.getSection().getActions())
                .containsExactly(new Action(4, null), new Action(0, null));
    }

    @Test
    @DisplayName("turnTo should update context with action event")
    public void turnToShouldUpdateContextWithActionEvent() {
        final StoryEntity story = buildStory(buildClueEvent("Z"));
        when(storyService.getStoryEntity()).thenReturn(story);
        when(storyService.getSection(story, 2)).thenReturn(buildSection2(null));
        final GameInstance game = buildGame(story, 2, "Z");
        when(gameRepository.getGame()).thenReturn(game);

        service.turnTo(2);

        assertThat(game.getContext().getIndications())
                .contains(new Indication(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should throw an exception if action event throws an exception")
    @Disabled("Unable to reproduce till action has a single event or there is only one registered event command")
    public void turnToShouldThrowAnExceptionIfActionEventThrowsAnException() {
        final StoryEntity story = new StoryBuilder("Once upon a time")
                .character(new CharacterEntity())
                .prologue(new SectionBuilder(0)
                        .event(new EventBuilder("add-indication")
                                .parameter("clue", "Q").build())
                        .action(new ActionBuilder(
                                new OptionBuilder(2)
                                        .event(new EventBuilder("unknown").build()).build())
                                .build())
                        .build())
                .build().getStory();
        when(storyService.getStoryEntity()).thenReturn(story);
        final GameInstance game = buildGame(story, new ActionInstance(2, null, null));
        when(gameRepository.getGame()).thenReturn(game);

        assertThatIllegalStateException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(gameRepository.getGame().getContext().getIndications())
                .doesNotContain(new Indication(IndicationType.CLUE, "Q"));
    }

    @Test
    @DisplayName("turnTo should update context with next section event")
    public void turnToShouldUpdateContextWithNextSectionEvent() {
        final EventEntity event = new EventBuilder("add-indication")
                .parameter("clue", "Z").build();
        final StoryEntity story = buildStory(null);
        when(storyService.getStoryEntity()).thenReturn(story);
        when(storyService.getSection(story, 2)).thenReturn(buildSection2(event));
        final GameInstance game = buildGame(story, 2);
        when(gameRepository.getGame()).thenReturn(game);

        service.turnTo(2);

        assertThat(game.getContext().getIndications())
                .contains(new Indication(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should throw an exception if next section event throws an exception")
    public void turnToShouldThrowAnExceptionIfNextSectionEventThrowsAnException() {
        final StoryEntity story = new StoryBuilder("Once upon a time")
                .character(new CharacterEntity())
                .prologue(new SectionBuilder(0)
                        .action(new ActionBuilder(2).build())
                        .build())
                .section(new SectionBuilder(2)
                        .event(new EventBuilder("add-indication")
                                .parameter("clue", "Q").build())
                        .event(new EventBuilder("unknown").build())
                        .build())
                .build().getStory();
        when(storyService.getStoryEntity()).thenReturn(story);
        final GameInstance game = buildGame(story, 2);
        when(gameRepository.getGame()).thenReturn(game);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(game.getContext().getIndications())
                .doesNotContain(new Indication(IndicationType.CLUE, "Q"));
    }

    @Test
    @DisplayName("turnTo should restart the game")
    public void turnToShouldRestartTheGame() {
        final StoryEntity story = buildStory(null);
        when(storyService.getStoryEntity()).thenReturn(story);
        final GameContext context = new GameContext(new Die(12), new CharacterEntity());
        context.addIndication(new Indication(IndicationType.CLUE, "A"));
        final GameInstance instance = buildGame(story, 0);
        instance.setContext(context);
        when(gameRepository.getGame()).thenReturn(instance);

        Game game = service.turnTo(0);

        assertThat(game.getSection().getId()).isEqualTo(0);
        assertThat(instance.getContext().getIndications()).isEmpty();
    }

}
