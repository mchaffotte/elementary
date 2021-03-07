package fr.chaffotm.gamebook.elementary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.chaffotm.gamebook.elementary.model.builder.StoryContext;
import fr.chaffotm.gamebook.elementary.model.io.IOStory;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ApplicationScoped
public class StoryImporterService {

    private final IOService ioService;

    private final StoryContextMapper storyMapper;

    private final ObjectMapper mapper;

    private final String storyLocation;

    @Inject
    public StoryImporterService(final ObjectMapper mapper,
                                @ConfigProperty(name = "elementary.stories.location", defaultValue = " ") final String storyLocation) {
        this.mapper = mapper;
        this.storyLocation = storyLocation;
        this.ioService = new IOService();
        this.storyMapper = new StoryContextMapper();
    }

    public List<Path> getStoryPaths() throws IOException, URISyntaxException {
        if (storyLocation.isBlank()) {
            return List.of();
        }
        return ioService.getStoryPaths(storyLocation);
    }

    private IOStory getBuiltInStory(final Path path) throws IOException {
        final Path storyPath = path.resolve("story.json");
        try (InputStream inputStream = Files.newInputStream(storyPath)) {
            return mapper.readValue(inputStream, IOStory.class);
        }
    }

    public StoryContext getStoryContext(final Path path) throws IOException {
        final IOStory story = getBuiltInStory(path);
        return storyMapper.map(story);
    }
}
