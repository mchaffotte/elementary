package fr.chaffotm.gamebook.elementary.model;

public class GameInstance {

    private final GameContext context;

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
}
