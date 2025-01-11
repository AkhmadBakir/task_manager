
// Утилитарный класс, отвечающий за создание менеджера задач
public class Managers {

    // Метод, возвращающий объект-менеджер
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    // Метод, возвращающий объект— историю просмотров
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}
