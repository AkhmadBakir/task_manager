import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ManagerService {

    //методы для добавления задач, эпиков, подзадач

    Map<Integer, String> createTask(String name);

    Map<Integer, String> createEpic(String name);

    Map<Integer, String> createSubTask(String name);

    //метод для получения списка всех подзадач в эпике

    List<Map<Integer, String>> getSubTasksInEpic(int id);

    //методы для удаления всех задач, эпиков, подзадач

    void deleteAllTasks();

    //методы для удаления задач, эпиков, подзадач по id

    void deleteTaskEpicSubTaskById(int id);

    //Проверка статуса эпика

    Map<Integer, String> getSubTasksByEpicID(int id);

    //методы для обновления задач, эпиков, подзадач

    Map<Integer, String>  updateTasksById(Map<Integer, String> map);

    Map<Integer, String>  updateEpicById(Map<Integer, String> map);

    Map<Integer, String>  updateSubTaskById(Map<Integer, String> map);

    //методы для получения задач, эпиков, подзадач

    Map<Integer, String> getTasksById(int id);

    Map<Integer, String> getEpicById(int id);

    Map<Integer, String> getSubTasksById(int id);

}
