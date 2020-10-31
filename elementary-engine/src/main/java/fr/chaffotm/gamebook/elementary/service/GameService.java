package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Event;
import fr.chaffotm.gamebook.elementary.model.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.model.mapper.GameMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.repository.GameRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GameService {

    private final StoryDefinitionService storyService;

    private final SectionService sectionService;

    private final GameRepository gameRepository;

    @Inject
    public GameService(final StoryDefinitionService storyService, final SectionService sectionService, final GameRepository gameRepository) {
        this.storyService = storyService;
        this.sectionService = sectionService;
        this.gameRepository = gameRepository;
    }

    public Game startGame() {
        if (gameRepository.getGame() != null) {
            throw new IllegalStateException("Another game is already in progress");
        }
        final StoryDefinition story = storyService.getStoryDefinition();
        final GameInstance game = new GameInstance(story);
        final GameContext context = new GameContext(game.getContext());
        return turnTo(game, null, story.getPrologue(), context);
    }

    public boolean stopGame() {
        gameRepository.removeGame();
        return true;
    }

    public Game turnTo(final int id) {
        final GameInstance game = gameRepository.getGame();
        if (game == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final SectionInstance section = game.getSection();
        final ActionInstance instance = getActionInstance(section.getActions(), id);
        final StoryDefinition story = game.getStory();
        final SectionDefinition prologue = story.getPrologue();
        final Event event = instance.getEvent();
        if (id == prologue.getId()) {
            final GameContext context = new GameContext(game.getContext().getDie(), story.getCharacter());
            return turnTo(game, event, prologue, context);
        }
        final GameContext context = new GameContext(game.getContext());
        return turnTo(game, event, story.getSection(id), context);
    }

    private ActionInstance getActionInstance(final List<ActionInstance> actions, final int id) {
        return actions.stream()
                .filter(action -> action.getNextId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot reach that section"));
    }

    private Game turnTo(final GameInstance game, final Event event, final SectionDefinition section, final GameContext context) {
        if (event != null) {
            event.execute(context);
        }
        final SectionInstance instance = sectionService.evaluate(section, context);
        game.setSection(instance);
        game.setContext(context);
        gameRepository.save(game);
        return GameMapper.map(game);
    }

    public Game getGame() {
        final GameInstance game = gameRepository.getGame();
        if (game == null) {
            throw new IllegalStateException("Not found");
        }
        return GameMapper.map(game);
    }

}
