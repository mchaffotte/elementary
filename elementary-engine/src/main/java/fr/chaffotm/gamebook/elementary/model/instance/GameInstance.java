package fr.chaffotm.gamebook.elementary.model.instance;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.service.GameContext;

public class GameInstance {

    private final StoryDefinition story;

    private GameContext context;

    private SectionInstance section;

    public GameInstance(final StoryDefinition story, final CharacterDefinition character) {
        this.story = story;
        this.context = new GameContext(new Die(12), character);
    }

    public StoryDefinition getStory() {
        return story;
    }

    public SectionInstance getSection() {
        return section;
    }

    public void setSection(SectionInstance section) {
        this.section = section;
    }

    public GameContext getContext() {
        return context;
    }

    public void setContext(GameContext context) {
        this.context = context;
    }
}
