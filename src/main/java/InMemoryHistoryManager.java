import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    public Node<Task> head;
    public Node<Task> tail;
    private int size = 0;
    Map<Integer, Node<Task>> nodes = new HashMap<>();
    private final int dimension = 10;

    public void linkLast(Task task) {
        if (task == null) return;

        if (nodes.containsKey(task.getId())) {
            removeNode(nodes.get(task.getId()));
        }

        Node<Task> newNode = new Node<>(task, null, tail);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;

        nodes.put(task.getId(), newNode);
        size++;

        if (size > dimension) {
            removeFirst();
        }
    }

    public void removeFirst() {
        if (head != null) {
            nodes.remove(head.data.getId());
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
            size--;
        }
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            tasks.add(current.data);
            current = current.next;
        }
        return tasks;
    }

    public void removeNode(Node<Task> node) {
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        nodes.remove(node.data.getId());
        size--;
    }

    @Override
    public void add(Task task) {
    linkLast(task);
    }

    @Override
    public void remove(int id) {
        nodes.remove(id);
    }

}
