package fr.chaffotm.gamebook.elementary.service.expression;

import java.util.HashMap;

public class ExistenceMap<String, V> extends HashMap<String, Boolean> {

    @Override
    public boolean containsKey(Object key) {
        return true;
    }

    @Override
    public Boolean get(Object key) {
        return super.get(key) != null;
    }
}
