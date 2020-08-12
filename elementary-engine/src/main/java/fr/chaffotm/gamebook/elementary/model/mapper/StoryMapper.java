package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.resource.Story;
import fr.chaffotm.gamebook.elementary.model.definition.StoryDefinition;

public class StoryMapper {

    public static Story map(final StoryDefinition storyDefinition) {
        final Story story = new Story();
        story.setName(storyDefinition.getName());
        return story;
    }

}
