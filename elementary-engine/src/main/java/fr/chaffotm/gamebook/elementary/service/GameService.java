package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.EventEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.model.mapper.GameMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.repository.GameRepository;
import fr.chaffotm.gamebook.elementary.service.event.EventCommand;
import fr.chaffotm.gamebook.elementary.service.event.EventFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class GameService {

    private final StoryService storyService;

    private final SectionService sectionService;

    private final GameRepository gameRepository;

    private final EventFactory eventFactory;

    @Inject
    public GameService(final StoryService storyService, final SectionService sectionService, final GameRepository gameRepository) {
        this.storyService = storyService;
        this.sectionService = sectionService;
        this.gameRepository = gameRepository;
        eventFactory = new EventFactory();
    }

    public Game startGame() {
        if (gameRepository.getGame() != null) {
            throw new IllegalStateException("Another game is already in progress");
        }
        final StoryEntity story = storyService.getStoryEntity();
        final CharacterEntity character = storyService.getCharacter(story);
        final GameInstance game = new GameInstance(story, character);
        final GameContext context = new GameContext(game.getContext());
        return turnTo(game, null, story.getPrologue(), context);
    }

    public boolean stopGame() {
        gameRepository.removeGame();
        return true;
    }

    public Game turnTo(final int reference) {
        final GameInstance game = gameRepository.getGame();
        if (game == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final SectionInstance section = game.getSection();
        final ActionInstance instance = getActionInstance(section.getActions(), reference);
        final StoryEntity story = game.getStory();
        final SectionEntity prologue = story.getPrologue();
        final EventEntity event = instance.getEvent();
        if (reference == prologue.getReference()) {
            final CharacterEntity character = storyService.getCharacter(story);
            final GameContext context = new GameContext(game.getContext().getDie(), character);
            return turnTo(game, event, prologue, context);
        }
        final GameContext context = new GameContext(game.getContext());
        final SectionEntity next = storyService.getSection(story, reference);
        return turnTo(game, event, next, context);
    }

    private ActionInstance getActionInstance(final List<ActionInstance> actions, final int reference) {
        return actions.stream()
                .filter(action -> action.getNextReference() == reference)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot reach that section"));
    }

    private Game turnTo(final GameInstance game, final EventEntity event, final SectionEntity section, final GameContext context) {
        if (event != null) {
            final EventCommand command = eventFactory.getEvent(event.getType());
            command.execute(context, event.getParameters());
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
