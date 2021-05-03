package fr.chaffotm.gamebook.elementary.resource;

import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Indication;
import fr.chaffotm.gamebook.elementary.service.GameService;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import java.util.List;

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
    public Game startFrom(final long storyId, final int nextReference, final List<Indication> indications) {
        return service.startFrom(storyId, nextReference, indications);
    }

    @Mutation
    public boolean stopGame() {
        return service.stopGame();
    }
}
