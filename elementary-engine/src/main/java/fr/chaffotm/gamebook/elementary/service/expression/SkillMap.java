package fr.chaffotm.gamebook.elementary.service.expression;

import java.util.HashMap;

public class SkillMap<String, V> extends HashMap<String, Integer> {

    @Override
    public boolean containsKey(Object key) {
        return true;
    }

    @Override
    public Integer get(Object key) {
        Integer value = super.get(key);
        if (value == null) {
            throw new IllegalStateException("Skill \"" + key + "\" does not exist.");
        }
        return value;
    }

}
