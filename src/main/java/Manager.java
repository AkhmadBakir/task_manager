import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager implements ManagerService {
    Map<Integer, String> tasks = new HashMap<Integer, String>();
    Map<Integer, String> epic = new HashMap<Integer, String>();
    Map<Integer, String> subTask = new HashMap<Integer, String>();


    @Override
    public Map<Integer, String> createTask(String name) {
        tasks.put(1, name);
        return Map.of();
    }

    @Override
    public Map<Integer, String> createEpic(String name) {
        return Map.of();
    }

    @Override
    public Map<Integer, String> createSubTask(String name) {
        return Map.of();
    }

    @Override
    public List<Map<Integer, String>> getSubTasksInEpic(int id) {
        return List.of();
    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTaskEpicSubTaskById(int id) {

    }

    @Override
    public Map<Integer, String> getSubTasksByEpicID(int id) {
        return Map.of();
    }

    @Override
    public Map<Integer, String> updateTasksById(Map<Integer, String> map) {

        return map;
    }

    @Override
    public Map<Integer, String> updateEpicById(Map<Integer, String> map) {

        return map;
    }

    @Override
    public Map<Integer, String> updateSubTaskById(Map<Integer, String> map) {

        return map;
    }

    @Override
    public Map<Integer, String> getTasksById(int id) {
        return Map.of();
    }

    @Override
    public Map<Integer, String> getEpicById(int id) {
        return Map.of();
    }

    @Override
    public Map<Integer, String> getSubTasksById(int id) {
        return Map.of();
    }
}
