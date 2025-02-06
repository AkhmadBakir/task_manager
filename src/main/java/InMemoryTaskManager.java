import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int identifier = 0;

    Map<Integer, Task> task = new HashMap<>();
    Map<Integer, Epic> epic = new HashMap<>();
    Map<Integer, SubTask> subTask = new HashMap<>();

    private final HistoryManager history = Managers.getDefaultHistory();

    @Override
    public void createTask(String name, String description, TaskStatus status, TaskType type) {
        task.put(++identifier, new Task(identifier, name, description, status, type));
    }

    @Override
    public void createEpic(String name, String description, TaskStatus status, TaskType type) {
        epic.put(++identifier, new Epic(identifier, name, description, status, type));
    }

    @Override
    public void createSubTask(String name, String description, TaskStatus status, TaskType type, int id) {
        SubTask subTask1 = new SubTask(++identifier, name, description, status, type, id);
        subTask.put(identifier, subTask1);
        epic.get(id).addSubTask(subTask1.getId());
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
        if (task.containsKey(id)) {
            history.remove(id, TaskType.TASK_TYPE);
            task.remove(id);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        if (epic.containsKey(id)) {
            List<Integer> subtasksId = epic.get(id).getSubTaskIds();
            for (Integer idSubtask : subtasksId) {
                history.remove(idSubtask, TaskType.SUBTASK_TYPE);
                subTask.remove(idSubtask);
            }
            history.remove(id, TaskType.EPIC_TYPE);
            epic.remove(id);
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        if (subTask.containsKey(id)) {
            epic.get(subTask.get(id).getEpicId()).removeSubTask(id - 1);
            history.remove(id, TaskType.SUBTASK_TYPE);
            subTask.remove(id);
        }
    }

    @Override
    public void updateTaskById(int id, String name, String description, TaskStatus status) {
        if (task.containsKey(id)) {
            task.get(id).setName(name);
            task.get(id).setDescription(description);
            task.get(id).setStatus(status);
        }
    }

    @Override
    public void updateEpicById(int id, String name, String description, TaskStatus status) {
        if (epic.containsKey(id)) {
            epic.get(id).setName(name);
            epic.get(id).setDescription(description);
            epic.get(id).setStatus(status);
        }
    }

    @Override
    public void updateSubTaskById(int id, String name, String description, TaskStatus status) {
        if (subTask.containsKey(id)) {
            subTask.get(id).setName(name);
            subTask.get(id).setDescription(description);
            subTask.get(id).setStatus(status);
            boolean flag = true;
            if (status == TaskStatus.DONE) {
                List<Integer> subTasksInEpic = epic.get(subTask.get(id).getEpicId()).getSubTaskIds();
                for (Integer item : subTasksInEpic) {
                    if (!subTask.get(item).getStatus().equals(TaskStatus.DONE)) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                epic.get(subTask.get(id).getEpicId()).setStatus(TaskStatus.DONE);
            }
        }
    }

    @Override
    public Task getTaskById(int id) {
        if (task.containsKey(id)) {
            history.add(task.get(id));
            return task.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpicById(int id) {
        if (epic.containsKey(id)) {
            history.add(epic.get(id));
            return epic.get(id);
        } else {
            return null;
        }
    }

    @Override
    public SubTask getSubTaskById(int id) {
        if (subTask.containsKey(id)) {
            history.add(subTask.get(id));
            return subTask.get(id);
        } else {
            return null;
        }
    }

    @Override
    public List<Integer> getSubTasksInEpic(int id) {
        if (epic.containsKey(id)) {
            return epic.get(id).getSubTaskIds();
        } else {
            return null;
        }

    }

    @Override
    public List<Task> getHistory() { return history.getTasks(); }

    public HistoryManager getHistoryManager() { return history; }

}
