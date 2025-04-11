package todo.validator;

import db.*;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step step)) {
            throw new IllegalArgumentException("invalid entity type!");
        }
        if (step.title == null || step.title.isEmpty()) {
            throw new InvalidEntityException("step title cannot be null or empty!");
        }
        boolean exist = false;
        for (Entity e : Database.entities)
            if (e.getEntityCode() == Task.TASk_ENTITY_CODE && e.id == step.taskRef) {
                exist = true;
                break;
            }
        if (exist)
            throw new InvalidEntityException("cannot find task with ID= "+ step.taskRef + ".");
    }
}
