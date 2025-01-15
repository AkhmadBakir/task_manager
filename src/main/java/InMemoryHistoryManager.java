import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>(10);
    private HashMap<Integer, CustomLinkedList.Node<Task>> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            tasksHistory.removeNode(nodeMap.get(task.getId()));
        }
        CustomLinkedList.Node<Task> newNode = tasksHistory.linkLast(task);
        nodeMap.put(task.getId(), newNode);

        if (tasksHistory.size() > tasksHistory.getLimit()) {
            CustomLinkedList.Node<Task> oldestNode = tasksHistory.getHead();
            tasksHistory.removeNode(oldestNode);
            nodeMap.remove(oldestNode.data.getId());
        }
    }

    @Override
    public void remove(Task task) {
        tasksHistory.removeNode(nodeMap.get(task.getId()));
        nodeMap.remove(task.getId());
    }

    @Override
    public List<Task> getTasks() {
        return tasksHistory.getTasks();
    }

    @Override
    public void removeAll() {
        nodeMap.clear();
        tasksHistory.clear();
    }
}
