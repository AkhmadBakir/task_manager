import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    Map<Integer, Task> task = new HashMap<>();
    Map<Integer, Epic> epic = new HashMap<>();
    Map<Integer, SubTask> subTask = new HashMap<>();

    private final HistoryManager history = Managers.getDefaultHistory();

    @Override
    public void createTask(String name, String description, TaskStatus status) {
        task.put(task.size() + 1, new Task(task.size() + 1, name, description, status ));
    }

    @Override
    public void createEpic(String name, String description, TaskStatus status) {
        epic.put(epic.size() + 1, new Epic(epic.size() + 1, name, description, status));
    }

    @Override
    public void createSubTask(String name, String description, TaskStatus status, int id) {
        epic.get(id).addSubTask(new SubTask(subTask.size() + 1, name, description, status, id).getId());
        subTask.put(subTask.size() + 1, new SubTask(subTask.size() + 1, name, description, status, id));
    }

    @Override
    public List<Integer> getSubTasksInEpic(int id) {
        return epic.get(id).getSubTaskIds();
    }

    @Override
    public void deleteAllTasks() {
        task.clear();
        epic.clear();
        subTask.clear();
        history.removeAll();
    }

    @Override
    public void deleteTaskById(int id) {
        history.remove(id);
        task.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        if (epic.containsKey(id)) {
            List<Integer> subtasksId = epic.get(id).getSubTaskIds();
            for (Integer idSubtask : subtasksId) {
                history.remove(idSubtask);
                subTask.remove(idSubtask);
            }
            history.remove(id);
            epic.remove(id);
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        history.remove(id);
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
        history.add(task.get(id));
        return task.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        history.add(epic.get(id));
        return epic.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        history.add(subTask.get(id));
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
    public List<Task> getHistory() { return history.getTasks(); }

}
