package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.repository.StoryDefinitionRepository;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestScoped
@Transactional
public class ImageService {

    private final StoryDefinitionRepository storyRepository;

    public ImageService(final StoryDefinitionRepository storyRepository) {
        this.storyRepository = storyRepository;

    }
    public InputStream getImageStream(final long storyId, final String imageName) throws IOException {
        final StoryDefinition story = storyRepository.getStory(storyId);
        final String location = story.getLocation();
        final Path path = Paths.get(location, "images", imageName);
        return Files.newInputStream(path);
    }
}
