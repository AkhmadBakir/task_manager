import java.util.List;
import java.util.Map;

public interface TaskManager {

    void createTask(String name, String description, TaskStatus status);

    void createEpic(String name, String description, TaskStatus status);

    void createSubTask(String name, String description, TaskStatus status, int epicId);

    List<Integer> getSubTasksInEpic(int epicId);

    void deleteAllTasks();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    void  updateTaskById(int id, String name, String description, TaskStatus status);

    void  updateEpicById(int id, String name, String description, TaskStatus status);

    void  updateSubTaskById(int id, String name, String description, TaskStatus status);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    String getTask();

    String getEpic();

    String getSubTask();

    List<Task> getHistory();

}
