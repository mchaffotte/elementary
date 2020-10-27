package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.resource.Story;

public class StoryMapper {

    public static Story map(final StoryDefinition storyDefinition) {
        final Story story = new Story();
        story.setName(storyDefinition.getName());
        return story;
    }

}
