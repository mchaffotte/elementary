package fr.chaffotm.gamebook.elementary.model;

public class GameInstance {

    private StoryDefinition story;

    private SectionInstance section;

    public StoryDefinition getStory() {
        return story;
    }

    public void setStory(StoryDefinition story) {
        this.story = story;
    }

    public SectionInstance getSection() {
        return section;
    }

    public void setSection(SectionInstance section) {
        this.section = section;
    }

}
