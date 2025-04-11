package todo.entity;

import db.Entity;

public class Step extends Entity {
    public enum Status {
        NotStarted, Completed
    }

    public static final int STEP_ENTITY_CODE = 17;

    public String title;
    public Status status;
    public int taskRef;

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }

}