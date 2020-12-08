package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.mapper.StoryMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Story;
import fr.chaffotm.gamebook.elementary.repository.StoryDefinitionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StoryService {

    private final StoryDefinitionRepository repository;

    @Inject
    public StoryService(final StoryDefinitionRepository repository) {
        this.repository = repository;
    }

    public List<Story> getStories(final int offset, final int limit) {
        final List<StoryDefinition> stories = repository.getStories(offset, limit);
        return StoryMapper.map(stories);
    }

    public Story getStory(final long id) {
        final StoryDefinition story = getStoryDefinition(id);
        return StoryMapper.map(story);
    }

    public StoryDefinition getStoryDefinition(final long id) {
        return repository.getStory(id);
    }

    public SectionDefinition getSection(final StoryDefinition story, int reference) {
        return repository.getSection(story, reference);
    }

    public CharacterDefinition getCharacter(final StoryDefinition story) {
        return repository.getCharacter(story);
    }
}
