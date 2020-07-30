package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.mapper.SectionMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Section;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GameService {

    private final StoryDefinitionService storyService;

    @Inject
    public GameService(final StoryDefinitionService storyService) {
        this.storyService = storyService;
    }

    public Game startStory() {
        final StoryDefinition story = storyService.getStoryDefinition();
        final Section section = SectionMapper.map(story.getPrologue());
        final Game game = new Game();
        game.setSection(section);
        return game;
    }

    public Game turnTo(final int id) {
        final StoryDefinition story = storyService.getStoryDefinition();
        SectionDefinition sectionDefinition = story.getSection(id);
        if (sectionDefinition == null) {
            throw new IllegalArgumentException("Cannot reach that section");
        }
        final Section section = SectionMapper.map(sectionDefinition);
        final Game game = new Game();
        game.setSection(section);
        return game;
    }
}
