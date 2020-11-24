package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;

import java.util.ArrayList;
import java.util.List;

public class StoryBuilder {

    private final StoryEntity story;

    private CharacterEntity character;

    private final List<SectionEntity> sections;

    public StoryBuilder(String name) {
        story = new StoryEntity();
        story.setName(name);
        sections = new ArrayList<>();
    }

    public StoryContext build() {
        return new StoryContext(story, character, sections);
    }

    public StoryBuilder character(final CharacterEntity character) {
        if (this.character != null) {
            this.character.setStory(null);
        }
        this.character = character;
        this.character.setStory(story);
        return this;
    }

    public StoryBuilder prologue(final SectionEntity prologue) {
        if (story.getPrologue() != null) {
            story.getPrologue().setStory(null);
        }
        story.setPrologue(prologue);
        return this;
    }

    public StoryBuilder section(final SectionEntity section) {
        section.setStory(story);
        sections.add(section);
        return this;
    }

}
