package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;

public class StoryBuilder {

    private final StoryEntity story;

    public StoryBuilder(String name) {
        story = new StoryEntity();
        story.setName(name);
    }

    public StoryEntity build() {
        return story;
    }

    public StoryBuilder character(final CharacterEntity character) {
        story.setCharacter(character);
        return this;
    }

    public StoryBuilder prologue(final SectionEntity prologue) {
        story.setPrologue(prologue);
        return this;
    }

    public StoryBuilder section(final SectionEntity section) {
        story.addSection(section);
        return this;
    }

}
