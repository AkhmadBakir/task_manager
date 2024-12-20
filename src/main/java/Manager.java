import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager implements ManagerService {
    Map<Integer, Task> task = new HashMap<>();
    Map<Integer, Task> epic = new HashMap<>();
    Map<Integer, Task> subTask = new HashMap<>();

    @Override
    public Map<Integer, Task> createTask(String name, String description, TaskStatus status) {
        Task newTask = new Task(task.size() + 1, name, description, status );
        task.put(task.size() + 1, newTask);
        return task;
    }

    @Override
    public Map<Integer, Task> createEpic(String name, String description, TaskStatus status) {
        Epic newEpic = new Epic(epic.size() + 1, name, description, status );
        epic.put(epic.size() + 1, newEpic);
        return epic;
    }

    @Override
    public Map<Integer, Task> createSubTask(String name, String description, TaskStatus status) {
        SubTask newSubTask = new SubTask(subTask.size() + 1, name, description, status );
        subTask.put(subTask.size() + 1, newSubTask);
        return subTask;
    }

    @Override
    public List<Map<Integer, String>> getSubTasksInEpic(int id) {
        return List.of();
    }

    @Override
    public void deleteAllTasks() {
        task.clear();
        epic.clear();
        subTask.clear();
    }

    @Override
    public void deleteTaskById(int id) { //разделить
        task.remove(id);
    }

    @Override
    public void deleteEpicById(int id) { //разделить
        epic.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        subTask.remove(id);
    }

    @Override
    public Map<Integer, String> getSubTasksByEpicID(int id) {

        return Map.of();
    }

    @Override
    public void updateTasksById(int id, String name, String description, TaskStatus status) {
        task.get(id).setName(name);
        task.get(id).setDescription(description);
        task.get(id).setStatus(status);
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
        return task.get(id).toString();
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
