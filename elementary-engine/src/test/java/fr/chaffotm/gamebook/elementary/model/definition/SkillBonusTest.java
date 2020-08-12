package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class SkillBonusTest {

    @Test
    @DisplayName("getValue should return the value of the skill")
    public void getValueShouldReturnTheValueOfTheSkill() {
        final SkillBonus bonus = new SkillBonus("observation");
        final Character character = new Character();
        character.setSkills(Set.of(new Skill("observation", 1)));
        final GameContext context = new GameContext(null, character);

        int value = bonus.getValue(context);

        assertThat(value).isEqualTo(1);
    }

    @Test
    @DisplayName("getValue should throw an exception if skill does not exist")
    public void getValueShouldThrowAnExceptionIfSkillDoesNotExist() {
        final SkillBonus bonus = new SkillBonus("intuition");
        final Character character = new Character();
        character.setSkills(Set.of(new Skill("observation", 1)));
        final GameContext context = new GameContext(null, character);

        assertThatIllegalStateException()
                .isThrownBy(() -> bonus.getValue(context))
                .withMessage("Skill \"intuition\" does not exist.");
    }

}
