package db;

import java.util.Date;

public abstract class Entity implements Cloneable {
    public int id;
    public Date creationDate;
    public Date lastModificationDate;

    public abstract int getEntityCode();

    @Override
    public Entity clone() throws CloneNotSupportedException {
        return (Entity) super.clone();
    }
}