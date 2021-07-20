package fr.chaffotm.gamebook.elementary.model.resource;

import java.util.Objects;

public class Money {

    private int pounds;

    private int shillings;

    private int pence;

    public int getPounds() {
        return pounds;
    }

    public void setPounds(int pounds) {
        this.pounds = pounds;
    }

    public int getShillings() {
        return shillings;
    }

    public void setShillings(int shillings) {
        this.shillings = shillings;
    }

    public int getPence() {
        return pence;
    }

    public void setPence(int pence) {
        this.pence = pence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return pounds == money.pounds && shillings == money.shillings && pence == money.pence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pounds, shillings, pence);
    }
}
