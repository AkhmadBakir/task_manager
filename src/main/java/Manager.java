import java.util.HashMap;
import java.util.Map;

public class Manager implements ManagerService {
    Map<Integer, Task> task = new HashMap<>();
    Map<Integer, Epic> epic = new HashMap<>();
    Map<Integer, SubTask> subTask = new HashMap<>();

    @Override
    public Map<Integer, Task> createTask(String name, String description, TaskStatus status) {
        Task newTask = new Task(task.size() + 1, name, description, status );
        task.put(task.size() + 1, newTask);
        return task;
    }

    public Map<Integer, Epic> createEpic(String name, String description, TaskStatus status, Manager subTask) {
        Epic newEpic = new Epic(epic.size() + 1, name, description, status, subTask);
        epic.put(epic.size() + 1, newEpic);
        return epic;
    }

    @Override
    public Map<Integer, SubTask> createSubTask(String name, String description, TaskStatus status) {
        SubTask newSubTask = new SubTask(subTask.size() + 1, name, description, status);
        subTask.put(subTask.size() + 1, newSubTask);
        return subTask;
    }

    @Override
    public Manager getSubTasksInEpic(int epicId) {
        return epic.get(epicId).getSubTask();
    }

    @Override
    public void deleteAllTasks() {
        task.clear();
        epic.clear();
        subTask.clear();
    }

    @Override
    public void deleteTaskById(int id) {
        task.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        epic.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        subTask.remove(id);
    }

    @Override
    public void updateTaskById(int id, String name, String description, TaskStatus status) {
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
    public Task getTaskById(int id) {
        return task.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epic.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        return subTask.get(id);
    }

    @Override
    public String getTask() {
        return task.toString();
    }

    @Override
    public String getEpic() {
        return epic.toString();
    }

    @Override
    public String getSubTask() {
        return subTask.toString();
    }

    @Override
    public void addToSubTask(String name, String description, TaskStatus status) {
        SubTask newSubTask = new SubTask(subTask.size() + 1, name, description, status);
        subTask.put(subTask.size() + 1, newSubTask);
    }
}
