package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.StoryDefinition;
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
        final StoryDefinition story = repository.getStoryDefinition();
        return StoryMapper.map(story);
    }

}
