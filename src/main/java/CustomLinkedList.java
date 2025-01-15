import java.util.ArrayList;
import java.util.List;

public class CustomLinkedList<T> {

    public static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private final int limit;
    private int size = 0;

    public CustomLinkedList(int limit) {
        this.limit = limit;
    }

    public Node<T> linkLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
        return newNode;
    }

    public void removeNode(Node<T> node) {
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

        size--;
    }

    public List<T> getTasks() {
        List<T> tasks = new ArrayList<>();
        Node<T> current = head;

        while (current != null) {
            tasks.add(current.data);
            current = current.next;
        }

        return tasks;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public int getLimit() {
        return limit;
    }

    public Node<T> getHead() {
        return head;
    }
}
