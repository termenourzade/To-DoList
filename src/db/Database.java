package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int firstId = 1;

    public static void add(Entity e) {
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

    public static void update (Entity e) throws EntityNotFoundException {
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