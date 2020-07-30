package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.StoryDefinition;
import fr.chaffotm.gamebook.elementary.repository.StoryDefinitionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class StoryDefinitionService {

    private final StoryDefinitionRepository repository;

    @Inject
    public StoryDefinitionService(final StoryDefinitionRepository repository) {
        this.repository = repository;
    }

    public StoryDefinition getStoryDefinition() {
        return repository.getStoryDefinition();
    }

}
