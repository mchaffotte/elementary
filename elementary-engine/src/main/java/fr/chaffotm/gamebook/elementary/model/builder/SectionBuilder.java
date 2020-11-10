package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.ActionEntity;
import fr.chaffotm.gamebook.elementary.model.entity.ActionSelection;
import fr.chaffotm.gamebook.elementary.model.entity.EventEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SectionEntity;

public class SectionBuilder {

    private final SectionEntity section;

    public SectionBuilder(int reference) {
        section = new SectionEntity();
        section.setReference(reference);
    }

    public SectionEntity build() {
        return section;
    }

    public SectionBuilder paragraph(final String paragraph) {
        section.addParagraph(paragraph);
        return this;
    }

    public SectionBuilder event(final EventEntity event) {
        section.addEvent(event);
        return this;
    }

    public SectionBuilder selection(final ActionSelection selection) {
        section.setSelection(selection);
        return this;
    }

    public SectionBuilder action(final ActionEntity action) {
        section.addAction(action);
        return this;
    }
}
