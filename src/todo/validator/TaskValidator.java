package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task task)) {
            throw new IllegalArgumentException("invalid entity type!");
        }
        if (task.title == null || task.title.isEmpty()) {
            throw new InvalidEntityException("task title cannot be null or empty!");
        }
    }
}
