package fr.chaffotm.gamebook.elementary.model.instance;

import java.util.Objects;

public class Indication {

    private final IndicationType type;

    private final String value;

    public Indication(final IndicationType type, final String value) {
        this.type = type;
        this.value = value;
    }

    public IndicationType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indication that = (Indication) o;
        return type == that.type &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
