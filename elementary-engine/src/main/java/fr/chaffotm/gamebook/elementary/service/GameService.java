package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.*;
import fr.chaffotm.gamebook.elementary.model.entity.instance.*;
import fr.chaffotm.gamebook.elementary.model.mapper.GameMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Indication;
import fr.chaffotm.gamebook.elementary.repository.GameInstanceRepository;
import fr.chaffotm.gamebook.elementary.service.action.ActionEvaluator;
import fr.chaffotm.gamebook.elementary.service.event.EventEvaluator;

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

    private final ActionEvaluator evaluator;

    private final EventEvaluator eventEvaluator;

    @Inject
    public GameService(final StoryService storyService, final SectionService sectionService, final GameInstanceRepository gameInstanceRepository) {
        this.storyService = storyService;
        this.sectionService = sectionService;
        this.gameInstanceRepository = gameInstanceRepository;
        this.evaluator = new ActionEvaluator();
        eventEvaluator = new EventEvaluator();
    }

    public Game getGame() {
        final GameInstance game = gameInstanceRepository.getGame();
        if (game == null) {
            throw new IllegalStateException("Not found");
        }
        return GameMapper.map(game);
    }

    public Game startGame(final long storyId) {
        final GameInstance game = initialize(storyId);
        final StoryDefinition story = game.getStory();
        final GameInstance next = turnTo(game, null, story.getPrologue());
        gameInstanceRepository.save(next);
        return GameMapper.map(next);
    }

    public Game startFrom(final long storyId, final int reference, final List<Indication> indications) {
        final GameInstance game = initialize(storyId);
        if (indications != null) {
            for (Indication indication : indications) {
                game.addIndication(new IndicationInstance(IndicationType.valueOf(indication.getName().toUpperCase()), indication.getValue()));
            }
        }
        final SectionDefinition next = storyService.getSection(game.getStory(), reference);
        final GameInstance nextGame = turnTo(game, null, next);
        gameInstanceRepository.save(nextGame);
        return GameMapper.map(nextGame);
    }

    public boolean stopGame() {
        gameInstanceRepository.removeGame();
        return true;
    }

    private GameInstance initialize(final long storyId) {
        if (gameInstanceRepository.getGame() != null) {
            throw new IllegalStateException("Another game is already in progress");
        }
        final StoryDefinition story = storyService.getStoryDefinition(storyId);
        final CharacterDefinition character = storyService.getCharacter(story);
        final GameInstance game = new GameInstance();
        game.setStory(story);
        game.setCharacter(new CharacterInstance(character));
        return game;
    }

    public Game turnTo(final int index, final String answer) {
        final GameInstance game = gameInstanceRepository.getGame();
        if (game == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final SectionInstance section = game.getSection();
        if (index < 0 || section.getActions().size() <= index) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final ActionInstance instance = section.getActions().get(index);
        if (instance.getDefinition() == null) {
            return turnTo(game, instance.getEvent(), instance.getNextReference());
        }
        final OptionDefinition option = evaluator.getOption(instance.getDefinition(), answer);
        return turnTo(game, option.getEvent(), option.getNextReference());
    }

    private Game turnTo(final GameInstance game, final EventDefinition event, int nextReference) {
        final StoryDefinition story = game.getStory();
        final SectionDefinition prologue = story.getPrologue();
        if (nextReference == prologue.getReference()) {
            final CharacterDefinition character = storyService.getCharacter(story);
            game.setCharacter(new CharacterInstance(character));
            game.getIndications().clear();
            final GameInstance nextGame = turnTo(game, event, prologue);
            return GameMapper.map(nextGame);
        }
        final SectionDefinition next = storyService.getSection(story, nextReference);
        final GameInstance nextGame = turnTo(game, event, next);
        return GameMapper.map(nextGame);
    }

    private GameInstance turnTo(final GameInstance game, final EventDefinition event, final SectionDefinition section) {
        final GameContext context = new GameContext(new Die(12), game);
        if (event != null) {
            eventEvaluator.execute(event, context);
        }
        final SectionInstance instance = sectionService.evaluate(section, context);
        game.setSection(instance);
        return game;
    }
}
