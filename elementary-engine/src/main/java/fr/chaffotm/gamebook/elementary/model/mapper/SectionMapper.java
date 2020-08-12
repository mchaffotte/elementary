package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionMapper {

    public static Section map(final SectionInstance sectionInstance) {
        final Section section = new Section();
        section.setId(sectionInstance.getId());
        section.setParagraphs(sectionInstance.getParagraphs());
        final List<Action> actions = new ArrayList<>();
        for (ActionInstance action : sectionInstance.getActions()) {
            actions.add(new Action(action.getNextId(), action.getDescription()));
        }
        section.setActions(actions);
        return section;
    }

}
