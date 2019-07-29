package selab.threetier.storage;
import selab.threetier.logic.Entity;
import selab.threetier.logic.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityStorage<T extends Entity> {

    private HashMap<Integer, T> list = new HashMap<Integer, T>();
    private int counter = 0;

    public Integer addOrUpdate(T item) {
        int id = item.getId();
        if (id == 0) {
            item.setId(++counter);
            id = counter;
        }

        if (list.containsKey(id))
            list.replace(id, item);
        else
            list.put(id, item);
        return id;
    }

    public void delete(Task task) {
        int id = task.getId();

        if (list.containsKey(id))
            list.remove(id);
    }

    public ArrayList<T> getAll() {
        return new ArrayList<T>(list.values());
    }

    public T get(int id) {
        return list.get(id);
    }
}
