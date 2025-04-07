package util;

import service.HistoryManager;
import service.TaskManager;
import service.impl.HttpTaskManager;
import service.impl.InMemoryHistoryManager;

import java.io.IOException;

// Утилитарный класс, отвечающий за создание менеджера задач
public class Managers {

    // Метод, возвращающий объект-менеджер
    public static TaskManager getDefault(String urlKVServer) throws IOException, InterruptedException {
        return new HttpTaskManager(urlKVServer);
    }

    // Метод, возвращающий объект историю просмотров
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
