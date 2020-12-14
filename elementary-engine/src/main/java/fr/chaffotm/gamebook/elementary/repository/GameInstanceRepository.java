package fr.chaffotm.gamebook.elementary.repository;

import fr.chaffotm.gamebook.elementary.model.entity.instance.GameInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class GameInstanceRepository {

    private final EntityManager em;

    @Inject
    public GameInstanceRepository(final EntityManager em) {
        this.em = em;
    }

    public GameInstance getGame() {
        final TypedQuery<GameInstance> query = em.createNamedQuery("getGame", GameInstance.class);
        List<GameInstance> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public void removeGame() {
        final GameInstance game = getGame();
        if (game != null) {
            em.remove(game);
        }
    }

    public GameInstance save(final GameInstance game) {
       em.persist(game);
       return game;
    }
}
