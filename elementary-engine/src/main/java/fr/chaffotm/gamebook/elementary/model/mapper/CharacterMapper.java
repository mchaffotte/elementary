package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.model.resource.Character;

public class CharacterMapper {

    public static Character map(final CharacterInstance characterInstance) {
        final Character character= new Character();
        character.setName(characterInstance.getName());
        character.setSkills(SkillMapper.map(characterInstance.getSkills()));
        return character;
    }
}
