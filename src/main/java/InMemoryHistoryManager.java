import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    List<Task> tasksHistory = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        tasksHistory.add(task);
    }

    @Override
    public void remove(int id) {
        tasksHistory.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return tasksHistory;
    }
}
