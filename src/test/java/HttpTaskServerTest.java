import com.sun.net.httpserver.HttpServer;
import enums.TaskStatus;
import enums.TaskType;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static server.HttpTaskServer.gson;

class HttpTaskServerTest {

    private static final int PORT = 8080;
    HttpServer httpServer;
    private final HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    void start() throws IOException, InterruptedException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new HttpTaskServer());
        httpServer.start();
    }

    @AfterEach
    void stop() throws IOException {
        httpServer.stop(1);
    }

    @Test
    void shouldReturnEmptyTaskListInitially() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("[]");
    }

    @Test
    void shouldCreateAndRetrieveTask() throws IOException, InterruptedException {
        Task task = new Task("Test Task", "This is a test task");
        task.setStatus(TaskStatus.NEW);
        task.setType(TaskType.TASK_TYPE);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(postResponse.statusCode()).isEqualTo(201);
        assertThat(postResponse.body()).contains("Test Task");

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(getResponse.statusCode()).isEqualTo(200);
        assertThat(getResponse.body()).contains("Test Task");
    }

    @Test
    void shouldReturn404ForUnknownEndpoint() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/unknown"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.body()).contains("Неизвестный эндпоинт");
    }

    @Test
    void shouldCreateAndReturnEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Test Epic", "Epic description");
        String json = gson.toJson(epic);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(postResponse.statusCode()).isEqualTo(201);
        assertThat(postResponse.body()).contains("Test Epic");

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic"))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(getResponse.statusCode()).isEqualTo(200);
        assertThat(getResponse.body()).contains("Test Epic");
    }

    @Test
    void shouldDeleteSubtaskById() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic with Subtask", "Desc");
        String epicJson = gson.toJson(epic);
        HttpRequest postEpic = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic"))
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .header("Content-Type", "application/json")
                .build();
        client.send(postEpic, HttpResponse.BodyHandlers.ofString());

        HttpRequest getEpics = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic"))
                .GET()
                .build();
        HttpResponse<String> epicsResponse = client.send(getEpics, HttpResponse.BodyHandlers.ofString());
        Epic[] epics = gson.fromJson(epicsResponse.body(), Epic[].class);
        int epicId = epics[0].getId();

        SubTask subtask = new SubTask("Subtask", "Subtask desc", epicId);
        subtask.setType(TaskType.SUBTASK_TYPE);
        HttpRequest postSubtask = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .header("Content-Type", "application/json")
                .build();
        client.send(postSubtask, HttpResponse.BodyHandlers.ofString());

        HttpRequest getSubtasks = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask"))
                .GET()
                .build();
        HttpResponse<String> getSubtaskResponse = client.send(getSubtasks, HttpResponse.BodyHandlers.ofString());
        SubTask[] subtasks = gson.fromJson(getSubtaskResponse.body(), SubTask[].class);
        int subtaskId = subtasks[0].getId();

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask?id=" + subtaskId))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(deleteResponse.statusCode()).isEqualTo(200);
        assertThat(deleteResponse.body()).contains("Подзадача с id " + subtaskId + " удалена");

        HttpResponse<String> afterDelete = client.send(getSubtasks, HttpResponse.BodyHandlers.ofString());
        assertThat(afterDelete.body()).doesNotContain("Subtask");
    }

    @Test
    void shouldReturnTaskHistory() throws IOException, InterruptedException {
        Task task = new Task("History Task", "Desc");
        task.setType(TaskType.TASK_TYPE);
        HttpRequest post = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .header("Content-Type", "application/json")
                .build();
        client.send(post, HttpResponse.BodyHandlers.ofString());

        HttpRequest getTasks = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(getTasks, HttpResponse.BodyHandlers.ofString());
        Task[] tasks = gson.fromJson(response.body(), Task[].class);
        int taskId = tasks[0].getId();

        HttpRequest getById = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task?id=" + taskId))
                .GET()
                .build();
        client.send(getById, HttpResponse.BodyHandlers.ofString());

        HttpRequest getHistory = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/history"))
                .GET()
                .build();
        HttpResponse<String> historyResponse = client.send(getHistory, HttpResponse.BodyHandlers.ofString());

        assertThat(historyResponse.statusCode()).isEqualTo(200);
        assertThat(historyResponse.body()).contains("History Task");
    }
}