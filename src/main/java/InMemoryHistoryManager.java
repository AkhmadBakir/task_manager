import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    List<Task> tasksHistory = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        for (int i = 1; i <= 10; i++) {
            if (tasksHistory.size() == 10) {
                tasksHistory.remove(0);
            }
        }
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
