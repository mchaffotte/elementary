package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.model.mapper.GameMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.repository.GameInstanceRepository;
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

    private final GameInstanceRepository gameInstanceRepository;

    private final EventFactory eventFactory;

    @Inject
    public GameService(final StoryService storyService, final SectionService sectionService, final GameInstanceRepository gameInstanceRepository) {
        this.storyService = storyService;
        this.sectionService = sectionService;
        this.gameInstanceRepository = gameInstanceRepository;
        eventFactory = new EventFactory();
    }

    public Game startGame(final long storyId) {
        if (gameInstanceRepository.getGame() != null) {
            throw new IllegalStateException("Another game is already in progress");
        }
        final StoryDefinition story = storyService.getStoryDefinition(storyId);
        final CharacterDefinition character = storyService.getCharacter(story);
        final GameInstance game = new GameInstance();
        game.setStory(story);
        game.setCharacter(new CharacterInstance(character));
        final GameInstance next = turnTo(game, null, story.getPrologue());
        gameInstanceRepository.save(next);
        return GameMapper.map(next);
    }

    public boolean stopGame() {
        gameInstanceRepository.removeGame();
        return true;
    }

    public Game turnTo(final int reference) {
        final GameInstance game = gameInstanceRepository.getGame();
        if (game == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final SectionInstance section = game.getSection();
        final ActionInstance instance = getActionInstance(section.getActions(), reference);
        final StoryDefinition story = game.getStory();
        final SectionDefinition prologue = story.getPrologue();
        final EventDefinition event = instance.getEvent();
        if (reference == prologue.getReference()) {
            final CharacterDefinition character = storyService.getCharacter(story);
            game.setCharacter(new CharacterInstance(character));
            game.getIndications().clear();
            final GameInstance nextGame = turnTo(game, event, prologue);
            return GameMapper.map(nextGame);
        }
        final SectionDefinition next = storyService.getSection(story, reference);
        final GameInstance nextGame = turnTo(game, event, next);
        return GameMapper.map(nextGame);
    }

    private ActionInstance getActionInstance(final List<ActionInstance> actions, final int reference) {
        return actions.stream()
                .filter(action -> action.getNextReference() == reference)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot reach that section"));
    }

    private GameInstance turnTo(final GameInstance game, final EventDefinition event, final SectionDefinition section) {
        final GameContext context = new GameContext(new Die(12), game);
        if (event != null) {
            final EventCommand command = eventFactory.getEvent(event.getType());
            command.execute(context, event.getParameters());
        }
        final SectionInstance instance = sectionService.evaluate(section, context);
        game.setSection(instance);
        return game;
    }

    public Game getGame() {
        final GameInstance game = gameInstanceRepository.getGame();
        if (game == null) {
            throw new IllegalStateException("Not found");
        }
        return GameMapper.map(game);
    }

}
