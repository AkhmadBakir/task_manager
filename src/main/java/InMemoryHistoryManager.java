import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>(10);
    private final HashMap<Integer, Map<TaskType, CustomLinkedList.Node<Task>>> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        Map<TaskType, CustomLinkedList.Node<Task>> typeMap = nodeMap.computeIfAbsent(task.getId(), k -> new HashMap<>());
        CustomLinkedList.Node<Task> existingNode = typeMap.get(task.getType());

        if (existingNode != null) {
            tasksHistory.removeNode(existingNode);
        }

        CustomLinkedList.Node<Task> newNode = tasksHistory.linkLast(task);
        typeMap.put(task.getType(), newNode);

        if (tasksHistory.size() > tasksHistory.getLimit()) {
            CustomLinkedList.Node<Task> oldestNode = tasksHistory.getHead();
            if (oldestNode != null) {
                tasksHistory.removeNode(oldestNode);
                nodeMap.get(oldestNode.data.getId()).remove(oldestNode.data.getType());
            }
        }
    }

    @Override
    public void remove(int id, TaskType type) {
        Map<TaskType, CustomLinkedList.Node<Task>> typeMap = nodeMap.get(id);
        if (typeMap != null) {
            CustomLinkedList.Node<Task> node = typeMap.get(type);
            if (node != null) {
                tasksHistory.removeNode(node);
                typeMap.remove(type);
                if (typeMap.isEmpty()) {
                    nodeMap.remove(id);
                }
            }
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
