package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.SectionInstance;
import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionMapper {

    public static Section map(final SectionInstance sectionInstance) {
        final Section section = new Section();
        section.setReference(sectionInstance.getReference());
        section.setParagraphs(sectionInstance.getParagraphs());
        final List<Action> actions = new ArrayList<>();
        for (ActionInstance action : sectionInstance.getActions()) {
            actions.add(new Action(action.getNextReference(), action.getDescription()));
        }
        section.setActions(actions);
        return section;
    }

}
