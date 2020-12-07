package fr.chaffotm.gamebook.elementary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.chaffotm.gamebook.elementary.model.builder.CharacterDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.SectionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.StoryContext;
import fr.chaffotm.gamebook.elementary.model.builder.StoryDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.io.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class StoryImporterService {

    private final ObjectMapper mapper;

    @Inject
    public StoryImporterService(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public StoryContext getStoryContext() throws IOException {
        final IOStory story = getBuiltInStory();
        return build(story);
    }

    private IOStory getBuiltInStory() throws IOException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("trapped.json")) {
            return mapper.readValue(inputStream, IOStory.class);
        }
    }

    private StoryContext build(final IOStory story) {
        final StoryDefinitionBuilder builder = new StoryDefinitionBuilder(story.getName())
                .character(build(story.getCharacter()))
                .prologue(build(story.getPrologue()));
        for (IOSection section : story.getSections()) {
            builder.section(build(section));
        }
        return builder.build();
    }

    private CharacterDefinition build(IOCharacter character) {
        final CharacterDefinitionBuilder builder = new CharacterDefinitionBuilder(character.getName());
        for (IOSkill skill : character.getSkills()) {
            builder.skill(skill.getName(), skill.getValue());
        }
        return builder.build();
    }

    private SectionDefinition build(final IOSection section) {
        final SectionDefinitionBuilder builder = new SectionDefinitionBuilder(section.getReference());
        for (String paragraph : section.getParagraphs()) {
            builder.paragraph(paragraph);
        }
        for (IOEvent event : section.getEvents()) {
            builder.event(event.toEventDefinition());
        }
        for (IOAction action : section.getActions()) {
            builder.action(action.toActionDefinition());
        }
        return builder.build();
    }

}
