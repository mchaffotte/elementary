package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.entity.instance.SkillInstance;
import fr.chaffotm.gamebook.elementary.model.resource.Skill;

import java.util.Set;
import java.util.stream.Collectors;

public class SkillMapper {

    public static Set<Skill> map(final Set<SkillInstance> skillInstances) {
        return skillInstances.stream()
                .map(SkillMapper::map)
                .collect(Collectors.toSet());
    }

    public static Skill map(final SkillInstance skillInstance) {
        return new Skill(skillInstance.getName(), skillInstance.getValue());
    }
}
