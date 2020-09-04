package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Event;
import fr.chaffotm.gamebook.elementary.model.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.model.mapper.GameMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Game;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class GameService {

    private final StoryDefinitionService storyService;

    private final SectionService sectionService;

    private GameInstance game;

    @Inject
    public GameService(final StoryDefinitionService storyService, final SectionService sectionService) {
        this.storyService = storyService;
        this.sectionService = sectionService;
    }

    public Game startGame() {
        if (game != null) {
            throw new IllegalStateException("Another game is already in progress");
        }
        final StoryDefinition story = storyService.getStoryDefinition();
        game = new GameInstance(story);
        final GameContext context = new GameContext(game.getContext());
        final SectionInstance instance = sectionService.evaluate(story.getPrologue(), context);
        game.setSection(instance);
        game.setContext(context);
        return getGame();
    }

    public boolean stopGame() {
        this.game = null;
        return true;
    }

    public Game turnTo(final int id) {
        if (game == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final SectionInstance section = game.getSection();
        final Optional<ActionInstance> optionalAction = section.getActions().stream()
                .filter(action -> action.getNextId() == id)
                .findFirst();
        if (optionalAction.isEmpty()) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final GameContext context = new GameContext(game.getContext());
        final Event event = optionalAction.get().getEvent();
        if (event != null) {
            event.execute(context);
        }
        final StoryDefinition story = game.getStory();
        final SectionDefinition definition = story.getSection(id);
        final SectionInstance instance = sectionService.evaluate(definition, context);
        game.setSection(instance);
        game.setContext(context);
        return getGame();
    }

    public Game getGame() {
        final GameInstance game = getGameInstance();
        if (game == null) {
            throw new IllegalStateException("Not found");
        }
        return GameMapper.map(game);
    }

    protected GameInstance getGameInstance() {
        return game;
    }

}
