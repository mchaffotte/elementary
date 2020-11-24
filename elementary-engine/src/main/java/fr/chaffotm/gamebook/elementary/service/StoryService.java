package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;
import fr.chaffotm.gamebook.elementary.model.mapper.StoryMapper;
import fr.chaffotm.gamebook.elementary.model.resource.Story;
import fr.chaffotm.gamebook.elementary.repository.StoryEntityRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class StoryService {

    private final StoryEntityRepository repository;

    @Inject
    public StoryService(final StoryEntityRepository repository) {
        this.repository = repository;
    }

    public Story getStory() {
        final StoryEntity story = repository.getStory();
        return StoryMapper.map(story);
    }

    public StoryEntity getStoryEntity() {
        return repository.getStory();
    }

    public SectionEntity getSection(final StoryEntity story, int reference) {
        return repository.getSection(story, reference);
    }

    public CharacterEntity getCharacter(final StoryEntity story) {
        return repository.getCharacter(story);
    }
}
