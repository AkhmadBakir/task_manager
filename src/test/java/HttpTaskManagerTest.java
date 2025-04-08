import com.sun.net.httpserver.HttpServer;
import model.Task;
import model.Epic;
import model.SubTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;
import server.KVTaskClient;
import service.TaskManager;
import util.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

import static enums.TaskStatus.*;
import static enums.TaskType.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpTaskManagerTest {

    private static final int PORT = 8080;
    KVServer kvServer;
    HttpServer httpServer;
    TaskManager taskManager;
    KVTaskClient kvTaskClient;

    @BeforeEach
    void start() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        kvTaskClient = new KVTaskClient("http://localhost:8078");
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new HttpTaskServer());
        httpServer.start();
        taskManager = Managers.getDefault("http://localhost:8078");
    }

    @AfterEach
    void stop() throws IOException {
        httpServer.stop(1);
        kvServer.stop(1);
    }

    @Test
    void shouldDeleteTaskById() {
        Task task = taskManager.createTask("Сделать домашку", "Сделать всю домашку до конца недели");
        task.setId(1);
        taskManager.deleteTaskById(1);
        assertThat(taskManager.getTaskById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteEpicById() {
        Epic epic = taskManager.createEpic("Сделать домашку", "Сделать всю домашку до конца недели");
        epic.setId(1);
        taskManager.deleteEpicById(1);
        assertThat(taskManager.getEpicById(1)).isEqualTo(null);
    }

    @Test
    void shouldDeleteSubTaskById() {
        Epic epic = taskManager.createEpic("Сделать домашку", "Сделать всю домашку до конца недели");
        epic.setId(1);
        SubTask subTask = taskManager.createSubTask("Сделать домашку", "Сделать всю домашку до конца недели", 1);
        subTask.setId(2);
        taskManager.deleteSubTaskById(2);
        assertThat(taskManager.getSubTaskById(2)).isEqualTo(null);
    }

    @Test
    void shouldUpdateTaskById() {
        taskManager.createTask("Сходить в кино", "Купить билеты");
        Task updateTask = taskManager.createTask("Сходил в кино", "Купил билеты");
        updateTask.setId(1);
        updateTask.setStatus(DONE);
        taskManager.updateTaskById(updateTask);
        assertThat(taskManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getId, Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly(1, "Сходил в кино", "Купил билеты", DONE, TASK_TYPE);
    }

    @Test
    void shouldUpdateEpicById() {
        taskManager.createEpic("Сходить в кино", "Купить билеты");
        Epic updateEpic = taskManager.createEpic("Сходил в кино", "Купил билеты");
        updateEpic.setId(1);
        updateEpic.setStatus(DONE);
        taskManager.updateEpicById(updateEpic);
        assertThat(taskManager.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getId, Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly(1, "Сходил в кино", "Купил билеты", DONE, EPIC_TYPE);
    }

    @Test
    void shouldUpdateSubTaskById() {
        taskManager.createEpic("Сходить в кино", "Купить билеты");
        taskManager.createSubTask("Сходить в кино", "Купить билеты",1);
        SubTask updateSubTask = taskManager.createSubTask("Сходил в кино", "Купил билеты", 1);
        updateSubTask.setId(1);
        updateSubTask.setStatus(DONE);
        taskManager.updateSubTaskById(updateSubTask);
        assertThat(taskManager.getSubTaskById(1))
                .isNotNull()
                .extracting(SubTask::getId, SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType)
                .containsExactly(1, "Сходил в кино", "Купил билеты", DONE, SUBTASK_TYPE);
    }

    @Test
    void shouldCreateTask() {
        taskManager.createTask("Сходить в кино", "Купить билеты");
        assertThat(taskManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getId, Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly(1, "Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
    }

    @Test
    void shouldCreateEpic() {
        taskManager.createEpic("Сходить в кино", "Купить билеты");
        assertThat(taskManager.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getId, Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly(1, "Сходить в кино", "Купить билеты", NEW, EPIC_TYPE);
    }

    @Test
    void shouldCreateSubTask() {
        taskManager.createEpic("Сходить в кино", "Купить билеты");
        taskManager.createSubTask("Сходить в кино", "Купить билеты", 1);
        assertThat(taskManager.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getId, SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly(2, "Сходить в кино", "Купить билеты", NEW, SUBTASK_TYPE, 1);
    }

    @Test
    void shouldGetTaskById() {
        taskManager.createTask("Сходить в кино", "Купить билеты");
        assertThat(taskManager.getTaskById(1))
                .isNotNull()
                .extracting(Task::getId, Task::getName, Task::getDescription, Task::getStatus, Task::getType)
                .containsExactly(1, "Сходить в кино", "Купить билеты", NEW, TASK_TYPE);
    }

    @Test
    void shouldGetEpicById() {
        taskManager.createEpic("Сходить в кино", "Купить билеты");
        assertThat(taskManager.getEpicById(1))
                .isNotNull()
                .extracting(Epic::getId, Epic::getName, Epic::getDescription, Epic::getStatus, Epic::getType)
                .containsExactly(1, "Сходить в кино", "Купить билеты", NEW, EPIC_TYPE);
    }

    @Test
    void shouldGetSubTaskById() {
        taskManager.createEpic("Сходить в кино", "Купить билеты");
        taskManager.createSubTask("Сходить в кино", "Купить билеты", 1);
        assertThat(taskManager.getSubTaskById(2))
                .isNotNull()
                .extracting(SubTask::getId, SubTask::getName, SubTask::getDescription, SubTask::getStatus, SubTask::getType, SubTask::getEpicId)
                .containsExactly(2, "Сходить в кино", "Купить билеты", NEW, SUBTASK_TYPE, 1);
    }
}