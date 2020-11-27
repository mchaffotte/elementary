package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.mapper.StoryMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Story;
import fr.chaffotm.gamebook.elementary.repository.StoryDefinitionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class StoryService {

    private final StoryDefinitionRepository repository;

    @Inject
    public StoryService(final StoryDefinitionRepository repository) {
        this.repository = repository;
    }

    public Story getStory() {
        final StoryDefinition story = repository.getStory();
        return StoryMapper.map(story);
    }

    public StoryDefinition getStoryEntity() {
        return repository.getStory();
    }

    public SectionDefinition getSection(final StoryDefinition story, int reference) {
        return repository.getSection(story, reference);
    }

    public CharacterDefinition getCharacter(final StoryDefinition story) {
        return repository.getCharacter(story);
    }
}
