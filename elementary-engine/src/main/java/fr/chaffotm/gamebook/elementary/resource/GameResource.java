package fr.chaffotm.gamebook.elementary.resource;

import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.service.GameService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;

@GraphQLApi
public class GameResource {

    private final GameService service;

    @Inject
    public GameResource(final GameService service) {
        this.service = service;
    }

    @Query
    public Game getGame() {
        return service.getGame();
    }

    @Mutation
    public Game startGame(final long storyId) {
        return service.startGame(storyId);
    }

    @Mutation
    public Game turnTo(final int nextReference) {
        return service.turnTo(nextReference);
    }

    @Mutation
    public boolean stopGame() {
        return service.stopGame();
    }
}
