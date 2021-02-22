package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.EventDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;

public class IOReduceSkillBonusEvent implements IOEvent {

    private String skill;

    private int value;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public EventDefinition toEventDefinition() {
        return new EventDefinitionBuilder("reduce-skill-bonus")
                .parameter(skill, String.valueOf(value))
                .build();
    }
}
