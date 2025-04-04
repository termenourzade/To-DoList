package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int firstId = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private Database() {}

    public static void registerValidator(int entityCode, Validator validator) {
        if(validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("validator for the code " + entityCode + " already exists!");
        }
        validators.put(entityCode, validator);
    }

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        } else {
            throw new IllegalArgumentException("No validator registered for entity code " + e.getEntityCode());
        }
        e.id = firstId;
        try {
            entities.add(e.clone());
        } catch (CloneNotSupportedException ex) {
            System.out.println("copying failed!");
        }
        firstId++;
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id == id) {
                try {
                    return entity.clone();
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException("copying failed!", ex);
                }
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) throws EntityNotFoundException{
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.id == id) {
                entities.remove(i);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void update (Entity e) throws EntityNotFoundException, InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        } else {
            throw new IllegalArgumentException("No validator registered for entity code " + e.getEntityCode());
        }
        boolean found = false;
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.id == e.id) {
                try {
                    entities.set(i, e.clone());
                    found = true;
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException("copying failed!", ex);
                }
                break;
            }
        }
        if (!found) {
            throw new EntityNotFoundException(e.id);
        }
    }
}
