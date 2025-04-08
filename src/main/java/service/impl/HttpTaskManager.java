package service.impl;

import model.Epic;
import model.SubTask;
import model.Task;
import server.KVTaskClient;

import java.io.IOException;
import java.util.List;

public class HttpTaskManager  extends FileBackedTasksManager {

    private final KVTaskClient kvTaskClient;
    private int identifier = 0;

    public HttpTaskManager(String urlKVServer) throws IOException, InterruptedException {
        super();
        this.kvTaskClient = new KVTaskClient(urlKVServer);
    }

    @Override
    public void deleteTaskById(int id) {
        try {
            kvTaskClient.delete(String.valueOf(id));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = null;
        try {
            String value = kvTaskClient.load(String.valueOf(id));
            epic = (Epic) FileBackedTasksManager.fromString(value.substring(1, value.length() - 1));
            List<Integer> subtaskIds = epic.getSubTaskIds();
            for (Integer subTaskId : subtaskIds) {
                kvTaskClient.delete(String.valueOf(subTaskId));
            }
            kvTaskClient.delete(String.valueOf(id));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        try {
            kvTaskClient.delete(String.valueOf(id));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTaskById(Task updateTask) {
        try {
            kvTaskClient.put(String.valueOf(updateTask.getId()), updateTask.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEpicById(Epic updateEpic) {
        try {
            kvTaskClient.put(String.valueOf(updateEpic.getId()), updateEpic.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSubTaskById(SubTask updateSubTask) {
        try {
            kvTaskClient.put(String.valueOf(updateSubTask.getId()), updateSubTask.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task createTask(String name, String description) {
        Task newTask = new Task(name, description);
        newTask.setId(++identifier);
        try {
            kvTaskClient.put(String.valueOf(newTask.getId()), newTask.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return newTask;
    }

    @Override
    public Epic createEpic(String name, String description) {
        Epic newEpic = new Epic(name, description);
        newEpic.setId(++identifier);
        try {
            kvTaskClient.put(String.valueOf(newEpic.getId()), newEpic.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return newEpic;
    }

    @Override
    public SubTask createSubTask(String name, String description, int epicId) {
        SubTask newSubTask = new SubTask(name, description, epicId);
        newSubTask.setId(++identifier);
        try {
            kvTaskClient.put(String.valueOf(newSubTask.getId()), newSubTask.toString());
            String idEpic = String.valueOf(newSubTask.getEpicId());
            String value = kvTaskClient.load(idEpic);
            Epic epic = (Epic) FileBackedTasksManager.fromString(value.substring(1, value.length() - 1));
            epic.addSubTask(newSubTask.getId());
            kvTaskClient.put(String.valueOf(epic.getId()), epic.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return newSubTask;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = null;
        try {
            String value = kvTaskClient.load(String.valueOf(id));
            if (!value.equals("Такого ключа нет")) {
                task = FileBackedTasksManager.fromString(value.substring(1, value.length() - 1));
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = null;
        try {
            String value = kvTaskClient.load(String.valueOf(id));
            if (!value.equals("Такого ключа нет")) {
                epic = (Epic) FileBackedTasksManager.fromString(value.substring(1, value.length() - 1));
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = null;
        try {
            String value = kvTaskClient.load(String.valueOf(id));
            if (!value.equals("Такого ключа нет")) {
                subTask = (SubTask) FileBackedTasksManager.fromString(value.substring(1, value.length() - 1));
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return subTask;
    }

}
