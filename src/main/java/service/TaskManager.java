package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    Task createTask(String name, String description);

    Epic createEpic(String name, String description);

    SubTask createSubTask(String name, String description, int epicId);

    List<Integer> getSubTasksInEpic(int epicId);

    void deleteAllTasks();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    void  updateTaskById(Task updateTask);

    void  updateEpicById(Epic updateEpic);

    void  updateSubTaskById(SubTask updateSubTask);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    Map<Integer, Task> getAllTasks();

    List<Task> getHistory();

}
