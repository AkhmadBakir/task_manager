package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import enums.Endpoint;
import enums.TaskStatus;
import enums.TaskType;
import model.Epic;
import model.SubTask;
import model.Task;
import service.impl.FileBackedTasksManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer implements HttpHandler {

    protected static final Gson gson = new Gson();
    private static final Charset DEFAULT_CHARSET = UTF_8;
    private static final File file = new File("history.csv");
    private static final FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_TASKS: {
                handleGetTasks(exchange);
                break;
            }
            case GET_HISTORY: {
                handleGetHistory(exchange);
                break;
            }
            case GET_TASK: {
                handleGetTask(exchange);
                break;
            }
            case GET_EPIC: {
                handleGetEpic(exchange);
                break;
            }
            case GET_SUBTASK: {
                handleGetSubTask(exchange);
                break;
            }
            case POST_TASK: {
                handlePostTask(exchange);
                break;
            }
            case POST_EPIC: {
                handlePostEpic(exchange);
                break;
            }
            case POST_SUBTASK: {
                handlePostSubTask(exchange);
                break;
            }
            case DELETE_TASK: {
                handleDeleteTask(exchange);
                break;
            }
            case DELETE_EPIC: {
                handleDeleteEpic(exchange);
                break;
            }
            case DELETE_SUBTASK: {
                handleDeleteSubTask(exchange);
                break;
            }
            case UNKNOWN: {
                writeResponse(exchange, "Неизвестный эндпоинт", 404);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }


    private void handleGetTasks(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(fileBackedTasksManager.getAllTasks()), 200);
    }

    private void handleGetHistory(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(fileBackedTasksManager.getHistory()), 200);
    }

    private void handleGetTask(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getQuery().split("=")[1];
        writeResponse(exchange, gson.toJson(fileBackedTasksManager.getTaskById(Integer.parseInt(id))), 200);
    }

    private void handleGetEpic(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getQuery().split("=")[1];
        writeResponse(exchange, gson.toJson(fileBackedTasksManager.getEpicById(Integer.parseInt(id))), 200);
    }

    private void handleGetSubTask(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getQuery().split("=")[1];
        writeResponse(exchange, gson.toJson(fileBackedTasksManager.getSubTaskById(Integer.parseInt(id))), 200);
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            handleUpdateTasks(exchange, "TASK");
        } else {
            handlePostTasks(exchange, "TASK");
        }
    }

    private void handlePostEpic(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            handleUpdateTasks(exchange, "EPIC");
        } else {
            handlePostTasks(exchange, "EPIC");
        }
    }

    private void handlePostSubTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            handleUpdateTasks(exchange, "SUBTASK");
        } else {
            handlePostTasks(exchange, "SUBTASK");
        }
    }

    private void handleUpdateTasks(HttpExchange exchange, String taskType) throws IOException {
        try {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            JsonObject jsonObject;
            try {
                jsonObject = JsonParser.parseString(body).getAsJsonObject();
            } catch (JsonSyntaxException e) {
                writeResponse(exchange, "Некорректный JSON", 400);
                return;
            }

            if (!jsonObject.has("name") || !jsonObject.has("description") ||
                    !jsonObject.has("status") || !jsonObject.has("type")) {
                writeResponse(exchange, "Отсутствуют обязательные поля", 400);
                return;
            }

            String idString = exchange.getRequestURI().getQuery().split("=")[1];
            int id = Integer.parseInt(idString);
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            TaskStatus status;
            TaskType type;

            try {
                status = TaskStatus.valueOf(jsonObject.get("status").getAsString());
                type = TaskType.valueOf(jsonObject.get("type").getAsString());
            } catch (IllegalArgumentException e) {
                writeResponse(exchange, "Некорректные значения для status или type", 400);
                return;
            }

            String responseString = "";

            switch (taskType) {
                case "TASK":
                    Task task = new Task(name, description);
                    task.setId(id);
                    task.setStatus(status);
                    task.setType(type);
                    fileBackedTasksManager.updateTaskById(task);
                    responseString = task.toString();
                    break;
                case "EPIC":
                    Epic epic = new Epic(name, description);
                    epic.setId(id);
                    epic.setStatus(status);
                    epic.setType(type);
                    fileBackedTasksManager.updateEpicById(epic);
                    responseString = epic.toString();
                    break;
                case "SUBTASK":
                    if (!jsonObject.has("epicId")) {
                        writeResponse(exchange, "Отсутствует обязательное поле epicId", 400);
                        return;
                    }
                    int epicId = jsonObject.get("epicId").getAsInt();
                    SubTask subTask = new SubTask(name, description, epicId);
                    subTask.setId(id);
                    subTask.setStatus(status);
                    subTask.setType(type);
                    fileBackedTasksManager.updateSubTaskById(subTask);
                    responseString = subTask.toString();
                    break;
                default:
                    writeResponse(exchange, "Неизвестный тип задачи", 400);
                    return;
            }

            writeResponse(exchange, responseString, 201); // 201 Created

        } catch (Exception e) {
            writeResponse(exchange, "Внутренняя ошибка сервера", 500);
            e.printStackTrace();
        }
    }

    private void handlePostTasks(HttpExchange exchange, String taskType) throws IOException {
        try {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            JsonObject jsonObject;
            try {
                jsonObject = JsonParser.parseString(body).getAsJsonObject();
            } catch (JsonSyntaxException e) {
                writeResponse(exchange, "Некорректный JSON", 400);
                return;
            }

            if (!jsonObject.has("name") || !jsonObject.has("description") ||
                    !jsonObject.has("status") || !jsonObject.has("type")) {
                writeResponse(exchange, "Отсутствуют обязательные поля", 400);
                return;
            }

            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            TaskStatus status;
            TaskType type;

            try {
                status = TaskStatus.valueOf(jsonObject.get("status").getAsString());
                type = TaskType.valueOf(jsonObject.get("type").getAsString());
            } catch (IllegalArgumentException e) {
                writeResponse(exchange, "Некорректные значения для status или type", 400);
                return;
            }

            String responseString = "";

            switch (taskType) {
                case "TASK":
                    Task task = fileBackedTasksManager.createTask(name, description);
                    task.setStatus(status);
                    task.setType(type);
                    responseString = task.toString();
                    break;
                case "EPIC":
                    Epic epic = fileBackedTasksManager.createEpic(name, description);
                    epic.setStatus(status);
                    epic.setType(type);
                    responseString = epic.toString();
                    break;
                case "SUBTASK":
                    if (!jsonObject.has("epicId")) {
                        writeResponse(exchange, "Отсутствует обязательное поле epicId", 400);
                        return;
                    }
                    int epicId = jsonObject.get("epicId").getAsInt();
                    SubTask subTask = fileBackedTasksManager.createSubTask(name, description, epicId);
                    subTask.setStatus(status);
                    subTask.setType(type);
                    responseString = subTask.toString();
                    break;
                default:
                    writeResponse(exchange, "Неизвестный тип задачи", 400);
                    return;
            }

            writeResponse(exchange, responseString, 201); // 201 Created

        } catch (Exception e) {
            writeResponse(exchange, "Внутренняя ошибка сервера", 500);
            e.printStackTrace();
        }
    }


    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getQuery().split("=")[1];
        fileBackedTasksManager.deleteTaskById(Integer.parseInt(id));
        writeResponse(exchange, "Задача удалена", 200);
    }

    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getQuery().split("=")[1];
        fileBackedTasksManager.deleteEpicById(Integer.parseInt(id));
        writeResponse(exchange, "Эпик и подзадачи удалены", 200);
    }

    private void handleDeleteSubTask(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getQuery().split("=")[1];
        fileBackedTasksManager.deleteSubTaskById(Integer.parseInt(id));
        writeResponse(exchange, "Подзадача удалена", 200);
    }

    protected void writeResponse(HttpExchange exchange,
                                 String responseString,
                                 int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    protected Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && pathParts[1].equals("tasks")) {
            return Endpoint.GET_TASKS;
        }
        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("history")) {
            return Endpoint.GET_HISTORY;
        }

        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("task") && requestMethod.equals("GET")) {
            return Endpoint.GET_TASK;
        }
        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("epic") && requestMethod.equals("GET")) {
            return Endpoint.GET_EPIC;
        }
        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("subtask") && requestMethod.equals("GET")) {
            return Endpoint.GET_SUBTASK;
        }

        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("task") && requestMethod.equals("POST")) {
            return Endpoint.POST_TASK;
        }
        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("epic") && requestMethod.equals("POST")) {
            return Endpoint.POST_EPIC;
        }
        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("subtask") && requestMethod.equals("POST")) {
            return Endpoint.POST_SUBTASK;
        }

        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("task") && requestMethod.equals("DELETE")) {
            return Endpoint.DELETE_TASK;
        }
        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("epic") && requestMethod.equals("DELETE")) {
            return Endpoint.DELETE_EPIC;
        }
        if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("subtask") && requestMethod.equals("DELETE")) {
            return Endpoint.DELETE_SUBTASK;
        }

        return Endpoint.UNKNOWN;
    }

}