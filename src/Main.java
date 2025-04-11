import db.Database;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;
import todo.service.StepService;
import todo.service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("Enter command: ");
            command = scanner.nextLine().trim();

            try {
                switch (command) {
                    case "add task" -> {
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Description: ");
                        String description = scanner.nextLine();
                        System.out.print("Due date (yyyy-MM-dd): ");
                        String dueDate = scanner.nextLine();
                        TaskService.addTask(title, description, dueDate);
                    }
                    case "add step" -> {
                        System.out.print("Task ID: ");
                        int taskId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        StepService.addStep(taskId, title);
                    }
                    case "delete" -> {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        TaskService.deleteTask(id);
                    }
                    case "update task" -> {
                        System.out.print("ID: ");
                        int taskId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Field: ");
                        String field = scanner.nextLine();
                        System.out.print("New Value: ");
                        String newValue = scanner.nextLine();
                        TaskService.updateTask(taskId, field, newValue);
                    }
                    case "update step" -> {
                        System.out.print("ID: ");
                        int stepId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Field: ");
                        String field = scanner.nextLine();
                        System.out.print("New Value: ");
                        String newValue = scanner.nextLine();
                        StepService.updateStep(stepId, field, newValue);
                    }
                    case "get task-by-id" -> {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        TaskService.getTaskById(id);
                    }
                    case "get all-tasks" -> TaskService.getAllTasks();
                    case "get incomplete-tasks" -> TaskService.getIncompleteTasks();
                    case "exit" -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Unknown command.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}