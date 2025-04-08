package service.impl;

import enums.TaskStatus;
import enums.TaskType;
import exception.ManagerSaveException;
import model.Epic;
import model.SubTask;
import model.Task;
import service.HistoryManager;
import service.TaskManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private File file = new File("history.csv");

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public FileBackedTasksManager() {}

    public void save() {

        try (FileWriter fileWriter = new FileWriter(file, false)) {

            String firstStringInFile = "id,name,description,status,type";
            fileWriter.write(firstStringInFile + "\n\n");

            int identifier = getIdentifier();
            fileWriter.write(Integer.toString(identifier) + "\n");

            for (Map.Entry<Integer, Task> entry : task.entrySet()) {
                Task task1 = entry.getValue();
                fileWriter.write(task1.toString() + "\n");
            }

            for (Map.Entry<Integer, Epic> entry : epic.entrySet()) {
                Task task1 = entry.getValue();
                fileWriter.write(task1.toString() + "\n");
            }

            for (Map.Entry<Integer, SubTask> entry : subTask.entrySet()) {
                Task task1 = entry.getValue();
                fileWriter.write(task1.toString() + "\n");
            }

            fileWriter.write("\n" + historyToString(getHistoryManager()));

        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    public static Task fromString(String value) {

        String[] makeTaskString = value.split(",");

        if (makeTaskString.length < 5) {
            throw new IllegalArgumentException("Некорректный формат строки: " + value);
        }

        int id = Integer.parseInt(makeTaskString[0].trim());
        String name = makeTaskString[1].trim();
        String description = makeTaskString[2].trim();
        TaskStatus status = TaskStatus.valueOf(makeTaskString[3].trim());
        TaskType type = TaskType.valueOf(makeTaskString[4].trim());

        switch (makeTaskString[4]) {
            case "TASK_TYPE" -> {
                Task task = new Task(name, description);
                task.setId(id);
                task.setStatus(status);
                task.setType(type);
                return task;
            }
            case "EPIC_TYPE" -> {
                Epic epic = new Epic(name, description);
                epic.setId(id);
                epic.setStatus(status);
                epic.setType(type);
                if (makeTaskString.length > 5 && !makeTaskString[5].trim().isEmpty()) {
                    String[] subTaskString = makeTaskString[5].trim().split("/");
                    for (String s : subTaskString) {
                        epic.addSubTask(Integer.parseInt(s.trim()));
                    }
                    return epic;
                } else {
                    return epic;
                }
            }
            case "SUBTASK_TYPE" -> {
                if (makeTaskString.length <= 5) {
                    throw new IllegalArgumentException("Отсутствует id эпика для подзадачи: " + value);
                }
                int epicId = Integer.parseInt(makeTaskString[5].trim());
                SubTask subTask = new SubTask(name, description, epicId);
                subTask.setId(id);
                subTask.setStatus(status);
                subTask.setType(type);
                return subTask;
            }
            default -> throw new IllegalArgumentException("Неизвестный тип задачи: " + value);
        }
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> tasksInManager = manager.getTasks();
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Task> iterator = tasksInManager.iterator();
        while (iterator.hasNext()) {
                var item = iterator.next();
                stringBuilder.append(item.getId());
                if (iterator.hasNext()) {
                    stringBuilder.append(",");
                }
            }
         return stringBuilder.toString();
    }

    public static List<Task> historyFromString(String value) {
        try (FileReader fileReader = new FileReader("history.csv");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();

            String[] makeTaskString = value.split(",");
            List<Task> history = new ArrayList<>();

            String line;
            while (((line = bufferedReader.readLine()) != null) && !line.trim().isEmpty()) {
                if (!line.isEmpty()) {
                    for (int i = 0; i < makeTaskString.length; i++) {
                        if (makeTaskString[i].equals(String.valueOf(line.charAt(0)))) {
                            history.add(fromString(line));
                        }
                    }
                }
            }
            return history;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        try (FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

            bufferedReader.readLine();
            bufferedReader.readLine();

            String line = "";
            while (!Objects.equals(line = bufferedReader.readLine(), "")) {
                if (!line.isEmpty()) {
                    String[] makeTaskString = line.split(",");

                    int id = Integer.parseInt(makeTaskString[0].trim());

                    if (makeTaskString.length == 1) {
                        fileBackedTasksManager.setIdentifier(Integer.parseInt(makeTaskString[0].trim()));
                    } else {
                        switch (makeTaskString[4]) {
                            case "TASK_TYPE" -> {
                                Task task = fromString(line);
                                fileBackedTasksManager.task.put(id, task);
                            }
                            case "EPIC_TYPE" -> {
                                Epic epic = (Epic) fromString(line);
                                fileBackedTasksManager.epic.put(id, epic);
                            }
                            case "SUBTASK_TYPE" -> {
                                SubTask subTask1 = (SubTask) fromString(line);
                                fileBackedTasksManager.subTask.put(id, subTask1);
                            }
                        }
                    }
                } else {

                }
            }

            List<String> lines = Files.readAllLines(Paths.get(file.toURI()));
            if (file.exists() && file.length() > 0) {
                String historyLine = lines.get(lines.size() - 1).trim();
                if (!historyLine.isEmpty() && !historyLine.trim().isEmpty()) {
                    fileBackedTasksManager.setHistory(historyFromString(historyLine));
                }
            }

            return fileBackedTasksManager;

            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void updateTaskById(Task updateTask) {
        super.updateTaskById(updateTask);
        save();
    }

    @Override
    public void updateEpicById(Epic updateEpic) {
        super.updateEpicById(updateEpic);
        save();
    }

    @Override
    public void updateSubTaskById(SubTask updateSubTask) {
        super.updateSubTaskById(updateSubTask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task createTask(String name, String description) {
        Task newTask = super.createTask(name, description);
        save();
        return newTask;
    }

    @Override
    public Epic createEpic(String name, String description) {
        Epic newEpic = super.createEpic(name, description);
        save();
        return newEpic;
    }

    @Override
    public SubTask createSubTask(String name, String description, int epicId) {
        SubTask newSubTask = super.createSubTask(name, description, epicId);
        save();
        return newSubTask;
    }

    @Override
    public Task getTaskById(int id) {
        super.getTaskById(id);
        save();
        return super.getTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        super.getEpicById(id);
        save();
        return super.getEpicById(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        super.getSubTaskById(id);
        save();
        return super.getSubTaskById(id);
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Integer> getSubTasksInEpic(int id) {
        super.getSubTasksInEpic(id);
        save();
        return super.getSubTasksInEpic(id);
    }


    public static void main(String[] args) {

        File file = new File("history.csv");

        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(file);

//        fileBackedTasksManager1.createTask("Сделать уборку дома", "Обязательно сделать генеральную уборку дома");
//        System.out.println(fileBackedTasksManager1.getTaskById(1));
//        Task task = new Task("testName", "testDescription");
//        task.setId(1);
//        task.setStatus(TaskStatus.DONE);
//        fileBackedTasksManager1.updateTaskById(task);
//        System.out.println(task);
//        System.out.println(fileBackedTasksManager1.getTaskById(1));

        fileBackedTasksManager1.createTask("Сходить в кино", "Купить билеты");
        fileBackedTasksManager1.createEpic("Сделать ремонт", "Ремонт должен быть хорошим");
        fileBackedTasksManager1.createSubTask("Сделать полы", "Полы из ламината", 2);
        fileBackedTasksManager1.createSubTask("Поклеить обои", "Обои красивые", 2);
        fileBackedTasksManager1.createSubTask("Сделать потолок", "Потолок натяжной", 2);
        fileBackedTasksManager1.createEpic("Построить дом", "Дом должен быть большим");
        fileBackedTasksManager1.createSubTask("Сделать фундамент", "Фундамент из бетона", 6);
        fileBackedTasksManager1.createSubTask("Сделать стены", "Стены из досок", 6);
        fileBackedTasksManager1.createSubTask("Сделать крышу", "Крыша из металла", 6);

        System.out.println(fileBackedTasksManager1.getTaskById(1));
        System.out.println(fileBackedTasksManager1.getEpicById(2));
        System.out.println(fileBackedTasksManager1.getSubTaskById(3));
        System.out.println(fileBackedTasksManager1.getSubTaskById(4));
        System.out.println(fileBackedTasksManager1.getSubTaskById(5));
        System.out.println(fileBackedTasksManager1.getEpicById(6));
        System.out.println(fileBackedTasksManager1.getSubTaskById(7));
        System.out.println(fileBackedTasksManager1.getSubTaskById(8));
        System.out.println(fileBackedTasksManager1.getSubTaskById(9));
        System.out.println(fileBackedTasksManager1.getHistory());

        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(file);

        System.out.println(fileBackedTasksManager2.getTaskById(1));
        System.out.println(fileBackedTasksManager2.getEpicById(2));
        System.out.println(fileBackedTasksManager2.getSubTaskById(3));
        System.out.println(fileBackedTasksManager2.getSubTaskById(4));
        System.out.println(fileBackedTasksManager2.getSubTaskById(5));
        System.out.println(fileBackedTasksManager2.getEpicById(6));
        System.out.println(fileBackedTasksManager2.getSubTaskById(7));
        System.out.println(fileBackedTasksManager2.getSubTaskById(8));
        System.out.println(fileBackedTasksManager2.getSubTaskById(9));
        System.out.println(fileBackedTasksManager2.getHistory());

    }
}