package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Character;
import fr.chaffotm.gamebook.elementary.model.definition.Indication;
import fr.chaffotm.gamebook.elementary.model.definition.Skill;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameContext {

    private final Die die;

    private final Character character;

    private final Set<Indication> indications;

    public GameContext(final Die die, final Character character) {
        this.die = die;
        this.character = character;
        this.indications = new HashSet<>();
    }

    public GameContext(final GameContext context) {
        this.die = context.die;
        this.character = new Character(context.character);
        this.indications = new HashSet<>(context.indications);
    }

    public Die getDie() {
        return die;
    }

    public int getSkillValue(final String skillName) {
        final Optional<Skill> optionalSkill = character.getSkill(skillName);
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

}
