package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.resource.Story;

public class StoryMapper {

    public static Story map(final StoryDefinition entity) {
        final Story story = new Story();
        story.setName(entity.getName());
        return story;
    }

}
