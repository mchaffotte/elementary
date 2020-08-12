package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.definition.Character;
import fr.chaffotm.gamebook.elementary.model.definition.Skill;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class GameContextTest {

    @Test
    @DisplayName("getValue should return the value of the skill")
    public void getValueShouldReturnTheValueOfTheSkill() {
        final Character character = new Character();
        character.setSkills(Set.of(new Skill("observation", 1)));
        final GameContext context = new GameContext(null, character);

        int value = context.getSkillValue("observation");

        assertThat(value).isEqualTo(1);
    }

    @Test
    @DisplayName("getValue should throw an exception if skill does not exist")
    public void getValueShouldThrowAnExceptionIfSkillDoesNotExist() {
        final Character character = new Character();
        character.setSkills(Set.of(new Skill("observation", 1)));
        final GameContext context = new GameContext(null, character);

        assertThatIllegalStateException()
                .isThrownBy(() -> context.getSkillValue("intuition"))
                .withMessage("Skill \"intuition\" does not exist.");
    }

}
