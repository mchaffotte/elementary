package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.builder.*;
import fr.chaffotm.gamebook.elementary.model.entity.definition.*;
import fr.chaffotm.gamebook.elementary.model.entity.instance.*;
import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Section;
import fr.chaffotm.gamebook.elementary.repository.GameInstanceRepository;
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
    GameInstanceRepository gameInstanceRepository;

    @BeforeEach
    public void setUp() {
        service.stopGame();
    }

    private SectionDefinition buildSection2(final EventDefinition nextSectionEvent) {
        final SectionDefinition section2 = new SectionDefinition();
        section2.setReference(2);
        if (nextSectionEvent != null) {
            section2.setEvents(List.of(nextSectionEvent));
        }
        section2.setText("Section 2");
        section2.setActions(List.of(buildAction(4, "Go to", null), buildAction(0, "Or", null)));
        return section2;
    }

    private StoryDefinition buildStory(final EventDefinition actionEvent) {
        final SectionDefinition prologue = new SectionDefinition();
        prologue.setReference(0);
        prologue.setText("Prologue 1\nPrologue 2");
        prologue.setActions(List.of(buildAction(2, null, actionEvent)));

        final StoryDefinition definition = new StoryDefinition();
        definition.setName("Test");
        definition.setPrologue(prologue);
        return definition;
    }

    private ActionDefinition buildAction(int nextReference, String description, EventDefinition event) {
        final OptionDefinition option = new OptionDefinition();
        option.setNextReference(nextReference);
        option.setDescription(description);
        option.setEvent(event);
        final ActionDefinition action = new ActionDefinition();
        action.addOption(option);
        return action;
    }

    private GameInstance buildGame(final StoryDefinition story, final CharacterInstance character) {
        final GameInstance game= new GameInstance();
        game.setStory(story);
        game.setCharacter(character);
        return game;
    }

    private GameInstance buildGame(final StoryDefinition story, int nextId) {
        return buildGame(story, new ActionInstance(nextId, null, null));
    }

    private GameInstance buildGame(final StoryDefinition story, int nextId, final String clue) {
        return buildGame(story, new ActionInstance(nextId, null, buildClueEvent(clue)));
    }

    private GameInstance buildGame(final StoryDefinition story, ActionInstance action) {
        final SectionInstance section = new SectionInstance();
        section.setActions(List.of(action));
        final GameInstance game = new GameInstance();
        game.setStory(story);
        game.setCharacter(new CharacterInstance());
        game.setSection(section);
        return game;
    }

    private EventDefinition buildClueEvent(final String clue) {
        ParameterDefinition parameter = new ParameterDefinition();
        parameter.setName("clue");
        parameter.setValue(clue);
        EventDefinition event = new EventDefinition();
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
        when(storyService.getStoryDefinition(1)).thenReturn(buildStory(null));
        when(storyService.getCharacter(any())).thenReturn(new CharacterDefinition());

        final Game game = service.startGame(1);

        Section section = game.getSection();
        assertThat(section.getActions())
                .containsExactly(new Action(2, ""));
    }

    @Test
    @DisplayName("startGame should throw an exception if prologue event throws an exception")
    public void startGameShouldThrowAnExceptionIfPrologueEventThrowsAnException() {
        final StoryDefinition story = new StoryDefinitionBuilder("Once upon a time")
                .prologue(new SectionDefinitionBuilder(0)
                        .event(new EventDefinitionBuilder("add-indication")
                                .parameter("clue", "Q").build())
                        .event(new EventDefinitionBuilder("unknown").build())
                        .build())
                .build().getStory();
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        when(storyService.getCharacter(any())).thenReturn(new CharacterDefinition());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.startGame(1));
        verify(gameInstanceRepository, never()).save(any());
    }

    @Test
    @DisplayName("startGame should return an exception if another game is already in progress")
    public void startGameShouldThrowAnExceptionIfAnotherGameIsAlreadyInProgress() {
        final StoryDefinition story = buildStory(null);
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        when(gameInstanceRepository.getGame()).thenReturn(buildGame(story, new CharacterInstance()));

        assertThatIllegalStateException()
                .isThrownBy(() -> service.startGame(1))
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
        when(storyService.getStoryDefinition(1)).thenReturn(buildStory(null));
        when(storyService.getCharacter(any())).thenReturn(new CharacterDefinition());
        service.startGame(1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(48))
                .withMessage("Cannot reach that section");
    }

    @Test
    @DisplayName("turnTo should go to new section")
    public void turnToShouldGoToNewSection() {
        final StoryDefinition story = buildStory(null);
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        when(storyService.getSection(story, 2)).thenReturn(buildSection2(null));
        final GameInstance instance = buildGame(story, 2);
        when(gameInstanceRepository.getGame()).thenReturn(instance);

        Game game = service.turnTo(2);

        assertThat(game.getSection().getActions())
                .containsExactly(new Action(4, "Go to"), new Action(0, "Or"));
    }

    @Test
    @DisplayName("turnTo should update context with action event")
    public void turnToShouldUpdateContextWithActionEvent() {
        final StoryDefinition story = buildStory(buildClueEvent("Z"));
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        when(storyService.getSection(story, 2)).thenReturn(buildSection2(null));
        final GameInstance game = buildGame(story, 2, "Z");
        when(gameInstanceRepository.getGame()).thenReturn(game);

        service.turnTo(2);

        assertThat(game.getIndications())
                .contains(new IndicationInstance(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should throw an exception if action event throws an exception")
    @Disabled("Unable to reproduce till action has a single event or there is only one registered event command")
    public void turnToShouldThrowAnExceptionIfActionEventThrowsAnException() {
        final StoryDefinition story = new StoryDefinitionBuilder("Once upon a time")
                .character(new CharacterDefinition())
                .prologue(new SectionDefinitionBuilder(0)
                        .event(new EventDefinitionBuilder("add-indication")
                                .parameter("clue", "Q").build())
                        .action(new ActionDefinitionBuilder(
                                new OptionDefinitionBuilder(2)
                                        .event(new EventDefinitionBuilder("unknown").build()).build())
                                .build())
                        .build())
                .build().getStory();
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        final GameInstance game = buildGame(story, new ActionInstance(2, null, null));
        when(gameInstanceRepository.getGame()).thenReturn(game);

        assertThatIllegalStateException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(game.getIndications())
                .contains(new IndicationInstance(IndicationType.CLUE, "Q"));
    }

    @Test
    @DisplayName("turnTo should update context with next section event")
    public void turnToShouldUpdateContextWithNextSectionEvent() {
        final EventDefinition event = new EventDefinitionBuilder("add-indication")
                .parameter("clue", "Z").build();
        final StoryDefinition story = buildStory(null);
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        when(storyService.getSection(story, 2)).thenReturn(buildSection2(event));
        final GameInstance game = buildGame(story, 2);
        when(gameInstanceRepository.getGame()).thenReturn(game);

        service.turnTo(2);

        assertThat(game.getIndications())
                .contains(new IndicationInstance(IndicationType.CLUE, "Z"));
    }

    @Test
    @DisplayName("turnTo should throw an exception if next section event throws an exception")
    public void turnToShouldThrowAnExceptionIfNextSectionEventThrowsAnException() {
        final StoryDefinition story = new StoryDefinitionBuilder("Once upon a time")
                .character(new CharacterDefinition())
                .prologue(new SectionDefinitionBuilder(0)
                        .action(new ActionDefinitionBuilder(2).build())
                        .build())
                .section(new SectionDefinitionBuilder(2)
                        .event(new EventDefinitionBuilder("add-indication")
                                .parameter("clue", "Q").build())
                        .event(new EventDefinitionBuilder("unknown").build())
                        .build())
                .build().getStory();
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        final GameInstance game = buildGame(story, 2);
        when(gameInstanceRepository.getGame()).thenReturn(game);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.turnTo(2));
        assertThat(game.getIndications())
                .doesNotContain(new IndicationInstance(IndicationType.CLUE, "Q"));
    }

    @Test
    @DisplayName("turnTo should restart the game")
    public void turnToShouldRestartTheGame() {
        final StoryDefinition story = buildStory(null);
        when(storyService.getStoryDefinition(1)).thenReturn(story);
        final GameInstance instance = buildGame(story, 0);
        instance.addIndication(new IndicationInstance(IndicationType.CLUE, "A"));
        when(gameInstanceRepository.getGame()).thenReturn(instance);
        when(storyService.getCharacter(story)).thenReturn(new CharacterDefinition());

        Game game = service.turnTo(0);

        assertThat(game.getSection().getReference()).isEqualTo(0);
        assertThat(instance.getIndications()).isEmpty();
    }
}
