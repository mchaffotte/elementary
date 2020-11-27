package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;

import java.util.ArrayList;
import java.util.List;

public class StoryDefinitionBuilder {

    private final StoryDefinition story;

    private CharacterDefinition character;

    private final List<SectionDefinition> sections;

    public StoryDefinitionBuilder(String name) {
        story = new StoryDefinition();
        story.setName(name);
        sections = new ArrayList<>();
    }

    public StoryContext build() {
        return new StoryContext(story, character, sections);
    }

    public StoryDefinitionBuilder character(final CharacterDefinition character) {
        if (this.character != null) {
            this.character.setStory(null);
        }
        this.character = character;
        this.character.setStory(story);
        return this;
    }

    public StoryDefinitionBuilder prologue(final SectionDefinition prologue) {
        if (story.getPrologue() != null) {
            story.getPrologue().setStory(null);
        }
        story.setPrologue(prologue);
        return this;
    }

    public StoryDefinitionBuilder section(final SectionDefinition section) {
        section.setStory(story);
        sections.add(section);
        return this;
    }

}
