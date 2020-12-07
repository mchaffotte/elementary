package fr.chaffotm.gamebook.elementary.model.io;

import java.util.ArrayList;
import java.util.List;

public class IOStory {

    private String name;

    private IOCharacter character;

    private IOSection prologue;

    private List<IOSection> sections = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IOCharacter getCharacter() {
        return character;
    }

    public void setCharacter(IOCharacter character) {
        this.character = character;
    }

    public IOSection getPrologue() {
        return prologue;
    }

    public void setPrologue(IOSection prologue) {
        this.prologue = prologue;
    }

    public List<IOSection> getSections() {
        return sections;
    }

    public void setSections(List<IOSection> sections) {
        this.sections = sections;
    }
}
