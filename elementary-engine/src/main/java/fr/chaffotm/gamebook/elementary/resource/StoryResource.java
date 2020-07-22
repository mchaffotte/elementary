package fr.chaffotm.gamebook.elementary.resource;

import fr.chaffotm.gamebook.elementary.model.Story;
import fr.chaffotm.gamebook.elementary.service.StoryService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;

@GraphQLApi
public class StoryResource {

    private final StoryService service;

    @Inject
    public StoryResource(final StoryService service) {
        this.service = service;
    }

    @Query
    public Story getStory()  {
        return service.getStory();
    }

}
