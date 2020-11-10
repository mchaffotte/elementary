package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SkillEntity;

public class CharacterBuilder {

    private final CharacterEntity character;

    public CharacterBuilder(String name) {
        character = new CharacterEntity();
        character.setName(name);
    }

    public CharacterBuilder skill(String name, int value) {
        SkillEntity skill = new SkillEntity();
        skill.setName(name);
        skill.setValue(value);
        character.addSkill(skill);
        return this;
    }

    public CharacterEntity build() {
        return character;
    }

}
