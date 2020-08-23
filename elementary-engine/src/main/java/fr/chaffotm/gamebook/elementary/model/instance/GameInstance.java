package fr.chaffotm.gamebook.elementary.model.instance;

import fr.chaffotm.gamebook.elementary.model.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.service.GameContext;

public class GameInstance {

    private GameContext context;

    private final StoryDefinition story;

    private SectionInstance section;

    public GameInstance(final StoryDefinition story) {
        this.story = story;
        this.context = new GameContext(new Die(12), story.getCharacter());
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
