import com.sun.net.httpserver.HttpServer;
import enums.TaskStatus;
import model.Epic;
import model.SubTask;
import server.HttpTaskServer;
import server.KVServer;
import service.TaskManager;
import service.impl.InMemoryTaskManager;
import util.Managers;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException, InterruptedException {

        fifthBranch
        new KVServer().start();

        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new HttpTaskServer());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        TaskManager taskManager = Managers.getDefault("http://localhost:8078");

        taskManager.createTask("Сделать домашку", "Сделать всю домашку до конца недели");
        taskManager.createTask("Сходить на прогулку", "Сходить в парк на прогулку");

        taskManager.createEpic("Сделать уборку дома", "Обязательно сделать генеральную уборку дома");
        taskManager.createSubTask("Пропылесосить полы", "Найти пылесос", 3);
        taskManager.createSubTask("Помыть полы", "Хорошо помыть полы", 3);
        taskManager.createSubTask("Помыть посуду", "Помыть всю посуду", 3);

        taskManager.createEpic("Съездить в отпуск", "В отпуске надо хорошо отдохнуть");
        taskManager.createSubTask("Собрать чемодан", "Сложить вещи", 7);
        taskManager.createSubTask("Купить билеты", "Билеты на поезд", 7);
        taskManager.createSubTask("Забронировать отель", "Отель 5 звезд", 7);

        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getEpicById(3));
        System.out.println(taskManager.getSubTaskById(4));
        System.out.println(taskManager.getSubTaskById(5));
        System.out.println(taskManager.getSubTaskById(6));
        System.out.println(taskManager.getEpicById(7));
        System.out.println(taskManager.getSubTaskById(8));
        System.out.println(taskManager.getSubTaskById(9));
        System.out.println(taskManager.getSubTaskById(10));
//
//        taskManager.deleteEpicById(3);
//        taskManager.createEpic("Сделать уборку дома", "Обязательно сделать генеральную уборку дома");
//        System.out.println(taskManager.getEpicById(1));
//        Epic epic = new Epic("testName", "testDescription");
//        epic.setId(1);
//        epic.setStatus(TaskStatus.DONE);
//        taskManager.updateEpicById(epic);
//        System.out.println(epic);
//        System.out.println(taskManager.getEpicById(1));

//        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
//
//        inMemoryTaskManager.createEpic("Test1", "Description1");
//        inMemoryTaskManager.createSubTask("Test2", "Description2", 1);
//        SubTask subTask = new SubTask("Test3", "Description3", 1);
//        subTask.setId(2);
//        subTask.setStatus(TaskStatus.DONE);
//        inMemoryTaskManager.updateSubTaskById(subTask);
//        System.out.println(inMemoryTaskManager.getSubTaskById(2));

//        InMemoryTaskManager manager = new InMemoryTaskManager();
//
//
//        manager.createTask("Сходить в кино", "Купить билеты", TaskStatus.IN_PROGRESS, TaskType.TASK_TYPE);
//
//        manager.createEpic("Сделать ремонт", "Ремонт должен быть хорошим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
//        manager.createSubTask("Сделать полы", "Полы из ламината", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
//        manager.createSubTask("Поклеить обои", "Обои красивые", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 2);
//        manager.createSubTask("Сделать потолок", "Потолок натяжной", TaskStatus.IN_PROGRESS, TaskType.SUBTASK_TYPE, 2);
//
//        manager.createEpic("Построить дом", "Дом должен быть большим", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
//        manager.createSubTask("Сделать фундамент", "Фундамент из бетона", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 6);
//        manager.createSubTask("Сделать стены", "Стены из досок", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 6);
//        manager.createSubTask("Сделать крышу", "Крыша из металла", TaskStatus.IN_PROGRESS, TaskType.SUBTASK_TYPE, 6);
//
//        manager.createEpic("Съездить в отпуск", "Отпуск", TaskStatus.IN_PROGRESS, TaskType.EPIC_TYPE);
//        manager.createSubTask("Купить билеты", "Билеты на поезд", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 10);
//        manager.createSubTask("Забронировать отель", "Отель 5 звезд", TaskStatus.DONE, TaskType.SUBTASK_TYPE, 10);
//        manager.createSubTask("Собрать вещи", "Ничего не забыть", TaskStatus.IN_PROGRESS, TaskType.SUBTASK_TYPE, 10);
//
//        System.out.println(manager.getTaskById(1));
//
//        System.out.println(manager.getEpicById(2));
//        System.out.println(manager.getSubTaskById(3));
//        System.out.println(manager.getSubTaskById(4));
//        System.out.println(manager.getSubTaskById(5));
//
//        System.out.println(manager.getEpicById(6));
//        System.out.println(manager.getSubTaskById(7));
//        System.out.println(manager.getSubTaskById(8));
//        System.out.println(manager.getSubTaskById(9));
//
//        System.out.println(manager.getEpicById(10));
//        System.out.println(manager.getSubTaskById(11));
//        System.out.println(manager.getSubTaskById(12));
//        System.out.println(manager.getSubTaskById(13));
//
//        System.out.println(manager.getHistory());
//        manager.deleteTaskById(1);
//        manager.deleteEpicById(2);
//        manager.deleteSubTaskById(13);
//        System.out.println(manager.getHistory());

    }
}
