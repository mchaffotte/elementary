package fr.chaffotm.gamebook.elementary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.chaffotm.gamebook.elementary.model.builder.StoryContext;
import fr.chaffotm.gamebook.elementary.model.io.IOStory;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class StoryImporterService {

    private final IOService ioService;

    private final StoryContextMapper storyMapper;

    private final ObjectMapper mapper;

    private final String storyLocation;

    @Inject
    public StoryImporterService(final ObjectMapper mapper,
                                @ConfigProperty(name = "elementary.default-story.location", defaultValue = " ") final String storyLocation) {
        this.mapper = mapper;
        this.storyLocation =storyLocation;
        this.ioService = new IOService();
        this.storyMapper = new StoryContextMapper();
    }

    public StoryContext getDefaultStoryContext() throws IOException {
        if (storyLocation.isBlank()) {
            return null;
        }
        final IOStory story = getBuiltInStory();
        return storyMapper.map(story);
    }

    private IOStory getBuiltInStory() throws IOException {
        try (InputStream inputStream = ioService.getInputStream(storyLocation)) {
            return mapper.readValue(inputStream, IOStory.class);
        }
    }
}
