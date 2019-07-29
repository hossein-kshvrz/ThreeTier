package selab.threetier.logic;

import selab.threetier.storage.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Task extends Entity {
    private String title;
    private Date start;
    private Date end;

    public String getTitle() { return title; }
    public void setTitle(String value) { title = value; }

    public void setStart(Date value) throws ParseException {
        if (end != null && value.after(end)) {
            throw new ParseException("ERROR", 0);
        }
        start = value;
    }
    public String getStartDate() {
        return new SimpleDateFormat("YYYY-MM-DD").format(start);
    }
    public String getStartTime() {
        return new SimpleDateFormat("HH:mm:ss").format(start);
    }

    public void setEnd(Date value) throws ParseException {
        if (value.before(start)) {
            throw new ParseException("ERROR", 0);
        }
        end = value;
    }
    public String getEndTime() {
        return new SimpleDateFormat("HH:mm:ss").format(end);
    }

    public void save() throws ParseException {
        ArrayList<Task> tasks = Storage.getInstance().getTasks().getAll();
        for (Task task:
             tasks) {
            if (this.start.before(task.end) && task.start.before(this.end)) {
                throw new ParseException("ERROR", 0);
            }
        }
        Storage.getInstance().getTasks().addOrUpdate(this);
    }

    public void remove() {
        Storage.getInstance().getTasks().delete(this);
    }

    public static ArrayList<Task> getAll() {
        ArrayList<Task> tasks = Storage.getInstance().getTasks().getAll();
        tasks.sort((task, t1) -> task.start.before(t1.start) ? -1 : 1);
        return tasks;
    }
}
