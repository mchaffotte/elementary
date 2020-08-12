package fr.chaffotm.gamebook.elementary.service;

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
        final SectionInstance instance = sectionService.evaluate(story.getPrologue(), game.getContext());
        game.setSection(instance);
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
        final Optional<ActionInstance> nextSection = section.getActions().stream()
                .filter(action -> action.getNextId() == id)
                .findFirst();
        if (nextSection.isEmpty()) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final StoryDefinition story = game.getStory();
        final SectionDefinition sectionDefinition = story.getSection(id);
        if (sectionDefinition == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final SectionInstance instance = sectionService.evaluate(sectionDefinition, game.getContext());
        game.setSection(instance);
        return GameMapper.map(game);
    }
}
