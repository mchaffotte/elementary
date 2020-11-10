package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SkillEntity;
import fr.chaffotm.gamebook.elementary.model.instance.Indication;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameContext {

    private final Die die;

    private final CharacterEntity character;

    private final Set<Indication> indications;

    public GameContext(final Die die, final CharacterEntity character) {
        this.die = die;
        this.character = character;
        this.indications = new HashSet<>();
    }

    public GameContext(final GameContext context) {
        this.die = context.die;
        this.character = new CharacterEntity(context.character);
        this.indications = new HashSet<>(context.indications);
    }

    public Die getDie() {
        return die;
    }

    public int getSkillValue(final String skillName) {
        final Optional<SkillEntity> optionalSkill = character.getSkill(skillName);
        if (optionalSkill.isEmpty()) {
            throw new IllegalStateException("Skill \"" + skillName + "\" does not exist.");
        }
        return optionalSkill.get().getValue();
    }

    public void addIndication(final Indication indication) {
        indications.add(indication);
    }

    public Set<Indication> getIndications() {
        return indications;
    }

    public CharacterEntity getCharacter() {
        return character;
    }

}
