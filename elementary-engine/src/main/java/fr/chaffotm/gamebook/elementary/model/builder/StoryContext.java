package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;

import java.util.List;

public class StoryContext {

    private final StoryDefinition story;

    private final CharacterDefinition character;

    private final List<SectionDefinition> sections;

    public StoryContext(StoryDefinition story, CharacterDefinition character, List<SectionDefinition> sections) {
        this.story = story;
        this.character = character;
        this.sections = sections;
    }

    public StoryDefinition getStory() {
        return story;
    }

    public CharacterDefinition getCharacter() {
        return character;
    }

    public List<SectionDefinition> getSections() {
        return sections;
    }
}