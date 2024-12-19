import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager implements ManagerService {
    Map<Integer, Task> tasks = new HashMap<>();
    Map<Integer, Task> epic = new HashMap<>();
    Map<Integer, Task> subTask = new HashMap<>();

    @Override
    public Map<Integer, Task> createTask(String name, String description, TaskStatus status) {
        Task newtask = new Task(tasks.size() + 1, name, description, status );
        tasks.put(tasks.size() + 1, newtask);
        return tasks;
    }

    @Override
    public Map<Integer, Task> createEpic(String name, String description, TaskStatus status) {
        Task newtask = new Task(epic.size() + 1, name, description, status );
        epic.put(epic.size() + 1, newtask);
        return epic;
    }

    @Override
    public Map<Integer, Task> createSubTask(String name, String description, TaskStatus status) {
        Task newtask = new Task(subTask.size() + 1, name, description, status );
        subTask.put(subTask.size() + 1, newtask);
        return subTask;
    }

    @Override
    public List<Map<Integer, String>> getSubTasksInEpic(int id) {
        return List.of();
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        epic.clear();
        subTask.clear();
    }

    @Override
    public void deleteTaskEpicSubTaskById(int id) { //разделить
        tasks.remove(id);
        epic.remove(id);
        subTask.remove(id);
    }

    @Override
    public Map<Integer, String> getSubTasksByEpicID(int id) {

        return Map.of();
    }

    @Override
    public void updateTasksById(int id, String name, String description, TaskStatus status) {
        tasks.get(id).setName(name);
        tasks.get(id).setDescription(description);
        tasks.get(id).setStatus(status);
    }

    @Override
    public void updateEpicById(int id, String name, String description, TaskStatus status) {
        epic.get(id).setName(name);
        epic.get(id).setDescription(description);
        epic.get(id).setStatus(status);
    }

    @Override
    public void updateSubTaskById(int id, String name, String description, TaskStatus status) {
        subTask.get(id).setName(name);
        subTask.get(id).setDescription(description);
        subTask.get(id).setStatus(status);
    }

    @Override
    public String getTasksById(int id) {
        return tasks.get(id).toString();
    }

    @Override
    public String getEpicById(int id) {
        return epic.get(id).toString();
    }

    @Override
    public String getSubTasksById(int id) {
        return subTask.get(id).toString();
    }
}
