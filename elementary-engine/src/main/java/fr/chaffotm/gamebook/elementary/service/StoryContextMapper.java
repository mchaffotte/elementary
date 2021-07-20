package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.builder.CharacterDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.SectionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.StoryContext;
import fr.chaffotm.gamebook.elementary.model.builder.StoryDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.io.*;

public class StoryContextMapper {

    final MoneyConverter converter = new OldBritishMoneyConverter();

    public StoryContext map(final IOStory story, final String location) {
        final StoryDefinitionBuilder builder = new StoryDefinitionBuilder(story.getName(), location)
                .character(map(story.getCharacter()))
                .prologue(map(story.getPrologue()));
        for (IOSection section : story.getSections()) {
            builder.section(map(section));
        }
        return builder.build();
    }

    private CharacterDefinition map(final IOCharacter character) {
        final CharacterDefinitionBuilder builder = new CharacterDefinitionBuilder(character.getName());
        for (IOSkill skill : character.getSkills()) {
            builder.skill(skill.getName(), skill.getValue());
        }
        builder.money(converter.toSubunit(character.getMoney()));
        return builder.build();
    }

    private SectionDefinition map(final IOSection section) {
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
