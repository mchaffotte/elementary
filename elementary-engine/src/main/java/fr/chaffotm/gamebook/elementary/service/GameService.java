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
        final StoryDefinition story = storyService.getStoryDefinition();
        game = new GameInstance(story);
        final GameContext context = new GameContext(game.getContext());
        final SectionInstance instance = sectionService.evaluate(story.getPrologue(), context);
        game.setSection(instance);
        game.setContext(context);
        return GameMapper.map(game);
    }

    public void removeGame() {
        this.game = null;
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
        final StoryDefinition story = game.getStory();
        final SectionDefinition sectionDefinition = story.getSection(id);
        if (sectionDefinition == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final GameContext context = new GameContext(game.getContext());
        final Event event = optionalAction.get().getEvent();
        if (event != null) {
            event.execute(context);
        }
        final SectionInstance instance = sectionService.evaluate(sectionDefinition, context);
        game.setSection(instance);
        game.setContext(context);
        return GameMapper.map(game);
    }

    protected GameInstance getGame() {
        return game;
    }

}
