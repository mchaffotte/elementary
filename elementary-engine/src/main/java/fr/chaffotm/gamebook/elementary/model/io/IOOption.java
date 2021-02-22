package fr.chaffotm.gamebook.elementary.model.io;

public class IOOption {

    private String expression;

    private int toReference;

    private IOEvent event;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getToReference() {
        return toReference;
    }

    public void setToReference(int toReference) {
        this.toReference = toReference;
    }

    public IOEvent getEvent() {
        return event;
    }

    public void setEvent(IOEvent event) {
        this.event = event;
    }
}
