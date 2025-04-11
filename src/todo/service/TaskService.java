package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskService {
    public static void addTask(String title, String description, String dueDateStr) throws InvalidEntityException {
        try {
            Task task = new Task();
            task.title = title;
            task.description = description;
            task.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);
            task.status = Task.Status.NotStarted;
            Database.add(task);
            System.out.println("Task saved successfully.");
            System.out.println("ID: " + task.id);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
        }
    }

    public static void deleteTask(int taskId) throws EntityNotFoundException {
        Database.delete(taskId);

        ArrayList<Entity> toRemove = new ArrayList<>();
        for (Entity entity : Database.entities) {
            if (entity instanceof Step && ((Step) entity).taskRef == taskId) {
                toRemove.add(entity);
            }
        }
        Database.entities.removeAll(toRemove);

        System.out.println("Task with ID=" + taskId + " and its associated steps were successfully deleted.");
    }

    public static void setAsCompleted(int taskId) throws EntityNotFoundException, InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.Completed;
        Database.update(task);

        for (Entity entity : Database.entities) {
            if (entity instanceof Step && ((Step) entity).taskRef == taskId) {
                Step step = (Step) entity;
                step.status = Step.Status.Completed;
                Database.update(step);
            }
        }
    }

    public static void getTaskById(int taskId) throws EntityNotFoundException {
        Task task = (Task) Database.get(taskId);
        printTask(task);
    }

    public static void getAllTasks() {
        ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);

        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                Task task1 = (Task) tasks.get(i);
                Task task2 = (Task) tasks.get(j);
                if (task1.dueDate.after(task2.dueDate)) {
                    tasks.set(i, task2);
                    tasks.set(j, task1);
                }
            }
        }

        for (Entity entity : tasks) {
            printTask((Task) entity);
        }
    }

    public static void updateTask(int taskId, String field, String newValue) throws EntityNotFoundException, InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        String oldValue = null;

        switch (field.toLowerCase()) {
            case "title":
                oldValue = task.title;
                task.title = newValue;
                break;
            case "description":
                oldValue = task.description;
                task.description = newValue;
                break;
            case "duedate":
                oldValue = task.dueDate.toString();
                task.dueDate = java.sql.Date.valueOf(newValue);
                break;
            case "status":
                oldValue = task.status.toString();
                task.status = Task.Status.valueOf(newValue);
                if (task.status == Task.Status.Completed) {
                    for (Entity entity : Database.entities) {
                        if (entity instanceof Step && ((Step) entity).taskRef == taskId) {
                            Step step = (Step) entity;
                            step.status = Step.Status.Completed;
                            Database.update(step);
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + field);
        }

        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + oldValue);
        System.out.println("New Value: " + newValue);
    }

    public static void getIncompleteTasks() {
        ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);

        for (Entity entity : tasks) {
            Task task = (Task) entity;
            if (task.status != Task.Status.Completed) {
                printTask(task);
            }
        }
    }

    private static void printTask(Task task) {
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.title);
        System.out.println("Due Date: " + task.dueDate);
        System.out.println("Status: " + task.status);
        System.out.println("Steps:");
        for (Entity entity : Database.entities) {
            if (entity instanceof Step && ((Step) entity).taskRef == task.id) {
                Step step = (Step) entity;
                System.out.println("    + " + step.title + ":");
                System.out.println("         ID: " + step.id);
                System.out.println("         Status: " + step.status);
            }
        }
    }
}