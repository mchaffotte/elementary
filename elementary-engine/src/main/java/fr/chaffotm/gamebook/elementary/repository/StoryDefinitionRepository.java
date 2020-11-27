package fr.chaffotm.gamebook.elementary.repository;

import fr.chaffotm.gamebook.elementary.model.builder.StoryContext;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.service.StoryMaker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StoryDefinitionRepository {

    private final EntityManager em;

    @Inject
    public StoryDefinitionRepository(final EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    @Transactional
    public void populate() {
        final StoryContext context = new StoryMaker().buildDefault();
        final StoryDefinition story = context.getStory();
        final List<SectionDefinition> sections = context.getSections();
        em.persist(story);
        em.persist(context.getCharacter());
        for (SectionDefinition section : sections) {
            section.setStory(story);
            em.persist(section);
        }
    }

    public StoryDefinition getStory() {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<StoryDefinition> criteriaQuery = criteriaBuilder.createQuery(StoryDefinition.class);
        final Root<StoryDefinition> rootQuery = criteriaQuery.from(StoryDefinition.class);
        criteriaQuery.select(rootQuery);
        final TypedQuery<StoryDefinition> query = em.createQuery(criteriaQuery);
        return query.getResultList().get(0);
    }

    public SectionDefinition getSection(final StoryDefinition story, final int reference) {
        final TypedQuery<SectionDefinition> query = em.createNamedQuery("getSection", SectionDefinition.class);
        query.setParameter("story", story);
        query.setParameter("reference", reference);
        return query.getSingleResult();
    }

    public CharacterDefinition getCharacter(StoryDefinition story) {
        return em.find(CharacterDefinition.class, story.getId());
    }

}
