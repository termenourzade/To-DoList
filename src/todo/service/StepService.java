package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepService {
    public static void addStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step();
        step.taskRef = taskRef;
        step.title = title;
        step.status = Step.Status.NotStarted;
        Database.add(step);
        System.out.println("Step saved successfully.");
        System.out.println("ID: " + step.id);
    }

    public static void updateStep(int stepId, String field, String newValue) throws EntityNotFoundException, InvalidEntityException {
        Step step = (Step) Database.get(stepId);
        String oldValue = null;

        switch (field.toLowerCase()) {
            case "title":
                oldValue = step.title;
                step.title = newValue;
                break;
            case "status":
                oldValue = step.status.toString();
                step.status = Step.Status.valueOf(newValue);

                Task task = (Task) Database.get(step.taskRef);
                boolean allStepsCompleted = true;
                boolean hasSteps = false;

                for (Entity entity : Database.entities) {
                    if (entity instanceof Step && ((Step) entity).taskRef == task.id) {
                        hasSteps = true;
                        if (((Step) entity).status != Step.Status.Completed) {
                            allStepsCompleted = false;
                        }
                    }
                }

                if (!hasSteps) {
                    task.status = Task.Status.NotStarted;
                } else if (allStepsCompleted) {
                    task.status = Task.Status.Completed;
                } else if (step.status == Step.Status.Completed && task.status == Task.Status.NotStarted) {
                    task.status = Task.Status.InProgress;
                }

                Database.update(task);
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + field);
        }

        Database.update(step);
        System.out.println("Successfully updated the step.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + oldValue);
        System.out.println("New Value: " + newValue);
    }

    public static void deleteStep(int stepId) throws EntityNotFoundException, InvalidEntityException {
        Step step = (Step) Database.get(stepId);
        Database.delete(stepId);

        Task task = (Task) Database.get(step.taskRef);
        boolean hasSteps = false;
        boolean allStepsCompleted = true;

        for (Entity entity : Database.entities) {
            if (entity instanceof Step && ((Step) entity).taskRef == task.id) {
                hasSteps = true;
                if (((Step) entity).status != Step.Status.Completed) {
                    allStepsCompleted = false;
                    break;
                }
            }
        }

        if (!hasSteps) {
            task.status = Task.Status.NotStarted;
        } else if (allStepsCompleted) {
            task.status = Task.Status.Completed;
        } else {
            task.status = Task.Status.InProgress;
        }

        Database.update(task);
        System.out.println("Step with ID=" + stepId + " was successfully deleted.");
    }
}