import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>(10);
    private final HashMap<Integer, CustomLinkedList.Node<Task>> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        CustomLinkedList.Node<Task> newNode = tasksHistory.linkLast(task);
        if (nodeMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        nodeMap.put(task.getId(), newNode);
        if (tasksHistory.size() > tasksHistory.getLimit()) {
            CustomLinkedList.Node<Task> oldestNode = tasksHistory.getHead();
            if (oldestNode != null) {
                tasksHistory.removeNode(oldestNode);
                nodeMap.remove(oldestNode.data.getId());
            }
        }
    }

    @Override
    public void remove(int id) {
        CustomLinkedList.Node<Task> node = nodeMap.get(id);
        if (node != null) {
            tasksHistory.removeNode(node);
            nodeMap.remove(id);
        }
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

    @Override
    public String toString() {
        return tasksHistory.toString();
    }

}
