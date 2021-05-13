package fr.chaffotm.gamebook.elementary.model.resource;

import java.util.Objects;

public class Action {

    private final String description;

    private final boolean answerNeeded;

    public Action(final String description, final boolean answerNeeded) {
        this.description =description;
        this.answerNeeded = answerNeeded;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAnswerNeeded() {
        return answerNeeded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return answerNeeded == action.answerNeeded && Objects.equals(description, action.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, answerNeeded);
    }

    @Override
    public String toString() {
        return "Action{" +
                "description='" + description + '\'' +
                ", answerNeeded=" + answerNeeded +
                '}';
    }
}
