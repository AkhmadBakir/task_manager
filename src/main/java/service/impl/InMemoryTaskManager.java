package service.impl;

import enums.TaskStatus;
import enums.TaskType;
import model.Epic;
import model.SubTask;
import model.Task;
import service.HistoryManager;
import service.TaskManager;
import util.Managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int identifier = 0;

    protected Map<Integer, Task> task = new HashMap<>();
    protected Map<Integer, Epic> epic = new HashMap<>();
    protected Map<Integer, SubTask> subTask = new HashMap<>();

    private final HistoryManager history = Managers.getDefaultHistory();

    @Override
    public Task createTask(String name, String description) {
        Task newTask = new Task(name, description);
        newTask.setId(++identifier);
        task.put(identifier, newTask);
        return newTask;
    }

    @Override
    public Epic createEpic(String name, String description) {
        Epic newEpic = new Epic(name, description);
        newEpic.setId(++identifier);
        epic.put(identifier, newEpic);
        return newEpic;
    }

    @Override
    public SubTask createSubTask(String name, String description, int id) {
        SubTask newSubTask = new SubTask(name, description, id);
        newSubTask.setId(++identifier);
        subTask.put(identifier, newSubTask);
        epic.get(id).addSubTask(newSubTask.getId());
        return newSubTask;
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
            history.remove(id);
            task.remove(id);
        }
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
        if (subTask.containsKey(id)) {
            epic.get(subTask.get(id).getEpicId()).removeSubTask(id);
            history.remove(id);
            subTask.remove(id);
        }
    }

    @Override
    public void updateTaskById(Task updateTask) {
        if (task.containsKey(updateTask.getId())) {
            task.put(updateTask.getId(), updateTask);
        }
    }

    @Override
    public void updateEpicById(Epic updateEpic) {
        if (epic.containsKey(updateEpic.getId())) {
            epic.put(updateEpic.getId(), updateEpic);
        }
    }

    @Override
    public void updateSubTaskById(SubTask updateSubTask) {
        if (subTask.containsKey(updateSubTask.getId())) {
            subTask.put(updateSubTask.getId(), updateSubTask);

            boolean flag = true;
            if (updateSubTask.getStatus() == TaskStatus.DONE) {
                List<Integer> subTasksInEpic = epic.get(subTask.get(updateSubTask.getId()).getEpicId()).getSubTaskIds();
                for (Integer item : subTasksInEpic) {
                    if (!subTask.get(item).getStatus().equals(TaskStatus.DONE)) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                epic.get(updateSubTask.getEpicId()).setStatus(TaskStatus.DONE);
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
    public Map<Integer, Task> getAllTasks() {
        Map<Integer, Task> allTasks = new HashMap<>();
        allTasks.putAll(task);
        allTasks.putAll(epic);
        allTasks.putAll(subTask);
        return allTasks;
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

    public void setHistory(List<Task> tasks) {
        for (Task task : tasks) {
            history.add(task);
        }
    }

    public HistoryManager getHistoryManager() { return history; }

    public int getIdentifier() { return identifier; }

    public void setIdentifier(int identifier) { this.identifier = identifier; }

    public Map<Integer, Task> getTask() { return task; }

    public Map<Integer, Epic> getEpic() { return epic; }

    public Map<Integer, SubTask> getSubTask() { return subTask; }
}
