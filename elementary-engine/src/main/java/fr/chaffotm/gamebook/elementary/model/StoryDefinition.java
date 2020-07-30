package fr.chaffotm.gamebook.elementary.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryDefinition {

    private String name;

    private SectionDefinition prologue;

    private Map<Integer, SectionDefinition> sections;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SectionDefinition getPrologue() {
        return prologue;
    }

    public void setPrologue(SectionDefinition prologue) {
        this.prologue = prologue;
    }

    public SectionDefinition getSection(final int id) {
        return sections.get(id);
    }

    public void setSections(final List<SectionDefinition> sections) {
        this.sections = new HashMap<>();
        for (SectionDefinition section : sections) {
            this.sections.put(section.getId(), section);
        }
    }
}
