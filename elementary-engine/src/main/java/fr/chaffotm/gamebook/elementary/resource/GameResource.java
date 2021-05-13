package fr.chaffotm.gamebook.elementary.resource;

import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Indication;
import fr.chaffotm.gamebook.elementary.service.GameService;

import org.eclipse.microprofile.graphql.*;

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
    public Game startGame(@NonNull final long storyId) {
        return service.startGame(storyId);
    }

    @Mutation
    public Game turnTo(@NonNull final int index, @DefaultValue("") final String answer) {
        return service.turnTo(index, answer);
    }

    @Mutation
    public Game startFrom(@NonNull final long storyId, @NonNull final int reference, final List<Indication> indications) {
        return service.startFrom(storyId, reference, indications);
    }

    @Mutation
    public boolean stopGame() {
        return service.stopGame();
    }
}
