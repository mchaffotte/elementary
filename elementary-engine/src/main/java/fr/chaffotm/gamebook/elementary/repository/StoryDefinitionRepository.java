package fr.chaffotm.gamebook.elementary.repository;

import fr.chaffotm.gamebook.elementary.model.builder.StoryContext;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.service.StoryImporterService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

@ApplicationScoped
public class StoryDefinitionRepository {

    private final EntityManager em;

    private final StoryImporterService importer;

    @Inject
    public StoryDefinitionRepository(final EntityManager em, StoryImporterService importer) {
        this.em = em;
        this.importer = importer;
    }

    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        populate();
    }

    @Transactional
    public void populate() throws IOException, URISyntaxException {
        final List<Path> storyPaths = importer.getStoryPaths();
        for (Path storyPath : storyPaths) {
            final StoryContext context = importer.getStoryContext(storyPath);
            final StoryDefinition story = context.getStory();
            final List<SectionDefinition> sections = context.getSections();
            em.persist(story);
            em.persist(context.getCharacter());
            for (SectionDefinition section : sections) {
                section.setStory(story);
                em.persist(section);
            }
        }
    }

    public List<StoryDefinition> getStories(final int offset, final int limit) {
        final TypedQuery<StoryDefinition> query = em.createNamedQuery("getStories", StoryDefinition.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public StoryDefinition getStory(final long id) {
        final TypedQuery<StoryDefinition> query = em.createNamedQuery("getStory", StoryDefinition.class);
        query.setParameter("id", id);
        return query.getSingleResult();
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
