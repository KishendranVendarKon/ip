package duke.task;

import duke.command.Ui;
import duke.command.DukeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class  TaskList {
    private List<Task> toDoList;
    protected static SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy hh:mm aaa");

    public TaskList(ArrayList<String> tasks) {
        this.toDoList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            String s = tasks.get(i);
            char type = s.charAt(1);
            switch (type) {
                case 'T':
                    Todo t = new Todo(s.substring(7));
                    if (s.charAt(4) == 'x'){
                        t.markAsDone();
                    }
                    toDoList.add(t);
                    break;
                case 'D':
                    int deadlineIndex = s.indexOf("(by:");
                    Calendar deadlineCal = Calendar.getInstance();

                    try {
                        deadlineCal.setTime(formatter.parse(s.substring(deadlineIndex + 5, s.length() - 1)));
                    } catch(ParseException e) {
                        System.out.println(e);
                    }

                    Deadline d = new Deadline(s.substring(7, deadlineIndex), deadlineCal);
                    if (s.charAt(4) == 'x'){
                        d.markAsDone();
                    }
                    toDoList.add(d);
                    break;
                case 'E':
                    int eventIndex = s.indexOf("(at:");

                    Calendar eventCal = Calendar.getInstance();

                    try {
                        eventCal.setTime(formatter.parse(s.substring(eventIndex + 5, s.length() - 1)));
                    } catch(ParseException e) {
                        System.out.println(e);
                    }

                    Event event = new Event(s.substring(7, eventIndex), eventCal);
                    if (s.charAt(4) == 'x'){
                        event.markAsDone();
                    }
                    toDoList.add(event);
                    break;
            }
        }
    }

    public void add(Task t) {
        toDoList.add(t);
    }

    public void delete(int i) throws DukeException {
        if (i > toDoList.size() || i < 1) {
            throw new DukeException("OOPS!!! Invalid task number");
        }
        Ui.showDeleteTaskMessage(toDoList.get(i-1).toString(), toDoList.size() - 1);
        toDoList.remove(i - 1);

    }

    public void markAsDone(int i) throws DukeException {
        if (i > toDoList.size() || i < 1) {
            throw new DukeException("OOPS!!! Invalid task number");
        }
        toDoList.get(i - 1).markAsDone();
        Ui.showMarkAsDoneMessage(toDoList.get(i-1).toString());
    }

    public void list() {
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println(i + 1 + "." + toDoList.get(i).toString());
        }
    }
    public int size() {
        return toDoList.size();
    }
    public String getStringDes(int i) {
        if (i > toDoList.size() || i < 1) {
            return "";
        }
        return toDoList.get(i - 1).toString();
    }

    public void find(String s) {
        int k = 1;
        for (int i = 0; i < toDoList.size(); i++) {
            String desc = toDoList.get(i).toString();
            if (desc.indexOf(s) >= 0) {
                System.out.println(k + "." + toDoList.get(i).toString());
                k++;
            }
        }
    }


}
