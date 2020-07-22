package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.Story;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StoryService {

    public Story getStory() {
        final Story story = new Story();
        story.setName("Murder at the Opera");
        return story;
    }

}
