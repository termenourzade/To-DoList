package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int firstId = 1;

    public static void add(Entity e) {
        e.id = firstId;
        entities.add(e);
        firstId++;
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id == id) {
                return entity;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) throws EntityNotFoundException {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.id == id) {
                entities.remove(i);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity e) throws EntityNotFoundException {
        boolean found = false;
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.id == e.id) {
                entities.set(i, e);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new EntityNotFoundException(e.id);
        }
    }
}
