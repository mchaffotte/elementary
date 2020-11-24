package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;

import java.util.List;

public class StoryContext {

    private final StoryEntity story;

    private final CharacterEntity character;

    private final List<SectionEntity> sections;

    public StoryContext(StoryEntity story, CharacterEntity character, List<SectionEntity> sections) {
        this.story = story;
        this.character = character;
        this.sections = sections;
    }

    public StoryEntity getStory() {
        return story;
    }

    public CharacterEntity getCharacter() {
        return character;
    }

    public List<SectionEntity> getSections() {
        return sections;
    }
}