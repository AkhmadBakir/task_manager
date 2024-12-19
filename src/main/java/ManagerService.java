import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ManagerService {

    //методы для добавления задач, эпиков, подзадач

    Map<Integer, Task> createTask(String name, String description, TaskStatus status);

    Map<Integer, Task> createEpic(String name, String description, TaskStatus status);

    Map<Integer, Task> createSubTask(String name, String description, TaskStatus status);

    //метод для получения списка всех подзадач в эпике

    List<Map<Integer, String>> getSubTasksInEpic(int id);

    //методы для удаления всех задач, эпиков, подзадач

    void deleteAllTasks();

    //методы для удаления задач, эпиков, подзадач по id

    void deleteTaskEpicSubTaskById(int id);

    //Проверка статуса эпика

    Map<Integer, String> getSubTasksByEpicID(int id);

    //методы для обновления задач, эпиков, подзадач

    void  updateTasksById(int id, String name, String description, TaskStatus status);

    void  updateEpicById(int id, String name, String description, TaskStatus status);

    void  updateSubTaskById(int id, String name, String description, TaskStatus status);

    //методы для получения задач, эпиков, подзадач

    String getTasksById(int id);

    String getEpicById(int id);

    String getSubTasksById(int id);

}
