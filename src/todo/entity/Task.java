package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public enum Status {
        NotStarted, InProgress, Completed
    }

    public static final int TASK_ENTITY_CODE = 16;

    public String title;
    public String description;
    public Date dueDate;
    public Status status;

    private Date creationDate;
    private Date lastModificationDate;

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }
}