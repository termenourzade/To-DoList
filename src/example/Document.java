package example;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Document extends Entity implements Trackable, Cloneable {
    public String content;

    public Document(String content) {
        this.content = content;
    }


    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }


    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public int getEntityCode() {
        return this.id;
    }

    @Override
    public Entity clone() throws CloneNotSupportedException {
        return (Entity) super.clone();
    }

}
