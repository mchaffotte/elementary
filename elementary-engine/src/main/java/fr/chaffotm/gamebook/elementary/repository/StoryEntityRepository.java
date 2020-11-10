package fr.chaffotm.gamebook.elementary.repository;

import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@ApplicationScoped
public class StoryEntityRepository {

    private final EntityManager em;

    @Inject
    public StoryEntityRepository(final EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    @Transactional
    public void populate() {
        final StoryEntity story = new StoryMaker().buildDefault();
        em.persist(story);
    }

    public StoryEntity getStory() {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<StoryEntity> criteriaQuery = criteriaBuilder.createQuery(StoryEntity.class);
        final Root<StoryEntity> rootQuery = criteriaQuery.from(StoryEntity.class);
        criteriaQuery.select(rootQuery);
        final TypedQuery<StoryEntity> query = em.createQuery(criteriaQuery);
        return query.getResultList().get(0);
    }

    public SectionEntity getSection(final StoryEntity story, final int reference) {
        final TypedQuery<SectionEntity> query = em.createNamedQuery("getSection", SectionEntity.class);
        query.setParameter("story", story);
        query.setParameter("reference", reference);
        return query.getSingleResult();
    }
}
