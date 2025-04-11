package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Step extends Entity {
    public String title;
    public Status status;
    public int taskRef;
    public static final int STEP_ENTITY_CODE = 17;

    public enum Status {
        NotStarted, Completed;
    }

    public Step(String title, int taskRef) {
        this.title = title;
        this.taskRef = taskRef;
        this.status = Status.NotStarted;
    }

    @Override
    public Step clone() throws CloneNotSupportedException {
        return (Step) super.clone();
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }
}
