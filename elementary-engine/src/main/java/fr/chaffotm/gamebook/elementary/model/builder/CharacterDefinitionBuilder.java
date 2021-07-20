package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SkillDefinition;

public class CharacterDefinitionBuilder {

    private final CharacterDefinition character;

    public CharacterDefinitionBuilder(String name) {
        character = new CharacterDefinition();
        character.setName(name);
    }

    public CharacterDefinitionBuilder skill(final String name, final int value) {
        final SkillDefinition skill = new SkillDefinition();
        skill.setName(name);
        skill.setValue(value);
        character.addSkill(skill);
        return this;
    }

    public CharacterDefinitionBuilder money(final int amount) {
        character.setMoney(amount);
        return this;
    }

    public CharacterDefinition build() {
        return character;
    }

}
