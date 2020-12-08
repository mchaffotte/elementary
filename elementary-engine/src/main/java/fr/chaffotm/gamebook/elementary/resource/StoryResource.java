package fr.chaffotm.gamebook.elementary.resource;

import fr.chaffotm.gamebook.elementary.model.resource.Story;
import fr.chaffotm.gamebook.elementary.service.StoryService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import java.util.List;

@GraphQLApi
public class StoryResource {

    private final StoryService service;

    @Inject
    public StoryResource(final StoryService service) {
        this.service = service;
    }

    @Query
    public List<Story> getStories(final int offset, final int limit) {
        return service.getStories(offset, limit);
    }

    @Query
    public Story getStory(final long storyId) {
        return service.getStory(storyId);
    }
}
