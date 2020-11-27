package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SkillDefinition;
import fr.chaffotm.gamebook.elementary.model.instance.Indication;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameContext {

    private final Die die;

    private final CharacterDefinition character;

    private final Set<Indication> indications;

    public GameContext(final Die die, final CharacterDefinition character) {
        this.die = die;
        this.character = character;
        this.indications = new HashSet<>();
    }

    public GameContext(final GameContext context) {
        this.die = context.die;
        this.character = new CharacterDefinition(context.character);
        this.indications = new HashSet<>(context.indications);
    }

    public Die getDie() {
        return die;
    }

    public int getSkillValue(final String skillName) {
        final Optional<SkillDefinition> optionalSkill = character.getSkill(skillName);
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

    public CharacterDefinition getCharacter() {
        return character;
    }

}
