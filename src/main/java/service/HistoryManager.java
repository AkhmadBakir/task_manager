package service;

import model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getTasks();

    void removeAll();

    String toString();
}
