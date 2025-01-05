import java.util.List;
import java.util.Map;

public interface TaskManager {

    //методы для добавления задач, эпиков, подзадач

    Map<Integer, Task> createTask(String name, String description, TaskStatus status);

    Map<Integer, Epic> createEpic(String name, String description, TaskStatus status);

    Map<Integer, SubTask> createSubTask(String name, String description, TaskStatus status, int epicId);

    //метод для получения списка всех подзадач в эпике

    Map<Integer, SubTask> getSubTasksInEpic(int epicId);

    //методы для удаления всех задач, эпиков, подзадач

    void deleteAllTasks();

    //методы для удаления задач, эпиков, подзадач по id

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    //Проверка статуса эпика

    //Map<Integer, String> getSubTasksByEpicID(int id);

    //методы для обновления задач, эпиков, подзадач

    void  updateTaskById(int id, String name, String description, TaskStatus status);

    void  updateEpicById(int id, String name, String description, TaskStatus status);

    void  updateSubTaskById(int id, String name, String description, TaskStatus status);

    //методы для получения задач, эпиков, подзадач

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Map<Epic, Map<Integer, SubTask>> getEpicByIdWithSubTasks(int id);

    SubTask getSubTaskById(int id);

    String getTask();

    String getEpic();

    String getSubTask();

    //InMemoryHistoryManager getHistory();

}
