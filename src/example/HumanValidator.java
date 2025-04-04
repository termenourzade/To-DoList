package example;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Human)) {
            throw new IllegalArgumentException("invalid entity type!");
        }
        Human h = (Human) entity;
        if(h.age < 0) {
            throw new InvalidEntityException("age cannot be negative!");
        }
        if(h.name == null || h.name.isEmpty()) {
            throw new InvalidEntityException("name cannot be null or empty!");
        }
    }
}
